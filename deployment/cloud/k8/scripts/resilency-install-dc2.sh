#!/bin/bash

export PROJECT_DIR=`pwd`

kubectl create namespace accounting-dc2
kubectl config set-context --current --namespace=accounting-dc2

kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD --namespace=accounting-dc2

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
  exit
fi

set -x #echo on

# Install RabbitMQ

cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuRabbitMQ/dev/tanzu-rabbitmq-event-streaming-showcase/deployment/cloud/k8/data-services/rabbitmq/install_commericial/
./rabbitmq-commericial.sh


cd $PROJECT_DIR

#install postgres operator
./deployment/cloud/k8/data-services/postgres/postgres-setup.sh


#-----------------------------------------
#install postgres database
kubectl apply -f ./deployment/cloud/k8/data-services/postgres/ha/postgres-db-ha.yaml

sleep 10
#Install RabbitMQ
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yaml

sleep 10

#wait for rabbit
kubectl wait pod -l=app.kubernetes.io/component=rabbitmq --for=condition=Ready --timeout=160s --namespace=accounting-dc2

#wait for postgres
kubectl wait pod -l=postgres-instance=postgres-db --for=condition=Ready --timeout=160s --namespace=accounting-dc2


# create streaming vhost
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/dc2/rabbit-stream-vhost.yaml

# create rabbitmq users
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/secret/users/rabbitmq_account_user_dc2.yaml



# Rest service
kubectl apply -f deployment/cloud/k8/apps/account-global-service/account-global-service.yaml

# Sink application
kubectl apply -f deployment/cloud/k8/apps/account-global-sink/account.global.consumer.yaml

#Create shovel
kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/secret/shovel/dc1-to-dc2-accounts.yml

kubectl describe shovel dc1-dc2-account-shovel

kubectl logs rabbitmq-server-0
