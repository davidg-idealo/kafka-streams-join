# kafka-streams-join
Join two streams and publish it.

- start project setup with docker-compose up

## Create two topics

```
./kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic customer-v1
```
```
./kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic payment-v1
```

##two entities
###Customer
```{"id":"1234}:{"name":"Robbin","age":"39","mobile":"123456789"}```
###Payment option
```{"id":"1234"}:{"payment":"VISA","cardnumber": "9876543210"}```
