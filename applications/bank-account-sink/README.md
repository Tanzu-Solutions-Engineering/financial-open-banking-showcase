# bank-account-sink

# Build Docker


```shell
mvn install 
cd applications/bank-account-sink
mvn spring-boot:build-image
docker tag bank-account-sink:0.0.1-SNAPSHOT cloudnativedata/bank-account-sink:0.0.1-SNAPSHOT
docker push cloudnativedata/bank-account-sink:0.0.1-SNAPSHOT
```

Optional on Kind

```shell
kind load docker-image bank-account-sink:0.0.1-SNAPSHOT


```shell
k apply -f cloud/k8/apps/bank-account-sink
```



# Testing

## Create




