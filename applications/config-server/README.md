
In Apache Gedoe 1.14 and higher

```shell
start locator --name=locator
start server --compatible-with-redis-bind-address=localhost --compatible-with-redis-port=6379
```



The properties should be stored as fields in a hash. The name of hash should be the same as spring.application.name property or conjunction of spring.application.name and spring.profiles.active[n].

```shell
HMSET sample-app server.port "8100" sample.topic.name "test" test.property1 "property1"
```
After executing the command visible above a hash should contain the following keys with values:

```shell
HGETALL sample-app
{
"server.port": "8100",
"sample.topic.name": "test",
"test.property1": "property1"
}
```


Testing

```shell
curl http://localhost:8888/sample-app/default
```

Output

```json
{
  "name":"sample-app",
  "profiles":["default"],
  "label":null,
  "version":null,
  "state":null,
  "propertySources":[
    {
      "name":"redis:sample-app-default",
      "source":{}},
    {
      "name":"redis:sample-app",
      "source":{
        "test.property1":"property1",
        "server.port":"8100",
        "sample.topic.name":"test"}}]
}
```
