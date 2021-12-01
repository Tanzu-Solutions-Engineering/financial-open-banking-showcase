# atm-geode-sink


Create region in Gfsh

```shell
create region --name=/Atm --type=PARTITION
```


contentType: application/json

```json
{
  "id": "id",
  "bank_id": "bank_id",
  "name": "string",
  "address": {
    "line_1": "line1",
    "line_2": "line2",
    "line_3": "line3",
    "city": "city",
    "county": "county",
    "state": "state",
    "postcode": "postcode",
    "country_code": "country_code"
  },
  "meta ": {
    "license": {
      "id": "id",
      "name":
      "name"
    }
  },
  "monday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "tuesday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "wednesday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "thursday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "friday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "saturday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "sunday": {
    "opening_time": "11:00 AM",
    "closing_time": "3:00 PM"
  },
  "is_accessible": "false",
  "located_at": "string",
  "more_info": "string",
  "has_deposit_capability": "false"
}
```


# Build Docker


```shell
mvn install 
cd applications/atm-geode-sink
mvn spring-boot:build-image
docker tag atm-geode-sink:0.0.1-SNAPSHOT cloudnativedata/atm-geode-sink:0.0.1-SNAPSHOT
docker push cloudnativedata/atm-geode-sink:0.0.1-SNAPSHOT
```


Optional on Kind

```shell
kind load docker-image atm-geode-sink:0.0.1-SNAPSHOT
```


```shell
k apply -f cloud/k8/apps/atm-geode-sink
k delete -f cloud/k8/apps/atm-geode-sink
```

```shell
k port-forward deployments/atm-geode-sink 4001:4001
```




# Testing

## Create

```shell
curl -X 'POST' \
  'http://localhost:4002/obp/v4.0.0/banks/gh.29.uk/atms' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id":"atm-id-123",
  "bank_id":"gh.29.uk",
  "name":"Atm by the Lake",
  "address":{
    "line_1":"No 1 the Road",
    "line_2":"The Place",
    "line_3":"The Hill",
    "city":"Berlin",
    "county":"",
    "state":"Brandenburg",
    "postcode":"13359",
    "country_code":"DE"
  },
  "location":{
    "latitude":11.45,
    "longitude":11.45
  },
  "meta":{
    "license":{
      "id":"5",
      "name":"TESOBE"
    }
  },
  "monday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "tuesday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "wednesday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "thursday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "friday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "saturday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "sunday":{
    "opening_time":"10:00",
    "closing_time":"18:00"
  },
  "is_accessible":"true",
  "located_at":"Full service store",
  "more_info":"short walk to the lake from here",
  "has_deposit_capability":"true"
}'
```


```shell
curl -X 'GET' \
  'http://localhost:4002/obp/v4.0.0/banks/gh.29.uk/atms/atm-id-123' \
  -H 'accept: */*';echo
```



