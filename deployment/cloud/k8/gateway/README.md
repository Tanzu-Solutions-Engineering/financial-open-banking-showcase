# Instructions

See Spring Cloud Gateway installation Instructions

https://docs.vmware.com/en/VMware-Spring-Cloud-Gateway-for-Kubernetes/1.0/scg-k8s/GUID-installation.html


downlaod

```shell
open https://network.pivotal.io/products/spring-cloud-gateway-for-kubernetes/
```


```shell
docker login $REGISTER_URL -u $HARBOR_USERID
```


```shell
./scripts/relocate-images.sh $REGISTER_URL
```
