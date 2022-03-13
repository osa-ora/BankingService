#!/bin/sh
echo "Please Login to OCP using oc login ..... "  
echo "Make sure Openshift Serverless Operator is installed"
echo "Make sure knative-serving namespace is created and an instance is already provisioned"
echo "Press [Enter] key to resume..." 
read

oc new-project dev
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/db-secret.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/customer-db-deployment.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/customer-db-service.yaml
echo "Service 'Banking-db' deployed successfully as ephemeral" 
echo "Login to Banking-db mysql pod and install the schema using:"
echo "mysql -u root"
echo "connect bankaccounts"
echo "CREATE TABLE `bankaccounts`.`accounts` (`id` INT NOT NULL AUTO_INCREMENT,`account_no` VARCHAR(45) NULL, `balance` DOUBLE NULL,`currency` VARCHAR(45) NULL, PRIMARY KEY (`id`));"

echo "INSERT INTO `bankaccounts`.`accounts` (`id`, `account_no`, `balance`, `currency`) VALUES ('1', '123456-1', '1300', 'EGP');"
echo "INSERT INTO `bankaccounts`.`accounts` (`id`, `account_no`, `balance`, `currency`) VALUES ('2', '123456-2', '888', 'USD');"
echo "INSERT INTO `bankaccounts`.`accounts` (`id`, `account_no`, `balance`, `currency`) VALUES ('3', '2323445-1', '12300', 'EGP');"
echo "CREATE TABLE `bankaccounts`.`transactions` (`transaction_id` INT NOT NULL AUTO_INCREMENT,`account_no` VARCHAR(45) NULL,`transaction` DOUBLE NULL,`date` DATETIME NULL,`transaction_details` VARCHAR(45) NULL,PRIMARY KEY (`transaction_id`));"
echo "INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('1', '123456-1', '-100', '2019-01-19 14:55:02', 'ATM withdraw');"
echo "INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('2', '123456-1', '300', '2019-04-19 10:55:02', 'Cash deposit');"
echo "INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('3', '123456-2', '888', '2019-05-10 15:55:02', 'Account Opening');"
echo "INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('4', '2323445-1', '12500', '2019-08-23 19:55:02', 'Account Opening');"
echo "INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('5', '2323445-1', '-200', '2019-12-19 22:55:02', 'Cash wihdraw');""

oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/image.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/build-config.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/banking-serverless.yaml
echo "Service 'BankingService' deployed successfully as a serverless" 
echo "Completed!"
