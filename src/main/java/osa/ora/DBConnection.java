/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

//import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author ooransa
 */
public class DBConnection {
    //jdbc:mysql://
    //"jdbc:oracle:thin:@";
    private static final String URL = "jdbc:mysql://";
    //localhost:1521:XE
    //localhost:3306/UserDB
    public static final String LOCAL_DEFAULT_CONNECT_DESCRIPTOR = "localhost:3306/BankAccounts";
    //com.mysql.jdbc.Driver
    //oracle.jdbc.driver.OracleDriver
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    //Environment Variable Cloud
    public static final Optional<String> DBAAS_USERNAME = Optional.ofNullable(System.getenv("DBAAS_USER_NAME"));
    public static final Optional<String> DBAAS_PASSWORD = Optional.ofNullable(System.getenv("DBAAS_USER_PASSWORD"));
    public static final Optional<String> DBAAS_DEFAULT_CONNECT_DESCRIPTOR = Optional.ofNullable(System.getenv("DBAAS_DEFAULT_CONNECT_DESCRIPTOR"));
    //Local settings        
    public static final String LOCAL_USERNAME = "BankAccounts";
    public static final String LOCAL_PASSWORD = "BankAccounts";

    private static Connection connection = null;
    private static DBConnection instance = null;

    private DBConnection() {
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (connection == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
    	try {
	        if (connection == null || !connection.isValid(5)) {
	            try {
	                MysqlDataSource ods=new MysqlDataSource();
	                //OracleDataSource ods = new OracleDataSource();
	                ods.setURL(URL + DBAAS_DEFAULT_CONNECT_DESCRIPTOR.orElse(LOCAL_DEFAULT_CONNECT_DESCRIPTOR));
	                ods.setUser(DBAAS_USERNAME.orElse(LOCAL_USERNAME));
	                ods.setPassword(DBAAS_PASSWORD.orElse(LOCAL_PASSWORD));
	                connection = ods.getConnection();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
        return connection;
    }
}
