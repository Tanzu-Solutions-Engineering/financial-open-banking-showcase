# Prerequisite


- RabbitMQ
- Postgres

- GemFire for Redis

```shell
export JAVA_ARGS="--add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED  --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED"
export PROJECT_DIR=`pwd`
cd $PROJECT_DIR/deployment/local/runtime/gf1
java -jar $PROJECT_DIR/applications/spring-gf-locator/target/spring-gf-locator-0.1.1-SNAPSHOT.jar --spring.profiles.active=site1
```

-----------------------

Spring GemFure

```shell
k apply -f deployment/cloud/k8/data-services/gemfire/redis/spring-redis/spring-gf-locator.yaml
```

----------------------

```shell
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yaml
```

```shell
kubectl apply -f deployment/cloud/k8/apps/http-amqp-source/http-amqp-source.yaml
```