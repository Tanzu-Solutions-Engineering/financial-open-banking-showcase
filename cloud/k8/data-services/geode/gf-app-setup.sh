

kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Atm --type=PARTITION"
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Bank --type=PARTITION"
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Account --type=PARTITION"
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=CdcRecord --type=PARTITION"
