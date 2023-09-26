# Prerequisite

Run

```shell
./deployment/cloud/k8/scripts/resilency-install.sh
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
export SOURCE_APP_HOST=`kubectl get services --namespace accounting  http-amqp-source-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
```

```shell
curl -X 'POST' \
  "http://$SOURCE_APP_HOST/amqp/?exchange=banking-account" \
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


Testing

- Delete RabbitMQ
- Source GEt Internal server error
- REst old data

*Scale Rabbit to 3 Nodes*

```shell
kubectl edit RabbitMQCluster
```
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
