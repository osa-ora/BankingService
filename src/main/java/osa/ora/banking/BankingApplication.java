package osa.ora.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
		System.out.println("For Health Check: http://localhost:PORT/actuator/health");
		System.out.println("Sample URL: http://localhost:PORT/api/v1/bankaccounts/all");
	}

}
