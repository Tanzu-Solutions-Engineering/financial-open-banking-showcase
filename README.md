
# Financial Open Banking Showcase

test

Example application on open banking and how the Tanzu portfolio helps. 

![img.png](docs/img/img.png)



Here are some useful links:

- [Open Banking Project Architecture](https://github.com/OpenBankProject/OBP-API/wiki/Open-Bank-Project-Architecture)
- [PSD2 Api Explorer](https://psd2-apiexplorer.openbankproject.com/?tags=PSD2&operation_id=OBPv3_0_0-getCoreTransactionsForBankAccount&currentTag=Transaction&bank_id=at02-0182--01&account_id=&view_id=&counterparty_id=&transaction_id=)


# Database integration example

## Oracle


Startup Oracle in docker

```shell script
docker pull truevoly/oracle-12c
mkdir -p ~/oracle_data
docker run -d -p 8088:8080 -p 1521:1521 -v ~/oracle_data/:/u01/app/oracle truevoly/oracle-12c
```



```json
{
  "id": "000121",
  "bank_id": "VMware",
  "label": "Testing",
  "number": "ABC1212",
  "product_code": "ABL",
  "balance": {
    "currency": "US",
    "amount": 125000
  },
  "account_routings": [
    {
      "scheme": "test",
      "address": "1 Straight Street"
    }
  ],
  "views_basic": [
    "all"
  ]
}
```
