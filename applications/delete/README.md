# bank-account-sink



# Using GemFire

Create region in Gfsh

```shell
create region --name=/BankAccount --type=PARTITION
```



# Build Docker


```shell
mvn install 
cd applications/bank-account-sink
mvn spring-boot:build-image
docker tag bank-account-sink:0.0.3-SNAPSHOT


Optional on Kind

```shell
kind load docker-image bank-account-sink:0.0.3-SNAPSHOT


```shell
k apply -f cloud/k8/apps/bank-account-sink
```



# Testing

## Create




