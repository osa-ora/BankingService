package osa.ora.banking;

import java.util.Calendar;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import osa.ora.beans.BankAccount;
import osa.ora.beans.Transactions;
import osa.ora.beans.TransferRequest;
import osa.ora.customer.exception.JsonMessage;
import osa.ora.customer.persistence.BankAccountPersistence;
import osa.ora.customer.persistence.TransactionsPersistence;

@RestController
@RequestMapping("/api/v1")
public class BankingController {
	private final BankAccountPersistence bankAccountsPersistence = new BankAccountPersistence();
    private final TransactionsPersistence transactionsPersistence = new TransactionsPersistence();
    
    public BankingController() {
		super();
	}

	@GetMapping("/bankaccounts/all")
	public BankAccount[] getAllBankAccounts() {
		System.out.println("Load all bank accounts..");
        BankAccount[] bankAccounts = bankAccountsPersistence.findAll();
        if (bankAccounts != null && bankAccounts.length > 0) {
            for (BankAccount bankAccount : bankAccounts) {
                Transactions[] transactions = transactionsPersistence.findbyId(bankAccount.getAccount_no());
                System.out.println("Found: " + transactions.length + " transactions for this bank account");
                bankAccount.setTransactions(transactions);
            }
            return bankAccounts;
        } else {
            return null;
        }
	}
	
	@GetMapping("/bankaccounts/{account_no}")
	public BankAccount getCustomerByEmail(@PathVariable(value = "account_no") String account_no) {
        BankAccount account = bankAccountsPersistence.findbyId(account_no);
        if (account != null) {
            System.out.println("Retireve bank account using: " + account_no);
            Transactions[] transactions = transactionsPersistence.findbyId(account.getAccount_no());
            System.out.println("Found: " + transactions.length + " transactions for this bank account");
            account.setTransactions(transactions);
            return account;
        } else {
            return null;
        }

	}
    @PostMapping("/bankaccounts/add")
	public boolean addBankAccount(@RequestBody BankAccount account) {
    	JsonMessage jsonMessage = bankAccountsPersistence.save(account);
        if (jsonMessage.getType().equals("Success")) {
            System.out.println("Successfully added a new bank account");
            return true;
        } else {
            return false;
        }
	}
    @PostMapping("/bankaccounts/transfer")
    public String transferMoney(@RequestBody TransferRequest transfer) {
        BankAccount account = bankAccountsPersistence.findbyId(transfer.getFromAccount());
        if (account != null && account.getBalance()>transfer.getAmount()) {
            System.out.println("Transfer from bank account: " + account.getAccount_no());
            transfer.setCurrency(account.getCurrency());
            Calendar cal=Calendar.getInstance();
            String swift="SW-"+cal.getTimeInMillis();
            boolean results=sendSwiftUsingCamel("{\"Swift_id\":\""+swift+"\",\"From\":\""+transfer.getFromAccount()
                    +"\",\"To\":\""+transfer.getToAccount()+"\",\"Amount\":"+transfer.getAmount()+
                    ",\"currency\":\""+transfer.getCurrency()+"\",\"Note\":\""+transfer.getNote()+"\"}");
            String message="Transfer Failed, Service Not Availble";
            if(results) {
                Transactions newTransactions=new Transactions(0,transfer.getFromAccount(),transfer.getAmount()
                        , new Date().toString(), transfer.getNote()+",SwiftCode="+swift);
                boolean transactionUpdate=transactionsPersistence.save(newTransactions);
                boolean bankAccountUpdate=false;
                if(transactionUpdate){
                    account.setBalance(account.getBalance()-transfer.getAmount());
                    bankAccountUpdate=bankAccountsPersistence.update(account);
                }
                if(bankAccountUpdate){
                    message="Transfer Succeeded, swift code:"+swift;
                }else{
                    message="Transfer Failed, Failed to Update the account!";
                }
            }
            return "{\"result\":\""+message+"\"}";
        } else {
            String message="Transfer Failed, Insufficient fund in this account!";
            return "{\"result\":\""+message+"\"}";
        }        
    }
    private boolean sendSwiftUsingCamel(String message) {
        CamelContext ctx = new DefaultCamelContext();
        ctx.setTypeConverterStatisticsEnabled(true);
        //configure jms component        
        String ip = System.getenv("ACTIVEMQ_HOST");
        if (ip == null) {
            ip = "0.0.0.0";
        }
        String port = System.getenv("ACTIVEMQ_PORT");
        if (port == null) {
            port = "61616";
        }
	String user = System.getenv("ACTIVEMQ_USER");
        if (user == null) {
            user = "admin";
        }
	String pass = System.getenv("ACTIVEMQ_PASSWORD");
        if (pass == null) {
            pass = "admin";
        }
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,pass,"tcp://" + ip + ":" + port);
            Connection connection = connectionFactory.createConnection();
            ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue destination = session.createQueue("swift");    
            TextMessage textMessage = session.createTextMessage(message);
            QueueSender publisher = session.createSender(destination);
            System.out.println("will send a message:" + message);
            publisher.send(textMessage);
            session.close();
            connection.close();
            System.out.println("Transfer Message sent");
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("ActiveMQ currently not available! "+e.getLocalizedMessage());
            System.out.println("Reason = "+e.getCause().getLocalizedMessage());
            return false;
        }
    }
    @PostMapping("/bankaccounts/{id}/update")
	public boolean updateBankAccount(@PathVariable(value = "id") long id,@RequestBody BankAccount bankAccount) {
    	bankAccount.setId(id);
        BankAccount cust = bankAccountsPersistence.findbyId(bankAccount.getAccount_no());
        if (cust != null) {
            boolean results = bankAccountsPersistence.update(bankAccount);
            if (results) {
                System.out.println("Successfully updated bank account with id=" + id);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
	}
    @PostMapping("/bankaccounts/{id}/remove")
	public boolean removeBankAccount(@PathVariable(value = "id") String id) {
    	BankAccount cust = bankAccountsPersistence.findbyId(id);
        if (cust != null) {
            JsonMessage jsm = bankAccountsPersistence.delete(id);
            if (jsm.getType().equals("Success")) {
                System.out.println("Successfully deleted bank account with id=" + id);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
	}
}
