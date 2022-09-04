
k apply -f cloud/k8/data-services/rabbitmq/federation


export bunny_user=`kubectl get secret bunny-default-user -o jsonpath="{.data.username}"| base64 --decode`
export bunny_pwd=`kubectl get secret bunny-default-user -o jsonpath="{.data.password}"| base64 --decode`

export hare_user=`kubectl get secret hare-default-user -o jsonpath="{.data.username}"| base64 --decode`
export hare_pwd=`kubectl get secret hare-default-user -o jsonpath="{.data.password}"| base64 --decode`


kubectl exec bunny-server-0  -- rabbitmqctl add_user admin admin
kubectl exec bunny-server-0 -- rabbitmqctl set_permissions  -p / admin ".*" ".*" ".*"
kubectl exec bunny-server-0 -- rabbitmqctl set_user_tags admin administrator


kubectl exec hare-server-0  -- rabbitmqctl add_user admin admin
kubectl exec hare-server-0 -- rabbitmqctl set_permissions  -p / admin ".*" ".*" ".*"
kubectl exec hare-server-0 -- rabbitmqctl set_user_tags admin administrator

kubectl exec -it bunny-server-0 -- rabbitmqctl set_parameter federation-upstream hare-upstream '{"uri":"amqp://admin:admin@hare","expires":3600000}'

##  all exchanges whose names begin with "amq."

kubectl exec -it bunny-server-0 -- rabbitmqctl set_policy --apply-to exchanges federate-hare "^amq\." '{"federation-upstream-set":"all"}'

## Published message in hare will replicate to bunny