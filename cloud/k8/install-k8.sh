#!/bin/bash


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


if [ -z $POSTGRES_OPERATOR_DOWNLOAD_DIR ]
then
  echo "Please set \POSTGRES_OPERATOR_DOWNLOAD_DIR to the location where your download postgres operator See http://network.pivotal.io"
  exit
fi

if [ -z $GEMFIRE_OPERATOR_DOWNLOAD_DIR ]
then
  echo "Please set \$GEMFIRE_OPERATOR_DOWNLOAD_DIR to the location where your download gemfire-operator-1.0.1.tgz. See http://network.pivotal.io"
  exit
fi


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
./cloud/k8/data-services/geode/gf-k8-setup.sh
./cloud/k8/data-services/geode/gf-app-setup.sh


# Install Postgres
./cloud/k8/data-services/postgres/postgres-setup.sh

# Install RabbitMQ
./cloud/k8/data-services/rabbitmq/rabbit-k8-setup.sh

#-----------------------------------------

kubectl apply -f cloud/k8/apps/account-rest-service


kubectl apply -f cloud/k8/apps/atm-geode-sink
kubectl apply -f cloud/k8/apps/atm-rest-service

kubectl apply -f cloud/k8/apps/bank-geode-sink
kubectl apply -f cloud/k8/apps/bank-rest-service


kubectl apply -f cloud/k8/apps/cdc/account/apache-geode-sink
kubectl apply -f cloud/k8/apps/cdc/account/jdbc-cdc-rabbitmq-source

kubectl apply -f cloud/k8/apps/cdc/atm/jdbc-cdc-rabbitmq-source

kubectl apply -f cloud/k8/apps/cdc/bank/jdbc-cdc-rabbitmq-source