# Setup

## k8

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Bank --type=PARTITION"
```

# Build Docker


```shell
mvn -pl applications/bank-rest-service -am clean spring-boot:build-image
docker tag bank-rest-service:0.0.1-SNAPSHOT cloudnativedata/bank-rest-service:0.0.1-SNAPSHOT
docker push cloudnativedata/bank-rest-service:0.0.1-SNAPSHOT
```


Optional on Kind

```shell
kind load docker-image bank-rest-service:0.0.1-SNAPSHOT
```


```shell
k apply -f cloud/k8/apps/bank-rest-service
k delete -f cloud/k8/apps/bank-rest-service/bank-rest-service.yml
```

```shell
k port-forward deployments/bank-rest-service 4003:4003
```

```shell
curl -X 'GET' \
  'http://localhost:4003/banks/bank1' \
  -H 'accept: */*';echo
```





