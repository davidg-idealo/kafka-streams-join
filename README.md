# kafka-streams-join
Join two streams and publish it.

Sources:
* https://github.com/confluentinc/kafka-streams-examples/blob/5.3.1-post/src/test/java/io/confluent/examples/streams/StreamToTableJoinIntegrationTest.java


start project setup with ```docker-compose up```

## Create two topics
At first you need the kafka-tools, which is a collection of shell scripts
You can download the whole package at https://www.apache.org/dyn/closer.cgi?path=/kafka/2.3.0/kafka_2.12-2.3.0.tgz

```
./kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic customer-v1
```
```
./kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic payment-v1
```

## Create console producer
```
./kafka-console-producer.sh --broker-list localhost:9092 --topic customer-v1 --property "parse.key=true" --property "key.separator=#"
```
```
./kafka-console-producer.sh --broker-list localhost:9092 --topic payment-v1 --property "parse.key=true" --property "key.separator=#"
```
##two entities
###Customer
```
{"id":"1234"}#{"name":"Robbin","age":"39","mobile":"123456789"}
```
###Payment option
```
{"id":"1234"}#{"payment":"VISA","cardnumber": "9876543210"}
```
