

# Setup


## Postgres

```shell
./cloud/k8/data-services/postgres/postgres-setup.sh
```

```shell
kubectl exec -it postgres-0 -- psql -c "drop TABLE BANK_ACCOUNTS"
```


```shell
kubectl exec -it postgres-0 -- psql -c "CREATE TABLE BANK_ACCOUNTS ( ACCT_ID  TEXT  NOT NULL, BANK_ID  TEXT  NOT NULL, ACCT_LABEL  TEXT NOT NULL, ACCT_NUMBER TEXT NOT NULL, ACCT_PRODUCT_CD TEXT NOT NULL, ACCT_ROUTINGS TEXT, ACCT_VIEWS_BASIC  TEXT, ACCT_BALANCE NUMERIC(30,2) DEFAULT 0, UPDATE_TS TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(ACCT_ID, BANK_ID));"
```

---------------

## GemFire

```shell
./cloud/k8/data-services/geode/gf-k8-setup.sh
```


```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Account --type=PARTITION"
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=CdcRecord --type=PARTITION"
```

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "query --query='select key,balance,views_basic from /Account'"
```

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "remove --region=/Account --key='1|1'"
```
## Rabbit

```shell
./cloud/k8/data-services/rabbitmq/rabbit-k8-setup.sh
```

kubectl exec rabbitmq-server-0 -- rabbitmqctl add_user cdc cdc

kubectl exec rabbitmq-server-0 -- rabbitmqctl set_permissions  -p / cdc ".*" ".*" ".*"

kubectl exec rabbitmq-server-0 -- rabbitmqctl set_user_tags cdc monitoring

-----

# Deploy Applications


CDC Source

```shell
k apply -f cloud/k8/apps/cdc/jdbc-cdc-rabbitmq-source/jdbc-cdc-rabbitmq-source.yml
```

Geode/GemFire Sink

```shell
k apply -f cloud/k8/apps/cdc/apache-geode-sink/apache-geode-sink.yml
```


Bank Account REST

```shell
k apply -f cloud/k8/apps/bank-account-rest-service/bank-account-rest-service.yml
```

```shell
k port-forward deployment/bank-account-rest-service 4001:4001
```

## Building Docker

```shell
mvn install
cd  applications/jdbc-cdc-rabbitmq-source
mvn spring-boot:build-image
cd ..
```


```shell
docker tag jdbc-cdc-rabbitmq-source:0.0.1-SNAPSHOT cloudnativedata/jdbc-cdc-rabbitmq-source:0.0.1-SNAPSHOT
docker push cloudnativedata/jdbc-cdc-rabbitmq-source:0.0.1-SNAPSHOT
```

--------------------
# Testing 

```shell
k port-forward service/rabbitmq 15673:15672
```
```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_accounts (acct_id, bank_id, acct_label, acct_number, acct_product_cd, acct_balance, update_ts) VALUES('1', '1',  'account_label', 'account_nm', 'product_cd', 10, CURRENT_TIMESTAMP);"
```

```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_accounts (acct_id, bank_id, acct_label, acct_number, acct_product_cd, acct_balance, update_ts) VALUES('2', '1',  'account_label', 'account_nm', 'product_cd', 2000000000020.23, CURRENT_TIMESTAMP);"
```

```shell
kubectl exec -it postgres-0 -- psql -c "delete from bank_accounts "
```


```shell
kubectl exec -it postgres-0 -- psql -c "UPDATE bank_accounts set ACCT_BALANCE = 20.50, update_ts = CURRENT_TIMESTAMP where acct_id = '1' and bank_id = '1'"
```
kubectl exec -it postgres-0 -- psql -c "SELECT  concat( acct_id,'|',bank_id) as key,acct_id,  acct_id id, bank_id, acct_label as label, acct_number number, acct_product_cd product_code, acct_routings, acct_views_basic views_basic, concat('{\"amount\":',acct_balance,'}') balance, update_ts FROM bank_accounts"

Update all
```shell
kubectl exec -it postgres-0 -- psql -c "UPDATE bank_accounts set update_ts = CURRENT_TIMESTAMP"
```

```postgres-sql
CREATE TABLE BANK_ACCOUNTS ( ACCT_ID  TEXT  NOT NULL, BANK_ID  TEXT  NOT NULL, ACCT_LABEL  TEXT NOT NULL, ACCT_NUMBER TEXT NOT NULL, ACCT_PRODUCT_CD TEXT NOT NULL, ACCT_ROUTINGS TEXT NOT NULL, ACCT_VIEWS_BASIC  TEXT, ACCT_BALANCE NUMERIC DEFAULT 0, UPDATE_TS TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(ACCT_ID, BANK_ID));
```


