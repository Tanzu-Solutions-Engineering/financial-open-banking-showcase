#!/bin/bash

export PROJECT_DIR=`pwd`

kubectl create namespace accounting
kubectl config set-context --current --namespace=accounting

kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD --namespace=accounting

STR=`pwd`
if [[ "$STR" == *"cloud"* ]]; then
  echo "You must execute this script from the root project directory"
  exit 0
fi

#
##PRE_REQUISUITE
#if ! command -v yq &> /dev/null
#then
#    echo "Please install yq See https://github.com/mikefarah/yq/releases/tag/3.3.0"
#    exit
#fi


#if ! command -v kbld &> /dev/null
#then
#    echo "Please install kbld See https://github.com/vmware-tanzu/carvel-kbld/releases/download/v0.30.0"
#    exit
#fi

#if ! command -v kapp &> /dev/null
#then
#    echo "Please install kapp See https://github.com/vmware-tanzu/carvel-kapp/releases/download/v0.39.0"
#    exit
#fi

if [ -z $HARBOR_USER ]
then
  echo "Please set the your username and password used to login into registry.pivotal.io .profile on on the shell. Example: export \$HARBOR_USER=MYUSER; export=\$HARBOR_PASSWORD==MYPASSWORD "
  exit
fi

if [ -z $HARBOR_PASSWORD ]
then
  echo "Please set the your username and password used to login into registry.pivotal.io in .profile on on the shell. Example: export \$HARBOR_USER=MYUSER; export=\$HARBOR_PASSWORD==MYPASSWORD "
  k get p
fi

set -x #echo on

# Install GemFire
./deployment/cloud/k8/data-services/gemfire/gf-k8-setup.sh

# Install RabbitMQ

cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuRabbitMQ/dev/tanzu-rabbitmq-event-streaming-showcase/deployment/cloud/k8/data-services/rabbitmq/install_commericial/
./rabbitmq-commericial.sh


cd $PROJECT_DIR

#-----------------------------------------

#Install GemFire Cluster
kubectl apply -f deployment/cloud/k8/data-services/gemfire/redis/gf-multi-site-redis.yaml

#Install RabbitMQ
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yaml

sleep 30

#wait for rabbit
kubectl wait pod -l=app.kubernetes.io/component=rabbitmq --for=condition=Ready --timeout=160s --namespace=accounting
# create rabbitmq users
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/secret/users/rabbitmq_account_user.yaml

#wait for gemfire
kubectl wait pod -l=app.kubernetes.io/component=gemfire-locator --for=condition=Ready --timeout=160s --namespace=accounting
sleep 20
kubectl wait pod -l=app.kubernetes.io/component=gemfire-server --for=condition=Ready --timeout=160s --namespace=accounting


# Rest service
kubectl apply -f deployment/cloud/k8/apps/account-rest-service/account-rest-redis-service.yaml

# Sink application
kubectl apply -f deployment/cloud/k8/apps/bank-account-sink/bank-account-redis-sink.yaml


#Source applications
kubectl apply -f deployment/cloud/k8/apps/http-amqp-source/http-amqp-source.yaml