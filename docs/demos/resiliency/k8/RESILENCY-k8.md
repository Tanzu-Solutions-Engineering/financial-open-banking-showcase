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
- Postgres
```shell
./deployment/cloud/k8/data-services/postgres/postgres-setup.sh
```

------------------

# install


GemFire

```shell
kubectl apply -f deployment/cloud/k8/data-services/gemfire/redis/gf-multi-site-redis.yaml
```


Postgres
```shell 
 kubectl  create secret generic postgres-db-app-user-db-secret \
    --from-literal=username=appaccounting \
    --from-literal=password=appaccounting
 
 kubectl  create secret generic postgres-db-read-only-user-db-secret \
    --from-literal=username=accounting-ro \
    --from-literal=password=accounting
 
 kubectl  create secret generic postgres-db-read-write-user-db-secret \
    --from-literal=username=accounting \
    --from-literal=password=accounting
    
kubectl apply -f deployment/cloud/k8/data-services/postgres/ha/postgres-db.yaml
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


```shell
k port-forward service/bank-account-rest-service 8090:80
```

```shell
k port-forward service/http-amqp-source-service 8095:80
```

```shell
k port-forward service/rabbitmq 35672:15672
```

```shell
k port-forward pod/gf-redis-server-0 6379:6379
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

