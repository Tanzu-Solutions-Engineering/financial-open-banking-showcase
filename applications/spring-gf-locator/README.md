# Start App

export JAVA_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED'

--add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED  --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED


java.management does not export com.sun.jmx.remote.security

```properties
gemfire.distributed-system-id=10
gemfire.remote-locators=localhost[12480]
geode.distributed-system.remote.id=20
spring.application.name=BootGemFireMultiSiteCachingServerApplication-Site1
#spring.profiles.include=locator-manager,gateway-receiver,gateway-sender
spring.data.gemfire.locator.port=11235
spring.data.gemfire.manager.port=1199
```
Register 


## WAN

--spring.data.gemfire.locator.port=10100 --gemfire.remote-locators=10200


Docker

```shell
cd applications/spring-gf-locator
mvn spring-boot:build-image

```


```shell script
docker tag spring-gf-locator:0.1.1-SNAPSHOT cloudnativedata/spring-gf-locator:0.1.1-SNAPSHOT 
docker login
docker push cloudnativedata/spring-gf-locator:0.1.1-SNAPSHOT

```