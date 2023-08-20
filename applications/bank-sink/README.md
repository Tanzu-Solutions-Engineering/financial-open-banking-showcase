# bank-geode-sink


Create region in Gfsh

```shell
create region --name=/Bank --type=PARTITION
```


content_type: text/plain


```json
{
    "id" : "bank",
    "short_name" : "bank",
    "full_name" : "bank",
    "logo" : "bank",
    "website" : "bank"
}
```

```shell
mvn install 
cd applications/bank-geode-sink
mvn spring-boot:build-image
docker tag bank-geode-sink:0.0.1-SNAPSHOT cloudnativedata/bank-geode-sink:0.0.1-SNAPSHOT
docker push cloudnativedata/bank-geode-sink:0.0.1-SNAPSHOT
```


Optional on Kind

```shell
kind load docker-image bank-geode-sink:0.0.1-SNAPSHOT
```


```shell
k apply -f cloud/k8/apps/bank-geode-sink
k delete -f cloud/k8/apps/bank-geode-sink
```

```shell
k port-forward deployments/bank-geode-sink 4001:4001
```