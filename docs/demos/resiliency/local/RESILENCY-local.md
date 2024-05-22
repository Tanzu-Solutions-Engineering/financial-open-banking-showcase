# Prerequisite

- RabbitMQ
- Postgres
- GemFire



## Start GemFire


Download and install GemFire 10 and higher

Set Env Variable $GEMFIRE_HOME to where GemFire is installed

```shell
export GEMFIRE_HOME=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-10.1.0
```

Use wrapper script to start GemFire locally. It will also setup regions and PDX

```shell
./deployment/local/gemfire/start.sh
```

## Start RabbitMQ

```shell
docker network create tanzu
```


```shell
docker run --name rabbitmq01  --network tanzu --rm -d -e RABBITMQ_USERNAME=guest -e RABBITMQ_PASSWORD=guest -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true -p 5672:5672 -p 5552:5552 -p 15672:15672  -p  1883:1883  bitnami/rabbitmq:3.13.1
```

View broker logs (look for message:  started TCP listener on [::]:5672)

```shell
docker logs rabbitmq01 -f
```


Open management console port user/pwd (guest/guest)

```shell
open http://localhost:15672
```

## Postgres 

```shell
docker network create tanzu
```

```shell
docker  run --name postgresql  --network tanzu --rm -it -p 5432:5432 -e ALLOW_EMPTY_PASSWORD=yes -v /Users/devtools/repositories/RDBMS/PostgreSQL/pg-docker:/bitnami/postgresql bitnami/postgresql:latest
```

Connect from psql 

Run initial to test and create database

```shell
docker run -it --rm  --network tanzu bitnami/postgresql:latest psql -h postgresql -U postgres
```

-----------------------------------------

# Start Applications

Start bank-account-sink

```shell
java -jar applications/bank-account-sink/target/bank-account-sink-0.0.2-SNAPSHOT.jar --spring.profiles.active=gemfire --spring.cloud.stream.bindings.input.destination=banking-account  --spring.cloud.stream.bindings.input.connection-name-prefix=banking-account-sink -spring.cloud.stream.bindings.input.group=bank-account-sink
```

Optionally check gemfire connection

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "list clients" 
```


```shell
java -jar applications/bank-account-rest-service/target/bank-account-rest-service-0.0.4-SNAPSHOT.jar --spring.profiles.active=gemfire
```

Open UI

```shell
open http://localhost:4001
```

# Runn


```shell
docker run  -it --rm --network tanzu -p  9494:9494 cloudnativedata/http-amqp-source:0.0.7-SNAPSHOT --spring.rabbitmq.host=rabbitmq01 --spring.rabbitmq.username=guest --spring.rabbitmq.password=guest --server.port=9494
```


open URL

```shell
open http://localhost:9494
```

-------------------

# Popular GemFire


banking-account

```shell
curl -X 'POST' \
  "http://localhost:9494/amqp/?exchange=banking-account" \
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