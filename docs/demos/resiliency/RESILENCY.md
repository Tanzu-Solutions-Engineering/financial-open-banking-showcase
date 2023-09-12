# Prerequisite


- GemFire Operator
- RabbitMQ Operator



# install

```shell
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yaml
```

```shell
kubectl apply -f deployment/cloud/k8/apps/http-amqp-source/http-amqp-source.yaml
```