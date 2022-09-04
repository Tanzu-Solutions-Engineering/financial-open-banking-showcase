# Installation

------------------
# ATM 

## Change Data Capture


```sqlite-sql
CREATE TABLE bank_atms (
atm_id varchar(50) NOT NULL,
bank_id varchar(50) NULL,
atm_name varchar(50) NULL,
address_line_1 varchar(50) NULL,
address_line_2 varchar(50) NULL,
address_line_3 varchar(50) NULL,
address_city varchar(50) NULL,
address_county varchar(50) NULL,
address_state varchar(50) NULL,
address_postcode varchar(50) NULL,
address_country_code varchar(50) NULL,
meta_license_id varchar(50) NULL,
meta_license_name varchar(50) NULL,
monday_opening_time varchar(50) NULL,
monday_closing_time varchar(50) NULL,
tuesday_opening_time varchar(50) NULL,
tuesday_closing_time varchar(50) NULL,
wednesday_opening_time varchar(50) NULL,
wednesday_closing_time varchar(50) NULL,
thursday_opening_time varchar(50) NULL,
thursday_closing_time varchar(50) NULL,
friday_opening_time varchar(50) NULL,
friday_closing_time varchar(50) NULL,
saturday_opening_time varchar(50) NULL,
saturday_closing_time varchar(50) NULL,
sunday_opening_time varchar(50) NULL,
sunday_closing_time varchar(50) NULL,
is_accessible varchar(50) NULL,
located_at varchar(50) NULL,
more_info varchar(50) NULL,
has_deposit_capability varchar(50) NULL,
update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Setup

ATM CDC App

```shell
k apply -f cloud/k8/apps/cdc/atm/jdbc-cdc-rabbitmq-source
```
```shell
k apply -f cloud/k8/apps/atm-geode-sink
```

```shell
k apply -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
```

```shell
k port-forward deployments/atm-rest-service 4002:4002
```


```shell
curl -X 'GET' \
  'http://localhost:4002/banks/bank1/atms/atm1' \
  -H 'accept: */*'
```
## ATM Testing


```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_atms(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county, address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time, tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time, friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible, located_at, more_info, has_deposit_capability, update_ts) VALUES('atm1', 'bank1', 'atm1', '124 Straight Street', '', '', 'Jersey City', '', 'NJ', '07305', 'US', '', '', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', 'true', 'truth street', 'more info', 'true', CURRENT_TIMESTAMP);"
```


```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_atms(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county, address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time, tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time, friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible, located_at, more_info, has_deposit_capability, update_ts) VALUES('atm2', 'bank2', 'atm1', '124 Straight Street', '', '', 'Jersey City', '', 'NJ', '07305', 'US', '', '', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', 'true', 'truth street', 'more info', 'true', CURRENT_TIMESTAMP);"
```

Update all

```shell
kubectl exec -it postgres-0 -- psql -c "update   bank_atms set  update_ts = CURRENT_TIMESTAMP"
```


-----------------------

# Bank

```sqlite-sql
CREATE TABLE banks (
bank_id varchar(50) PRIMARY KEY,
short_name varchar(50) NOT NULL,
full_name varchar(50) NOT NULL,
logo varchar(50) NULL,
website varchar(50) NOT NULL,
bank_routings varchar(50) NULL,
update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP);
```


Example Query

```sqlite-sql
select bank_id as id, bank_id, short_name,full_name, 
logo, website, bank_routings, update_ts
from banks
```

## Bank CDC Testing

```sqlite-sql
INSERT INTO banks (bank_id, short_name, full_name, logo, website, bank_routings) VALUES('bank1', 'bank1', 'bank1', 'logo', 'website', null);
```

```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO banks (bank_id, short_name, full_name, logo, website, bank_routings) VALUES('bank1', 'bank1', 'bank1', 'logo', 'website', null);"

kubectl exec -it postgres-0 -- psql -c "update banks set update_ts = CURRENT_TIMESTAMP"


kubectl port-forward deployment/bank-rest-service 4003:4003

```


```shell
curl -X 'GET' \
  'http://localhost:4003/banks/bank1' \
  -H 'accept: */*';echo
```

-----------------------

# Account

Table DDL

```sqlite-sql

CREATE TABLE bank_accounts (
	acct_id text NOT NULL,
	bank_id text NOT NULL,
	acct_label text NOT NULL,
	acct_number text NOT NULL,
	acct_product_cd text NOT NULL,
	acct_routings text NOT NULL,
	acct_views_basic text NULL,
	acct_balance numeric NULL DEFAULT 0,
	update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT bank_accounts_pkey PRIMARY KEY (acct_id, bank_id)
);
```

## Deploy Account Applications

CDC Source

```shell
k apply -f cloud/k8/apps/cdc/account/jdbc-cdc-rabbitmq-source
```

Geode/GemFire Sink

```shell
k apply -f cloud/k8/apps/cdc/account/apache-geode-sink
```


Bank Account REST

```shell
k apply -f cloud/k8/apps/account-rest-service
```

```shell
k port-forward deployment/bank-account-rest-service 4001:4001
```

```shell
open http://localhost:4001
```


## Account CDC Testing

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "query --query='select key,balance,account_routings,views_basic from /Account'"
```

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "remove --region=/Account --key='1|1'"
```


```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_accounts (acct_id, bank_id, acct_label, acct_number, acct_product_cd, acct_balance,acct_routings,acct_views_basic, update_ts) VALUES('1', '1',  'account_label', 'account_nm', 'product_cd', 10, 'acct_routings','acct_views_basic', CURRENT_TIMESTAMP);"
```

```shell
curl -X 'GET' \
  'http://localhost:4001/banks/1/accounts/1' \
  -H 'accept: */*';echo
```

```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_accounts (acct_id, bank_id, acct_label, acct_number, acct_product_cd, acct_balance, update_ts) VALUES('2', '1',  'account_label', 'account_nm', 'product_cd', 2000000000020.23, CURRENT_TIMESTAMP);"
```

```shell
curl -X 'GET' 'http://localhost:4001/banks/1/accounts/2' \
-H 'accept: */*';echo
```

```shell
kubectl exec -it postgres-0 -- psql -c "delete from bank_accounts "
```


```shell
kubectl exec -it postgres-0 -- psql -c "UPDATE bank_accounts set ACCT_BALANCE = 30.50, update_ts = CURRENT_TIMESTAMP where acct_id = '1' and bank_id = '1'"
```

```shell
kubectl exec -it postgres-0 -- psql -c "SELECT  concat( acct_id,'|',bank_id) as key,acct_id,  acct_id id, bank_id, acct_label as label, acct_number number, acct_product_cd product_code, acct_routings, acct_views_basic views_basic, concat('{\"amount\":',acct_balance,'}') balance, update_ts FROM bank_accounts"
```

Update all

```shell
kubectl exec -it postgres-0 -- psql -c "UPDATE bank_accounts set update_ts = CURRENT_TIMESTAMP"
```

```postgres-sql
CREATE TABLE BANK_ACCOUNTS ( ACCT_ID  TEXT  NOT NULL, BANK_ID  TEXT  NOT NULL, ACCT_LABEL  TEXT NOT NULL, ACCT_NUMBER TEXT NOT NULL, ACCT_PRODUCT_CD TEXT NOT NULL, ACCT_ROUTINGS TEXT NOT NULL, ACCT_VIEWS_BASIC  TEXT, ACCT_BALANCE NUMERIC DEFAULT 0, UPDATE_TS TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(ACCT_ID, BANK_ID));
```


