# kafka-streams-join
## Goal
Join two streams and publish the result to a new topic with using the Kafka Stream DSL.
In this simple example we have two entites the customer and the corresponding payment method.

Sources:
* https://www.confluent.io/blog/introducing-kafka-streams-stream-processing-made-simple/
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
Start the console producer with the command:
```
./kafka-console-producer.sh --broker-list localhost:9092 --topic customer-v1 --property "parse.key=true" --property "key.separator=#"
```
```
./kafka-console-producer.sh --broker-list localhost:9092 --topic payment-v1 --property "parse.key=true" --property "key.separator=#"
```
Afterwards pase the json model from each entity in the corresponding producer terminal.
## Entites data
###Customer
```
{"id":"1234"}#{"name":"Robbin","age":"39","mobile":"123456789"}
```
###Payment option
```
{"id":"1234"}#{"payment":"VISA","cardnumber": "9876543210"}
```

## Result
You should see in the output-topic:
```
{"name":"Robbin","age":"39","mobile":"123456789"}-{"payment":"VISA","cardnumber": "9876543210"}
```
