```shell
cd /Users/devtools/repositories/IMDG/geode/apache-geode-1.13.7
```


```shell
./gfsh
```

Start Locator

```shell
start locator --name=locator
```

```shell
configure pdx --read-serialized=true --disk-store=DEFAULT
```

Start Server
```shell
start server --name=server1
```


Create region in Gfsh

```shell
create region --name=/Atm --type=PARTITION
```

```shell
cd /Users/Projects/VMware/Tanzu/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase
```

```shell
java -jar applications/atm-rest-service/target/atm-rest-service-0.0.1-SNAPSHOT.jar --server.port=9000
```

```shell
open http://localhost:9000
```