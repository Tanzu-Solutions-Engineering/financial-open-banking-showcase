#!/bin/bash



export ACCT_USER_PWD=`kubectl get secrets/rabbit-accounting-credentials-secrete --template={{.data.password}} | base64 -d`


#helm uninstall scdf

helm install scdf oci://registry-1.docker.io/bitnamicharts/spring-cloud-dataflow --set externalRabbitmq.enabled=true --set rabbitmq.enabled=false --set externalRabbitmq.host=rabbitmq --set externalRabbitmq.username=account --set externalRabbitmq.password=$ACCT_USER_PWD --set server.service.type=LoadBalancer --set server.service.ports.http=9393

#Switch to DC2

#helm install scdf oci://registry-1.docker.io/bitnamicharts/spring-cloud-dataflow --set externalRabbitmq.enabled=true --set rabbitmq.enabled=false --set externalRabbitmq.host=rabbitmq --set externalRabbitmq.username=account --set externalRabbitmq.password=$ACCT_USER_PWD --set server.service.type=LoadBalancer --set server.service.ports.http=9393


# ----

 export SERVICE_PORT=$(kubectl get --namespace accounting -o jsonpath="{.spec.ports[0].port}" services scdf-spring-cloud-dataflow-server)
    kubectl port-forward --namespace accounting svc/scdf-spring-cloud-dataflow-server ${SERVICE_PORT}:${SERVICE_PORT} &
    echo "http://127.0.0.1:${SERVICE_PORT}/dashboard"
