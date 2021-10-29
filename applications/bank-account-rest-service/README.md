
# Setup

## k8

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Account --type=PARTITION"
```

# Build Docker


```shell
mvn -pl applications/bank-account-rest-service -am clean spring-boot:build-image
docker tag bank-account-rest-service:0.0.1-SNAPSHOT cloudnativedata/bank-account-rest-service:0.0.1-SNAPSHOT 
docker push cloudnativedata/bank-account-rest-service:0.0.1-SNAPSHOT
```


Optional on Kind

```shell
kind load docker-image bank-account-rest-service:0.0.1-SNAPSHOT
```


```shell
k apply -f cloud/k8/apps/account-rest-service
k delete -f cloud/k8/apps/account-rest-service
```

```shell
k port-forward deployments/account-rest-service 4001:4001
```






