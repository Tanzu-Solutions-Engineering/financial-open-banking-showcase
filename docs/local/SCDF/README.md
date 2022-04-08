# Setup

## Start Config service

```shell
cd /Users/Projects/VMware/Tanzu/SCDF/dev/scdf-extensions
java -jar applications/config-server/target/config-server-0.0.1-SNAPSHOT.jar --server.port=8888 --spring.cloud.config.server.git.uri=file://$HOME/config-repo
```


```shell
open http://localhost:8888/jdbc-cdc-rabbitmq-source/local-bank-atm
```

# Postgres cleanup


```shell
delete from bank_atms;
```

# Gemfire
```shell
cd /Users/devtools/repositories/IMDG/geode/apache-geode-1.13.7/bin
```


```shell
./gfsh
```

Start Locator

```shell
start locator --name=locator
```

```shell
configure pdx --read-serialized=true --disk-store
```

Start Server
```shell
start server --name=server1 --start-rest-api=true --http-service-bind-address=localhost --http-service-port=7071
```

```shell
create lucene index --name=bank_full_name --region=Bank --field=full_name

```

```shell
create region --name=Bank --type=PARTITION_PERSISTENT
```

```shell
deploy --jar=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/apache-geode-extensions/components/apache-geode-extensions-core/build/libs/apache-geode-extensions-core-2.5.1-SNAPSHOT.jar
```



```shell
open http://localhost:7080/geode/swagger-ui.html
```



Create region in Gfsh

```shell
create region --name=/Atm --type=PARTITION
```


Setup Regions

```shell

deploy --jar=/Users/Projects/VMware/Tanzu/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase/components/atm-domain/target/atm-domain-0.0.1-SNAPSHOT.jar
create region --name=BankAccount --type=PARTITION

create region --name=Atm --type=PARTITION --key-constraint=java.lang.String --value-constraint=com.vmware.financial.open.banking.atm.domain.Atm

```

## SCDF SETUP


```shell
app register --name jdbc-cdc-rabbitmq-source --type source --uri file:///Users/Projects/VMware/Tanzu/SCDF/dev/scdf-extensions/applications/jdbc-cdc-rabbitmq-source/target/jdbc-cdc-rabbitmq-source-0.0.2-SNAPSHOT.jar
```


```shell
app register --name atm-geode-sink --type sink --uri file:///Users/Projects/VMware/Tanzu/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase/applications/atm-geode-sink/target/atm-geode-sink-0.0.2-SNAPSHOT.jar
```


```shell
app register --name apache-geode-sink --type sink --uri file:///Users/Projects/VMware/Tanzu/SCDF/dev/scdf-extensions/applications/apache-geode-sink/target/apache-geode-sink-0.0.1-SNAPSHOT.jar
```

```shell
app register --name bank-geode-sink --type sink --uri file:///Users/Projects/VMware/Tanzu/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase/applications/bank-geode-sink/target/bank-geode-sink-0.0.1-SNAPSHOT.jar
```


## Start ATM Rest

```shell
cd /Users/Projects/VMware/Tanzu/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase
```

```shell
java -jar applications/atm-rest-service/target/atm-rest-service-0.0.1-SNAPSHOT.jar --server.port=9000
```




```shell
open http://localhost:9000
```

----------------------------------------
# START Demo


In SCDF

```shell
stream create --name atm-cdc --definition "jdbc-cdc-rabbitmq-source --keyFieldExpression=key --valuePdxClassName=com.vmware.financial.open.banking.atm.domain.Atm  --spring.profiles.active=local-bank-atm |atm-geode-sink"
```

Optional

```shell
stream deploy --name atm-cdc
```

```shell
http://localhost:9393/dashboard/#/streams/list
```


```sqlite-psql
INSERT INTO bank_atms(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county,
address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time,
tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time,
friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible,
located_at, more_info, has_deposit_capability, update_ts)
VALUES('atm1', 'bank1', 'atm1', '124 Straight Street', '', '', 'Jersey City', '', 'NJ', '07305', 'US', '', '', '7:00AM-9:00PM',
'7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM',
'7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', 'true', 'truth street', 'more info', 'true',
CURRENT_TIMESTAMP);
```


```sqlite-psql
INSERT INTO bank_atms(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county,
address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time,
tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time,
friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible,
located_at, more_info, has_deposit_capability, update_ts)
VALUES('atm2', 'bank1', 'atm2', '224 Straight Street', '', '', 'Jersey City', '', 'NJ', '07305', 'US', '', '', '7:00AM-9:00PM',
'7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM',
'7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', 'true', 'truth street', 'more info', 'true',
CURRENT_TIMESTAMP);
```

