# Spring SCDF

--------------

## Open SCDF Dashboard

## step 1 - add jdbc-cdc-rabbitmq-source

```shell
k port-forward deployment/scdf-server 9393:8080
```


Start Shell

```shell
java -jar cloud/k8/data-services/scdf/lib/spring-cloud-dataflow-shell-2.8.1.jar 
```

Register application

In SCDF Shell

```shell
app register --name jdbc-cdc-rabbitmq-source --type source --uri docker:cloudnativedata/jdbc-cdc-rabbitmq-source:0.0.1-SNAPSHOT
```

```shell
app register --name stream-account-geode-sink --type sink --uri docker:cloudnativedata/apache-geode-sink:0.0.1-SNAPSHOT
```




```definition
jdbc-cdc-rabbitmq-source --server.port=8080 | stream-account-geode-sink --server.port=8080 --spring.profiles.active=stream --spring.application.name=scf-account-sink-stream --spring.rabbitmq.stream.host=rabbitmq --spring.data.gemfire.pool.locators=gemfire1-locator-0.gemfire1-locator[10334]
```

```shell
stream create --name --spring.cloud.stream.bindings.geodeSink-in-0.destination=Account --spring.cloud.stream.bindings.geodeSink-in-0.group=AccountCdc --valuePdxClassName=com.vmware.financial.open.banking.account.domain.Account --keyFieldExpression=key --regionName=Account --spring.rabbitmq.stream.host=rabbitmq --spring.rabbitmq.host=rabbitmq --LOCATORS=gemfire1-locator-0.gemfire1-locator[10334]  
```

deployer.<appName>.kubernetes.<deployerPropertyName>

```shell
stream deploy --name scf-acct --properties "deployer.jdbc-cdc-rabbitmq-source.kubernetes.requests.memory=1Gi, deployer.stream-account-geode-sink.kubernetes.requests.memory=1Gi, deployer.jdbc-cdc-rabbitmq-source.kubernetes.limits.memory=1Gi, deployer.stream-account-geode-sink.kubernetes.limits.memory=1Gi"
```

deployer.jdbc-cdc-rabbitmq-source.kubernetes.configMapKeyRefs.dataKey=locators,deployer.jdbc-cdc-rabbitmq-source.kubernetes.configMapKeyRefs.configMapName=gemfire1-config,deployer.jdbc-cdc-rabbitmq-source.kubernetes.configMapKeyRefs.envVarName=spring.data.gemfire.pool.locators

```shell
stream list
```



Open [http://localhost:9393/dashboard](http://localhost:9393/dashboard])



## step 5 - review Account data GemFire region/table

```shell
    kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "query --query='select id, bank_id, label, number, product_code from /Account'"
```

## destroy region

```shell
    kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "destroy region --name=Account"
```

## destroy region

```shell
    kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Account --type=PARTITION"
```

```shell
    kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "query --query='select id, bank_id, label, number, product_code from /Account'"
```

## Create pipeline with replay

## In SCDF shell


```shell
cd ~/dataServices/scdf
java -jar spring-cloud-dataflow-shell-2.8.1.jar
```

```shell
stream destroy --name scf-acct
```

See argument --rabbitmq.streaming.replay=true

```shell
stream create --name scdf-account-stream-replay --definition "jdbc-cdc-rabbitmq-source --server.port=8080 | stream-account-geode-sink --rabbitmq.streaming.replay=true --server.port=8080 --spring.profiles.active=stream --spring.application.name=scf-account-sink-stream --spring.rabbitmq.stream.host=rabbitmq --spring.data.gemfire.pool.locators=gemfire1-locator-0.gemfire1-locator[10334]"
```

```shell
stream deploy --name scdf-account-stream-replay --properties "deployer.jdbc-cdc-rabbitmq-source.kubernetes.requests.memory=1Gi, deployer.stream-account-geode-sink.kubernetes.requests.memory=1Gi, deployer.jdbc-cdc-rabbitmq-source.kubernetes.limits.memory=1Gi, deployer.stream-account-geode-sink.kubernetes.limits.memory=1Gi"
```

```shell
watch kubectl get pods
```




View data in GemFire

```shell
    kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "query --query='select id, bank_id, label, number, product_code from /Account'"
```
