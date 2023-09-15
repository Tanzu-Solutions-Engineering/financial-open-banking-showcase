# Bank Account Rest Services

This [Spring Boot](https://spring.io/projects/spring-boot) service implements basic read/write from
Open Bank Project [Bank Account Service interface](https://psd2-apiexplorer.openbankproject.com/?version=OBPv4.0.0&operation_id=OBPv4_0_0-addAccount&currentTag=Account&api-collection-id=&bank_id=at02-0182--01&account_id=&view_id=&counterparty_id=&transaction_id=#OBPv4_0_0-addAccount).

See [GitHub project](https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase.git)

# Setup

## Local GemFire

Install and deploy a [VMware GemFire](https://docs.vmware.com/en/VMware-GemFire/10.0/gf/getting_started-installation-install_intro.html) cluster

In Gfsh
```shell
create region --name=BankAccount --type=PARTITION
```

## GemFire for Redis

```shell
start server --name=redisServer1 --locators=localhost[10334] --server-port=40404 --classpath=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-for-redis-apps-1.1.0/lib/* --J=-Dgemfire-for-redis-enabled=true
```

```shell
gfsh> start server --name=redisServer2 --locators=localhost[10334] --server-port=40405 --classpath=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-for-redis-apps-1.1.0/lib/* --J=-Dgemfire-for-redis-port=6380 --J=-Dgemfire-for-redis-enabled=true
```

# Docker

See [Docker Hub Image](https://hub.docker.com/r/cloudnativedata/bank-account-rest-service)

## Builder Docker

```shell
mvn install
cd applications/bank-account-rest-service 
mvn spring-boot:build-image
docker tag bank-account-rest-service:0.0.2-SNAPSHOT cloudnativedata/bank-account-rest-service:0.0.2-SNAPSHOT 
docker push cloudnativedata/bank-account-rest-service:0.0.2-SNAPSHOT
```

## Kubernetes

Install and Deploy a [GemFire cluster](https://tanzu.vmware.com/developer/data/tanzu-gemfire/guides/get-started-tgf4k8s-sbdg/)

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=BankAccount --type=PARTITION"
```


```shell
k apply -f cloud/k8/apps/account-rest-service
k delete -f cloud/k8/apps/account-rest-service
```

```shell
k port-forward deployments/account-rest-service 4001:4001
```


# Testing


POST

```shell
curl -X 'POST' \
  'http://localhost:4001/obp/v4.0.0/banks/gh.29.uk/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{  
  "user_id":"9ca9a7e4-6d02-40e3-a129-0b2bf89de9b1",  
  "label":"My Account",  
  "product_code":"AC",  
  "balance":{    "currency":"EUR",    "amount":"0"  },  
  "branch_id":"DERBY6",  
  "account_routings":[{    "scheme":"AccountNumber",    "address":"4930396"  }]}'
```

GET 

/obp/v4.0.0/my/banks/at02-0182--01/accounts/ACCOUNT_ID/account

```shell
curl -X 'GET' \
  'http://localhost:4001/obp/v4.0.0/banks/gh.29.uk/accounts/20211130162851787-571565789/account' \
  -H 'accept: */*';echo
```