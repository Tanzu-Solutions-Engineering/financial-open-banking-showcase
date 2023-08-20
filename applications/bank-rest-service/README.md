# Bank Rest Services

This [Spring Boot](https://spring.io/projects/spring-boot) service implements basic read/write from
Open Bank Project [Bank Service interface](https://psd2-apiexplorer.openbankproject.com/?version=OBPv4.0.0&operation_id=OBPv4_0_0-createBank&currentTag=Bank&api-collection-id=&bank_id=at02-0182--01&account_id=&view_id=&counterparty_id=&transaction_id=#OBPv4_0_0-createBank).

See [GitHub project](https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase.git) 

#  Docker

See [Docker Hub Image](https://hub.docker.com/repository/docker/cloudnativedata/bank-rest-service)

## Build Docker

```shell
mvn install
cd applications/bank-rest-service
mvn spring-boot:build-image
docker tag bank-rest-service:0.0.1-SNAPSHOT cloudnativedata/bank-rest-service:0.0.1-SNAPSHOT
docker push cloudnativedata/bank-rest-service:0.0.1-SNAPSHOT
```


# Kubernetes

Install and Deploy a [GemFire cluster](https://tanzu.vmware.com/developer/data/tanzu-gemfire/guides/get-started-tgf4k8s-sbdg/)

## Region Setup


In Kubernetes

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Bank --type=PARTITION"
```


Local Gsh

```shell
create region --name=Bank --type=PARTITION
```


```shell
k apply -f cloud/k8/apps/bank-rest-service
k delete -f cloud/k8/apps/bank-rest-service/bank-rest-service.yml
```

```shell
k port-forward deployments/bank-rest-service 4003:4003
```

--------------

# Testing 

## Create Bank


```shell
curl -X 'POST' \
  'http://localhost:4003/obp/v4.0.0/banks' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{  
  "id":"gh.29.uk",  
  "short_name":"short_name ",  
  "full_name":"full_name",  
  "logo":"logo",  
  "website":"www.openbankproject.com",  
  "bank_routings":[
  {    "scheme":"Bank_ID",    "address":"gh.29.uk"  }]}';echo
```


```shell
curl -X 'GET' \
  'http://localhost:4003/banks/gh.29.uk' \
  -H 'accept: */*';echo
```





