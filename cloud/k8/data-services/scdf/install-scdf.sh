#!/bin/bash



kubectl create secret docker-registry scdf-image-regcred --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl apply -f cloud/k8/data-services/scdf/services/dev/postgresql/secret.yaml
kubectl apply -f cloud/k8/data-services/scdf/services/dev/rabbitmq/config.yaml
kubectl apply -f cloud/k8/data-services/scdf/services/dev/rabbitmq/secret.yaml

kubectl apply -f cloud/k8/data-services/scdf/services/dev/skipper.yaml
kubectl wait pod -l=app=skipper --for=condition=Ready   --timeout=160s


kubectl apply -f cloud/k8/data-services/scdf/services/dev/data-flow.yaml
