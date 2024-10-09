

Properties

spring.cloud.stream.bindings.output.producer.poller.fixed-delay=30

# Install Kafka on k8


```shell
helm uninstall kafka
```


```shell
helm install kafka oci://registry-1.docker.io/bitnamicharts/kafka \
 --set service.type=LoadBalancer  --set serviceAccount.create=true \
 --set listeners.client.protocol=PLAINTEXT \
 --set externalAccess.enabled=true  \
 --set externalAccess.autoDiscovery.enabled=true  \
 --set externalAccess.service.replicas=1      \
 --set externalAccess.broker.service.type=LoadBalancer \
 --set externalAccess.broker.service.ports.external=9094 \
 --set externalAccess.controller.service.type=LoadBalancer \
 --set externalAccess.controller.service.containerPorts.external=9094 \
 --set externalAccess.autoDiscovery.enabled=true \
 --set rbac.create=true \
 --set controller.automountServiceAccountToken=true \
 --set controller.replicaCount=1 \
 --set rbac.create=true \
 --set replicaCount=1  
```

-----------

# Running Application


export KAFKA_SERVERS=localhost:9092

```shell
java -jar applications/transaction-supplier/target/transaction-supplier-0.0.1-SNAPSHOT.jar --spring.profiles.active=kafka --spring.kafka.bootstrap-servers=$KAFKA_SERVERS --spring.cloud.stream.kafka.binder.brokers=$KAFKA_SERVERS --spring.cloud.stream.bindings.output.producer.poller.fixed-delay=30 --server.port=0 --transaction.supplier.max.count=5
```
k port-forward kafka-controller-0 9999:9092

--------------

sudo yum install java-17
