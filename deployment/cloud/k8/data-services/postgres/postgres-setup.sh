#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager
kubectl create namespace sql-system


kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.12.4/cert-manager.crds.yaml



# Install Postgres
#export HELM_EXPERIMENTAL_OCI=1

helm registry login registry.tanzu.vmware.com \
       --username=$HARBOR_USER \
       --password=$HARBOR_PASSWORD

helm pull oci://registry.tanzu.vmware.com/tanzu-sql-postgres/vmware-sql-postgres-operator --version v2.2.0 --untar --untardir /tmp


kubectl create secret docker-registry regsecret --namespace=sql-system --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create secret docker-registry regsecret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

helm install postgres-operator /tmp/vmware-sql-postgres-operator/  --namespace=sql-system --namespace=sql-system  --wait