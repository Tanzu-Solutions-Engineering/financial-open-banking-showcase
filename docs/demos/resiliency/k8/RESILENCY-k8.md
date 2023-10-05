# Prerequisite

Run in DC1

```shell
./deployment/cloud/k8/scripts/resilency-install.sh
```

Run in DC2

```shell
./deployment/cloud/k8/scripts/resilency-install-dc2.sh
```
-------------------
- Postgres

```shell
./deployment/cloud/k8/data-services/postgres/postgres-setup.sh
```

Postgres
```shell 
kubectl apply -f deployment/cloud/k8/data-services/postgres/ha/postgres-db.yaml
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
