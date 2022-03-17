#!/bin/sh
echo "Please Login to OCP using oc login ..... "  
echo "Make sure Openshift Serverless Operator is installed"
echo "Make sure knative-serving namespace is created and an instance is already provisioned"
echo "Press [Enter] key to resume..." 
read

oc new-project dev
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/db-secret.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/banking-db-deployment.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/banking-db-srv.yaml
echo "Service 'Banking-db' deployed successfully as ephemeral" 
echo "Login to Banking-db mysql pod and install the schema using:"
echo "mysql -u root"
echo "connect bankaccounts"
curl https://raw.githubusercontent.com/osa-ora/BankingService/master/scripts/initial_banking_schema.sql

oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/image.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/build-config.yaml
oc apply -f https://raw.githubusercontent.com/osa-ora/BankingService/master/configs/banking-serverless.yaml
echo "Service 'BankingService' deployed successfully as a serverless" 
echo "Completed!"
