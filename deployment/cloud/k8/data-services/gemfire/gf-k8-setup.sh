#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.12.4/cert-manager.yaml
kubectl get pods --namespace cert-manager


kubectl wait pod -l=app.kubernetes.io/instance=cert-manager --for=condition=Ready --timeout=160s --namespace=cert-manager

kubectl create namespace gemfire-system

kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create rolebinding psp-gemfire --namespace=gemfire-system --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default
sleep 5s

# Install the GemFire Operator
helm registry login -u $HARBOR_USER -p $HARBOR_PASSWORD registry.tanzu.vmware.com

helm install gemfire-crd oci://registry.tanzu.vmware.com/tanzu-gemfire-for-kubernetes/gemfire-crd --version 2.2.0 --namespace gemfire-system --set operatorReleaseName=gemfire-operator
helm install gemfire-operator oci://registry.tanzu.vmware.com/tanzu-gemfire-for-kubernetes/gemfire-operator --version 2.2.0 --namespace gemfire-system


kubectl wait pod -l=app.kubernetes.io/component=gemfire-controller-manager --for=condition=Ready --timeout=160s --namespace=gemfire-system


 k get pods --namespace gemfire-system

kubectl apply -f cloud/k8/data-services/geode/gemfire.yml

kubectl wait pod -l=app=gemfire1-server --for=condition=Ready --timeout=160s


# Install Gateway
kubectl apply -f https://projectcontour.io/quickstart/contour-gateway-provisioner.yaml
kubectl --namespace projectcontour get deployments
