# Prerequisite

- RabbitMQ
- Postgres
- GemFire


-----------------------


# Start GemFire


Download and install GemFire 10 and higher

Set Env Variable $GEMFIRE_HOME to where GemFire is installed

```shell
export GEMFIRE_HOME=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-10.1.0
```

Use wrapper script to start GemFire locally. It will also setup regions and PDX

```shell
./deployment/local/gemfire/start.sh
```