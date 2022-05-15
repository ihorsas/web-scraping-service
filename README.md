# web-scraping-service
https://kafka.apache.org/quickstart

```
cd kafka
```
1st terminal
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
2nd terminal
```
bin/kafka-server-start.sh config/server.properties
```
3rd terminal
```
bin/kafka-console-consumer.sh --topic pravda --from-beginning --bootstrap-server localhost:9092
```

bin/zookeeper-server-start.sh config/zookeeper.properties