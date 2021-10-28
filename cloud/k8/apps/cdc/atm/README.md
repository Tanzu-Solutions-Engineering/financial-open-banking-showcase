-- pivotalmarkets.bank_atms definition

-- Drop table

-- DROP TABLE pivotalmarkets.bank_atms;


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


```shell

kubectl exec -it postgres-0 -- psql -c "CREATE TABLE bank_atms ( atm_id varchar(50) NOT NULL, bank_id varchar(50) NULL, atm_name varchar(50) NULL, address_line_1 varchar(50) NULL, address_line_2 varchar(50) NULL, address_line_3 varchar(50) NULL, address_city varchar(50) NULL, address_county varchar(50) NULL, address_state varchar(50) NULL, address_postcode varchar(50) NULL, address_country_code varchar(50) NULL, meta_license_id varchar(50) NULL, meta_license_name varchar(50) NULL, monday_opening_time varchar(50) NULL, monday_closing_time varchar(50) NULL, tuesday_opening_time varchar(50) NULL, tuesday_closing_time varchar(50) NULL, wednesday_opening_time varchar(50) NULL, wednesday_closing_time varchar(50) NULL, thursday_opening_time varchar(50) NULL, thursday_closing_time varchar(50) NULL, friday_opening_time varchar(50) NULL, friday_closing_time varchar(50) NULL, saturday_opening_time varchar(50) NULL, saturday_closing_time varchar(50) NULL, sunday_opening_time varchar(50) NULL, sunday_closing_time varchar(50) NULL, is_accessible varchar(50) NULL, located_at varchar(50) NULL, more_info varchar(50) NULL, has_deposit_capability varchar(50) NULL, update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP );"
```

```shell
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Atm --type=PARTITION"
```
```shell
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=CdcRecord --type=PARTITION"
```


```shell
k apply -f cloud/k8/apps/cdc/atm/jdbc-cdc-rabbitmq-source
```


