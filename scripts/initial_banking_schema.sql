CREATE SCHEMA `BankAccounts` ;

CREATE TABLE `bankaccounts`.`accounts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_no` VARCHAR(45) NULL,
  `balance` DOUBLE NULL,
  `currency` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

INSERT INTO `bankaccounts`.`accounts` (`id`, `account_no`, `balance`, `currency`) VALUES ('1', '123456-1', '1300', 'EGP');
INSERT INTO `bankaccounts`.`accounts` (`id`, `account_no`, `balance`, `currency`) VALUES ('2', '123456-2', '888', 'USD');
INSERT INTO `bankaccounts`.`accounts` (`id`, `account_no`, `balance`, `currency`) VALUES ('3', '2323445-1', '12300', 'EGP');
commit;

CREATE TABLE `bankaccounts`.`transactions` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `account_no` VARCHAR(45) NULL,
  `transaction` DOUBLE NULL,
  `date` DATETIME NULL,
  `transaction_details` VARCHAR(45) NULL,
  PRIMARY KEY (`transaction_id`));

INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('1', '123456-1', '-100', '2019-01-19 14:55:02', 'ATM withdraw');
INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('2', '123456-1', '300', '2019-04-19 10:55:02', 'Cash deposit');
INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('3', '123456-2', '888', '2019-05-10 15:55:02', 'Account Opening');
INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('4', '2323445-1', '12500', '2019-08-23 19:55:02', 'Account Opening');
INSERT INTO `bankaccounts`.`transactions` (`transaction_id`, `account_no`, `transaction`, `date`, `transaction_details`) VALUES ('5', '2323445-1', '-200', '2019-12-19 22:55:02', 'Cash wihdraw');
commit;

GRANT ALL PRIVILEGES ON *.* TO 'BankAccounts'@'localhost' IDENTIFIED BY 'BankAccounts';
