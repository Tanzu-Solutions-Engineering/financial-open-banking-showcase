#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true

kubectl create namespace gemfire-system


kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create rolebinding psp-gemfire --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default

kubectl create rolebinding psp-gemfire --namespace=gemfire-system --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default
kubectl create rolebinding psp-gemfire --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default

# Install the GemFire Operator
sleep 40
helm install gemfire-operator ~/dataServices/gemfire/gemfire-operator-1.0.2.tgz --namespace gemfire-system

sleep 30s
kubectl apply -f cloud/k8/data-services/geode/gemfire.yml
