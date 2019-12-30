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
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String note;
    private String currency;
    public TransferRequest(){
        
    }
    public TransferRequest(String fromAccount, double amount, String toAccount, String note,String currency){
        this.fromAccount=fromAccount;
        this.toAccount=toAccount;
        this.amount=amount;
        this.note=note;
        this.currency=currency;
    }
    @Override
    public String toString() {
        return "Transfer Request {" + "fromAccount=" + getFromAccount() + ", toAccount=" + getToAccount()+ ", note=" + getNote() +  ", currency=" + getCurrency()+
                ", amount=" + getAmount() +"}";
    }

    /**
     * @return the fromAccount
     */
    public String getFromAccount() {
        return fromAccount;
    }

    /**
     * @param fromAccount the fromAccount to set
     */
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    /**
     * @return the toAccount
     */
    public String getToAccount() {
        return toAccount;
    }

    /**
     * @param toAccount the toAccount to set
     */
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
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
    
}
