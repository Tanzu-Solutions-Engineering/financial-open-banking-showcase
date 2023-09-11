
# HA RabbitMQ
- Region 1 Bank API Published - 1 Broker 1 Consumer -> 1 GemFire Redis
  - Bank API Pub-  Bank rabbit cluster 3 Brokers 1 Consumer  ->  1 GemFire Redis
  - Scale rabbit 1 to 3 nodes
  - Global Account - 3 Broker Consumer ->  1 Postgres
  - Write to Bank rabbit cluster saved to Bank API
  - Data replicated Global rabbit cluster saved to Bank API


# HA GemFire
- Region 1 Scale Bank GemFire Redis 1 -> 3 nodes
  - kill/write 1
  - Save to disk kill/write

# HA Postgres
- Region 1 Global Account - Postgres 1 + 2 read replicates
    - kill/write 1
    - Save to disk kill/write