```shell
curl -X 'GET' \
  'http://localhost:4002/obp/v4.0.0/banks/bank1/atms/atm1' \
  -H 'accept: */*'
```



## Start Bank Account service

```shell
java -jar applications/bank-account-rest-service/target/bank-account-rest-service-0.0.1-SNAPSHOT.jar --server.port=4002
```

```shell
open http://localhost:4002
```

Create Account

```json
{
  "account_id": "acct1",
  "user_id": "nyla",
  "label": "00232-2323-23232",
  "product_code": "CHECKING",
  "balance": {
    "amount": 1000000,
    "currency": "string"
  },
  "account_routings": [
    {
      "address": "000000",
      "scheme": "NA"
    }
  ],
  "branch_id": "BranchWood"
}
```


http://localhost:7071/geode/swagger-ui.html

```sqlite-sql
select * from /BankAccount where balance.amount > 10
```

```shell
open http://localhost:15672/
```

-------------------

## Bank

```shell
java -jar applications/bank-rest-service/target/bank-rest-service-0.0.1-SNAPSHOT.jar  --server.port=4003
```

```shell
open http://localhost:4003
```

POST

```json
{
  "id": "bank1",
  "short_name": "1st Bank",
  "full_name": "The National Bank of Charlotte",
  "logo": "vmware.com/logo.png",
  "website": "http://vmware.com",
  "bank_routings": [
    {
      "address": "00000",
      "scheme": "ABC"
    }
  ]
}
```

create lucene index --name=bank_full_name --region=Bank --field=full_name

```json
[
  { "@type": "String", "@value" : "bank_full_name"},
  { "@type": "String", "@value" : "Bank"},
  { "@type": "String", "@value" : "Charlotte"},
  { "@type": "String", "@value" : "full_name"}
]
```


```shell
curl -X POST "http://localhost:7071/geode/v1/functions/SimpleLuceneSearchFunction?onRegion=Bank" -H "accept: application/json;charset=UTF-8" -H "Content-Type: application/json" -d "[ { \"@type\": \"String\", \"@value\" : \"bank_full_name\"}, { \"@type\": \"String\", \"@value\" : \"Bank\"}, { \"@type\": \"String\", \"@value\" : \"Charlotte\"}, { \"@type\": \"String\", \"@value\" : \"full_name\"}]"
```

---------------

Test Schema Constraints

```shell
put --key="deleteme" --value="invalid" --region=/Atm
````


## RabbitMQ Demo


Adding routing binding

```

Exchange: banking.bank
 Queue: banking.bank.jersey
 Routing Key: jersey.#

 Queue: banking.bank.all
 Routing Key: #
```

routing_key: jersey.bank
```json
{
  "id": "nj",
  "short_name": "Jersey Bank",
  "full_name": "The National Bank of Jersey",
  "logo": "vmware.com/logo.png",
  "website": "http://vmware.com",
  "bank_routings": [
    {
      "address": "00000",
      "scheme": "ABC"
    }
  ]
}
```


routing_key: NY
```json
{
  "id": "NY",
  "short_name": "NY Bank",
  "full_name": "The National Bank of NY",
  "logo": "vmware.com/logo.png",
  "website": "http://vmware.com",
  "bank_routings": [
    {
      "address": "00000",
      "scheme": "ABC"
    }
  ]
}
```

Add Banking VMware


```shell
java -jar applications/bank-geode-sink/target/bank-geode-sink-0.0.1-SNAPSHOT.jar
```


```shell
Queue: banking.bank.bankingBankStream
Routing Key: VMware.#
```

routing_key: VMware.Bank1
```json
{
  "id": "VMware1",
  "short_name": "VMware Bank1",
  "full_name": "The National Bank of VMware1",
  "logo": "vmware.com/logo.png",
  "website": "http://vmware.com",
  "bank_routings": [
    {
      "address": "00001",
      "scheme": "VMware"
    }
  ]
}
```


```shell
open http://localhost:4003
```


GET VMware1

----------------

## RabbitMQ TTL

test-ttl

8000

8 seconds


------------------
## GemFire TTL


```shell
destroy region --name=TestTTL
create region --name=TestTTL --eviction-action=local-destroy --eviction-max-memory=10000 --entry-time-to-live-expiration=10 --entry-time-to-live-expiration-action=DESTROY --enable-statistics=true --type=PARTITION
```

```shell
put --key=hello --value="Hello world, I am still in the cache" --region=/TestTTL
get --key=hello --region=/TestTTL
```
