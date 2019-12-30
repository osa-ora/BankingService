/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.beans;

/**
 *
 * @author ooransa
 */
public class Transactions {
    private long id;
    private String accountNumber;
    private double transaction;
    private String details;
    private String date;
    public Transactions(){
        
    }
    public Transactions(long id, String accountNumber, double transaction, String date,String details){
        this.date=date;
        this.accountNumber=accountNumber;
        this.transaction=transaction;
        this.details=details;
        this.id=id;
    }
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the transaction
     */
    public double getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Transaction {" + "id=" + id + ", account_no=" + accountNumber + 
                ", date=" + date + ", details=" + details + ", transaction=" + transaction + 
                '}';
    }
    
}
