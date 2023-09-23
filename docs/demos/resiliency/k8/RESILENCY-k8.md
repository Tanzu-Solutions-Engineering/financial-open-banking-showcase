# Prerequisite


```shell
kubectl create namespace accounting
kubectl config set-context --current --namespace=accounting
```

- GemFire Operator
```shell
./deployment/cloud/k8/data-services/gemfire/gf-k8-setup.sh
```

- RabbitMQ Operator

GemFire
```shell
kubectl apply -f deployment/cloud/k8/data-services/gemfire/redis/gf-multi-site-redis.yaml
```

RabbitMQ

```shell
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yaml
```


Source applications
```shell
kubectl apply -f deployment/cloud/k8/apps/http-amqp-source/http-amqp-source.yaml
```


Sink application

```shell
kubectl apply -f deployment/cloud/k8/apps/bank-account-sink/bank-account-redis-sink.yaml
```


Rest service

```shell
kubectl apply -f deployment/cloud/k8/apps/account-rest-service/account-rest-redis-service.yaml
```
-------------------
```shell
kubectl create namespace accounting-dc2
kubectl config set-context --current --namespace=accounting-dc2
```
- Postgres

```shell
./deployment/cloud/k8/data-services/postgres/postgres-setup.sh
```

Postgres
```shell 
kubectl apply -f deployment/cloud/k8/data-services/postgres/ha/postgres-db.yaml
```

------------------

Port forwards

```shell
k port-forward service/bank-account-rest-service 8090:80 -n accounting
```

```shell
k port-forward service/http-amqp-source-service 8095:80 -n accounting
```

```shell
k port-forward service/rabbitmq 35672:15672 -n accounting
```

```shell
k port-forward pod/gf-redis-server-0 6379:6379 -n accounting
```

Post though source

```shell
curl -X 'POST' \
  'http://localhost:8095/amqp/?exchange=banking-acount' \
  -H 'accept: application/hal+json' \
  -H 'rabbitContentType: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "bank_id" :  "FLT",
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

*Scale Rabbit to 3 Nodes*

```shell
rabbitmq-streams add_replica  banking-account.bank-account-redis-sink rabbit@rabbitmq-server-1.rabbitmq-nodes.accounting
rabbitmq-streams add_replica  banking-account.bank-account-redis-sink rabbit@rabbitmq-server-2.rabbitmq-nodes.accounting
```


- Delete RabbitMQ node
- Publish from source
- Get from Service

*Delete GemFire Redis Server*

- Get from Service (missing)
- Restart sink (replay)
- Get from Service (found)

*Scale GemFire Redis to 2 Nodes*
- Delete GemFire Redis Server
- Get from Service (found)

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
k port-forward service/rabbitmq 55672:15672 -n accounting-dc2
```
