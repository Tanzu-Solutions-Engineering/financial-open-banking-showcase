


```shell
docker pull truevoly/oracle-12c
mkdir -p ~/oracle_data
docker run -d -p 8088:8080 -p 1521:1521 -v ~/oracle_data/:/u01/app/oracle truevoly/oracle-12c
```


```shell
user: system
password: oracle
sid: xe
localhhost 2521
```

```properties
cdc.connector=oracle
cdc.name=my-oracle
cdc.config.database.server.name=xe
cdc.config.database.user=system
cdc.config.database.password=oracle
cdc.config.database.hostname=localhost
cdc.config.database.port=2521
cdc.schema=true
cdc.flattering.enabled=true
```

Run applications/database-sor-migration


```shell
jdbc --cron='0/20 * * * * ? *' --password=oracle --driver-class-name=oracle.jdbc.OracleDriver --username=system --url='jdbc:oracle:thin:@localhost:1521:xe' --update='update accouts set update_ts = current_timestamp' --query='select * from accounts' | geode --region-name=test --key-expression=payload.id --host-addresses=localhost:10334 --connect-type=locator
```


-------------------
# Not working

Shell definition

```shell
cdc-debezium --table.include.list=accounts --cdc.connector=oracle --cdc.name=my-oracle --cdc.config.database.server.name=xe --cdc.config.database.user=system --cdc.config.database.password=oracle --cdc.config.database.hostname=localhost --cdc.config.database.port=2521 --cdc.schema=true --cdc.flattering.enabled=true | geode --region-name=test --key-expression=payload.id --host-addresses=localhost:10334 --connect-type=locator
```

---

app.cdc-debezium.cdc.config.database.hostname=localhost
app.cdc-debezium.cdc.config.database.password=******
app.cdc-debezium.cdc.config.database.port=2521
app.cdc-debezium.cdc.config.database.server.name=xe
app.cdc-debezium.cdc.config.database.user=system
app.cdc-debezium.cdc.connector=oracle
app.cdc-debezium.cdc.flattering.enabled=true
app.cdc-debezium.cdc.name=my-oracle
app.cdc-debezium.cdc.schema=true
app.cdc-debezium.table.include.list=accounts
app.geode.connect-type=locator
app.geode.host-addresses=localhost:10334
app.geode.key-expression=payload.id
app.geode.region-name=test