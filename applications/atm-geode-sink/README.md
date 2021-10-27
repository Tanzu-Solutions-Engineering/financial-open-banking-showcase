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

