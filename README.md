
# Financial Open Banking Showcase

test

Example application on open banking and how the Tanzu portfolio helps. 

![img.png](docs/img/img.png)



Here are some useful links:

- [Open Banking Project Architecture](https://github.com/OpenBankProject/OBP-API/wiki/Open-Bank-Project-Architecture)
- [PSD2 Api Explorer](https://psd2-apiexplorer.openbankproject.com/?tags=PSD2&operation_id=OBPv3_0_0-getCoreTransactionsForBankAccount&currentTag=Transaction&bank_id=at02-0182--01&account_id=&view_id=&counterparty_id=&transaction_id=)


# Data REST Services

The following is the list of services that implement the Open Bank API 

Service                                                                     |       Notes
-------------------------------------------------------------------------   |  ----------------------------
[atm-rest-service](applications/atm-rest-service)                           | ATM REST API
[bank-account-rest-service](applications/bank-account-rest-service)         | Account REST API
[bank-rest-service](applications/bank-rest-service)                         | Bank REST API

# Brown-Field Integration

## Database 

See the following for a Change Data Capture ([CDC](docs/cdc)) example to integrate existing account information from a 
relational database (such as Postgres) 
using [RabbitMQ](https://tanzu.vmware.com/rabbitmq), a data cache [GemFire](https://tanzu.vmware.com/gemfire)
and [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream).

