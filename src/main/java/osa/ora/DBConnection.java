/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora;

import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public static final String LOCAL_DEFAULT_CONNECT_DESCRIPTOR = "localhost:3306/bankaccounts";
    //com.mysql.jdbc.Driver
    //oracle.jdbc.driver.OracleDriver
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    //Environment Variable Cloud
    public static final Optional<String> DBAAS_USERNAME = Optional.ofNullable(System.getenv("DBAAS_USER_NAME"));
    public static final Optional<String> DBAAS_PASSWORD = Optional.ofNullable(System.getenv("DBAAS_USER_PASSWORD"));
    public static final Optional<String> DBAAS_DEFAULT_CONNECT_DESCRIPTOR = Optional.ofNullable(System.getenv("DBAAS_DEFAULT_CONNECT_DESCRIPTOR"));
    //Local settings        
    public static final String LOCAL_USERNAME = "bankaccounts";
    public static final String LOCAL_PASSWORD = "bankaccounts";

    private static Connection connection = null;
    private static DBConnection instance = null;

    private DBConnection() {
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
	                connection =
       				DriverManager.getConnection(URL + DBAAS_DEFAULT_CONNECT_DESCRIPTOR.orElse(LOCAL_DEFAULT_CONNECT_DESCRIPTOR) +
                                   "?user="+DBAAS_USERNAME.orElse(LOCAL_USERNAME)+"&password="+DBAAS_PASSWORD.orElse(LOCAL_PASSWORD));
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
