# Prerequisite

Run in DC1

```shell
./deployment/cloud/k8/scripts/resilency-install.sh
```

Run SCDF

```shell
deployment/cloud/k8/scripts/install-scdf-k8.sh
```

Register Applications
Click Add applications -> Import ... from a properties files

```properties
app.bank-account-app=docker:cloudnativedata/bank-account-rest-service:0.0.4-SNAPSHOT
source.bank-account-http-source=docker:cloudnativedata/http-amqp-source:0.0.5-SNAPSHOT
sink.bank-account-sink=docker:cloudnativedata/bank-account-sink:0.0.2-SNAPSHOT
```




## Create Stream

```shell
bank-accounts=bank-account-http-source | bank-account-gemfire-sink: bank-account-sink
bank-account-app=bank-account-app
```

## Deploy Properties


Deploy bank-account-app properties
```properties
deployer.bank-account-app.kubernetes.createLoadBalancer=true
deployer.bank-account-app.kubernetes.environmentVariables=server.port=8080,spring.profiles.active=redis,spring.data.redis.cluster.nodes=gemfire-server-0:6379,spring.data.redis.client-type=JEDIS,spring.application.name=bank-account-app,spring.data.gemfire.pool.default.locators=gemfire-locator-0.gemfire-locator.accounting.svc.cluster.local[10334]
deployer.bank-account-app.bootVersion=3
```


Deploy bank-accounts properties
```properties
deployer.bank-account-http-source.kubernetes.createLoadBalancer=true
deployer.bank-account-gemfire-sink.kubernetes.environmentVariables=spring.profiles.active=redis,spring.data.redis.cluster.nodes=gemfire-server-0:6379,rabbitmq.streaming.replay=true,spring.application.name=bank-account-gemfire-sink,spring.cloud.stream.rabbit.bindings.input.consumer.container-type=stream,spring.cloud.stream.binder.connection-name-prefix=bank-account-gemfire-sink,spring.rabbitmq.stream.host=rabbitmq,spring.data.gemfire.pool.default.locators=gemfire-locator-0.gemfire-locator.accounting.svc.cluster.local[10334]
deployer.bank-account-gemfire-sink.kubernetes.imagePullPolicy=IfNotPresent
deployer.bank-account-gemfire-sink.bootVersion=3
deployer.bank-account-http-source.bootVersion=3
#deployer.bank-account-sink.kubernetes.imagePullPolicy=Always
```



------------

Run in DC2 

*Use the SAME Shell*


```shell
./deployment/cloud/k8/scripts/resilency-install-scdf-dc2.sh
```
------------------

Post though source

```shell
export SOURCE_APP_HOST=`kubectl get services --namespace accounting  http-amqp-source-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
```
banking-account

```shell
curl -X 'POST' \
  "http://$SOURCE_APP_HOST/amqp/?exchange=banking-account" \
  -H 'accept: application/hal+json' \
  -H 'rabbitContentType: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "number" : "9343L",
  "bank_id" :  "BankA",
  "user_id": "imani",
  "label": "imani-001",
  "product_code": "CHECKING",
  "balance": {
    "amount": 25000,
    "currency": "US"
  },
  "account_routings": [
    {
      "address": "1 Straight Street Newark, NJ",
      "scheme": "direct"
    }
  ],
  "branch_id": "BRANCH-BROOK"
}'
```

Connect dc1
```shell
kubectl config set-context --current --namespace=accounting
k9s  --kubeconfig ~/.kube/config.gke.dc1
```

Connect dc2
```shell
kubectl config set-context --current --namespace=accounting-dc2
k9s  --kubeconfig ~/.kube/config.gke.dc2
```

- Delete RabbitMQ
- Source GEt Internal server error
- REst old data



- Delete RabbitMQ node
- Publish from source
- Get from Service

*Delete GemFire Redis Server*

- Get from Service (missing)
- Restart sink (replay)
- Get from Service (found)

*Scale GemFire Redis to 3 Nodes*

- Delete GemFire Redis Server
- Get from Service (found)


----------------

```shell
kubectl edit GemFireCluster
```

------------

Takes about 3-4 minutes
```shell
k apply -f deployment/cloud/k8/data-services/postgres/ha/postgres-db-ha.yaml -n accounting-dc2
```

```shell
k apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yaml -n accounting-dc2
```



```shell
k apply -f deployment/cloud/k8/apps/account-global-sink/account.global.consumer.yaml -n accounting-dc2
```

```shell
k apply -f deployment/cloud/k8/apps/account-global-service/account-global-service.yaml
```


```shell
k port-forward service/rabbitmq 55672:15672 -n accounting-dc2
```
