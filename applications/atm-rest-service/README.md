# ATM Rest Services

This [Spring Boot](https://spring.io/projects/spring-boot) service implements
basic read/write operations from the Open Bank Project [ATM Service interface](https://psd2-apiexplorer.openbankproject.com/?version=OBPv4.0.0&operation_id=OBPv3_0_0-createAtm&currentTag=ATM&api-collection-id=&bank_id=at02-0182--01&account_id=&view_id=&counterparty_id=&transaction_id=#OBPv3_0_0-createAtm).

See [GitHub project](https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase.git)

# Setup

## Local

Install and deploy a [VMware GemFire](https://docs.vmware.com/en/VMware-GemFire/10.0/gf/getting_started-installation-install_intro.html) cluster

In [Gfsh](https://docs.vmware.com/en/VMware-GemFire/10.0/gf/tools_modules-gfsh-starting_gfsh.html)

```shell
create region --name=BankAccount --type=PARTITION
```
```shell
create region --name=Atm --type=PARTITION
```

# Docker

See [Docker Hub](https://hub.docker.com/r/cloudnativedata/atm-rest-service) image

## Build


```shell
mvn clean install
cd applications/atm-rest-service
mvn spring-boot:build-image

docker tag atm-rest-service:0.0.1-SNAPSHOT cloudnativedata/atm-rest-service:0.0.1-SNAPSHOT
docker push cloudnativedata/atm-rest-service:0.0.1-SNAPSHOT
```


# Kubernetes Deployment

## K8 Setup

Install and Deploy a [GemFire cluster](https://tanzu.vmware.com/developer/data/tanzu-gemfire/guides/get-started-tgf4k8s-sbdg/)

```shell
kubectl exec gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Atm --type=PARTITION"
```

```shell
k apply -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
k delete -f cloud/k8/apps/atm-rest-service/atm-rest-service.yml
```

```shell
k port-forward deployments/atm-rest-service 4002:4002
```



-------------------
# Testing


## Create ATM

```shell
curl -X 'POST' \
  'http://localhost:4002/obp/v4.0.0/banks/gh.29.uk/atms' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{  "id":"atm-id-1x23",  
    "bank_id":"gh.29.uk",  
  "name":"Atm by the Lake",  
  "address":{    "line_1":"No 1 the Road",    "line_2":"The Place",    "line_3":"The Hill",    "city":"Berlin",    "county":"",    "state":"Brandenburg",    "postcode":"13359",    "country_code":"DE"  },  
  "location":{    "latitude":11.45,    "longitude":11.45  },  
  "meta":{    "license":{      "id":"5",      "name":"TESOBE"    }  },  "monday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  "tuesday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  "wednesday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  "thursday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  "friday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  "saturday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  "sunday":{    "opening_time":"10:00",    "closing_time":"18:00"  },  
  "is_accessible":"true",  "located_at":"Full service store",  "more_info":"short walk to the lake from here",  "has_deposit_capability":"true"}';echo
```

## Get ATM

```shell
curl -X 'GET' \
  'http://localhost:4002/obp/v4.0.0/banks/gh.29.uk/atms/atm-id-1x23' \
  -H 'accept: */*';echo
```


----

# Change Data Capture Testing


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