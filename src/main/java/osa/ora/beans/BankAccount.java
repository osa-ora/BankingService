package osa.ora.beans;

import java.io.Serializable;

public class BankAccount implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String account_no;
	private double balance;
	private String currency;
        private Transactions[] transactions;

	public BankAccount(){
		
	}
	
	public BankAccount(long id, String account_no, double balance, String currency) {
		super();
		this.id =id;
		this.account_no = account_no;
		this.balance = balance;
		this.currency = currency;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

        @Override
    public String toString() {
        return "Bank Account {" + "id=" + id + ", account_no=" + getAccount_no() + 
                ", balance=" + getBalance() + ", currency=" + getCurrency() + 
                '}';
    }


    /**
     * @return the account_no
     */
    public String getAccount_no() {
        return account_no;
    }

    /**
     * @param account_no the account_no to set
     */
    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the transactions
     */
    public Transactions[] getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(Transactions[] transactions) {
        this.transactions = transactions;
    }
}
