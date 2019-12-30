package osa.ora.customer.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import osa.ora.DBConnection;
import osa.ora.beans.Transactions;

public class TransactionsPersistence {

	private Connection conn = null;

	public TransactionsPersistence() {
	}
	private Connection getConnection(){
		conn = DBConnection.getInstance().getConnection();
		return conn;
	}

	public boolean save(Transactions account) {
		String insertTableSQL = "INSERT INTO TRANSACTIONS "
				+ "(ACCOUNT_NO,TRANSACTION, DATE, TRANSACTION_DETAILS) "
				+ "VALUES(?,?,?,?)";

		try (PreparedStatement preparedStatement = getConnection()
				.prepareStatement(insertTableSQL)) {

			preparedStatement.setString(1, account.getAccountNumber());
			preparedStatement.setDouble(2, account.getTransaction());
			preparedStatement.setString(3, account.getDate());
			preparedStatement.setString(4, account.getDetails());
                        preparedStatement.executeUpdate();
                        return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public Transactions[] findAll() {
		String queryStr = "SELECT * FROM transactions";
		return this.query(queryStr);
	}

	public Transactions[] findbyId(String id) {
                System.out.println("Retireve bank account transactions using: " + id);
		String queryStr = "SELECT * FROM transactions WHERE ACCOUNT_NO='" + id+"' order by transaction_id desc";
		Transactions transactions[] = this.query(queryStr);
		return transactions;
	}

	public Transactions[] query(String sqlQueryStr) {
		ArrayList<Transactions> cList = new ArrayList<>();
		try (PreparedStatement stmt = getConnection().prepareStatement(sqlQueryStr)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cList.add(new Transactions(rs.getInt("transaction_id"), rs
						.getString("account_no"),rs
						.getDouble("transaction"),rs.getString("date"),rs
						.getString("transaction_details")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cList.toArray(new Transactions[0]);
	}
}
