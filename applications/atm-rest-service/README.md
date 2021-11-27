# Setup

## k8

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Atm --type=PARTITION"
```

# Build Docker


```shell
mvn install
cd applications/atm-rest-service
mvn spring-boot:build-image

docker tag atm-rest-service:0.0.1-SNAPSHOT cloudnativedata/atm-rest-service:0.0.1-SNAPSHOT
docker push cloudnativedata/atm-rest-service:0.0.1-SNAPSHOT
```


Optional on Kind

```shell
kind load docker-image atm-rest-service:0.0.1-SNAPSHOT
```


```shell
k apply -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
k delete -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
```

```shell
k port-forward deployments/atm-rest-service 4002:4002
```

```sqlite-sql

INSERT INTO bank_atms
(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county, address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time, tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time, friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible, located_at, more_info, has_deposit_capability, update_ts)
VALUES('atm1', 'bank1', 'atm1', '124 Straight Street', '', '', 'Jersey City', '', 'NJ', '07305', 'US', '', '', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', 'true', 'truth street', 'more info', 'true', CURRENT_TIMESTAMP);

```


```shell
kubectl exec -it postgres-0 -- psql -c "INSERT INTO bank_atms(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county, address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time, tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time, friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible, located_at, more_info, has_deposit_capability, update_ts) VALUES('atm1', 'bank1', 'atm1', '124 Straight Street', '', '', 'Jersey City', '', 'NJ', '07305', 'US', '', '', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', '7:00AM-9:00PM', 'true', 'truth street', 'more info', 'true', CURRENT_TIMESTAMP);"
```

```shell
kubectl exec -it postgres-0 -- psql -c "update   bank_atms set  update_ts = CURRENT_TIMESTAMP"
```


```shell
kubectl exec -it postgres-0 -- psql -c "delete from   bank_atms"
```


```shell
kubectl exec -it postgres-0 -- psql -c "select * from bank_atms"
```
