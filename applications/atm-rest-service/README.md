# Setup

## k8

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Atm --type=PARTITION"
```

# Build Docker


```shell
mvn -pl applications/atm-rest-service -am clean spring-boot:build-image
docker tag atm-rest-service:0.0.1-SNAPSHOT cloudnativedata/atm-rest-service:0.0.1-SNAPSHOT
docker push cloudnativedata/atm-rest-service:0.0.1-SNAPSHOT
```


Optional on Kind

```shell
kind load docker-image atm-rest-service:0.0.1-SNAPSHOT
```


```shell
k apply -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
k delete -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
```

```shell
k port-forward deployments/atm-rest-service 4001:4001
```






