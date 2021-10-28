kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"


sleep 30
kubectl apply -f cloud/k8/data-services/rabbitmq/rabbitmq.yml

sleep 120


```shell
kubectl exec rabbitmq-server-0 -- rabbitmqctl add_user cdc cdc
```

```shell
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_permissions  -p / cdc ".*" ".*" ".*"
```

```shell
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_user_tags cdc administrator
```
