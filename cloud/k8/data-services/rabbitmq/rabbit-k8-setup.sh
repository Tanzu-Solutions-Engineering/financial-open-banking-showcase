kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"


sleep 30s
kubectl apply -f cloud/k8/data-services/rabbitmq/rabbitmq.yml