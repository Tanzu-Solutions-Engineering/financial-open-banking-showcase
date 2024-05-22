
# Demo 

```shell
kubectl config set-context --current --namespace=accounting
```

Register SCDF Applications


```properties
app.bank-account-app=docker:cloudnativedata/bank-account-rest-service:0.0.4-SNAPSHOT
app.bank-account-http-source=docker:cloudnativedata/http-amqp-source:0.0.5-SNAPSHOT
source.bank-account-http-source=docker:cloudnativedata/http-amqp-source:0.0.5-SNAPSHOT
sink.bank-account-sink=docker:cloudnativedata/bank-account-sink:0.0.2-SNAPSHOT
```


## Create SCDF Stream

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

exchange

bank-accounts.bank-account-http-source


```json
{
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
}
```

cd /Users/Projects/VMware/Tanzu/Use-Cases/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase


-------------

Setup 

```shell
kubectl  create namespace tanzu-data-demo
kubectl config set-context --current --namespace=tanzu-data-demo
kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
```


Create GemFire

```shell
kubectl apply -f deployment/cloud/k8/data-services/gemfire/demo/gemfire.yml
```

Create Postgre

```shell
kubectl apply -f deployment/cloud/k8/data-services/postgres/demo/postgres-db.yaml
```

------------------

Cleanup 

k delete  -f deployment/cloud/k8/data-services/gemfire/demo/gemfire.yml

k delete -f deployment/cloud/k8/data-services/postgres/demo/postgres-db.yaml
