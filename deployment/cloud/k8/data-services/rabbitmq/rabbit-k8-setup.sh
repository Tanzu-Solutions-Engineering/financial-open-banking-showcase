kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"


kubectl wait pod -l=app.kubernetes.io/name=rabbitmq-cluster-operator --for=condition=Ready --timeout=60s --namespace=rabbitmq-system

kubectl apply -f cloud/k8/data-services/rabbitmq/rabbitmq.yaml

sleep 30

kubectl  create secret docker-registry rabbitmq --docker-server=registry.pivotal.io  --docker-username=$HARBOR_USER \
--docker-password=$HARBOR_PASSWORD

kubectl wait pod -l=statefulset.kubernetes.io/pod-name=rabbitmq-server-0 --for=condition=Ready --timeout=360s

kubectl exec rabbitmq-server-0 -- rabbitmqctl add_user cdc cdc
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_permissions  -p / cdc ".*" ".*" ".*"
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_user_tags cdc administrator


kubectl exec rabbitmq-server-0 -- rabbitmqctl add_user oba oba
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_permissions  -p / oba ".*" ".*" ".*"
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_user_tags oba administrator

