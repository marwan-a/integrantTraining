package com.javatpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class TwitterKafkaConsumer {
    private ConsumerConnector consumerConnector = null;
    private final String topic = "twitter-topic";
    public void initialize() {
          Properties props = new Properties();
          props.put("auto.offset.reset", "largest");
          props.put("zookeeper.connect", "localhost:2181");
          props.put("zookeeper.session.timeout.ms", "400");
          props.put("zookeeper.sync.time.ms", "300");
          props.put("group.id", "2");
          props.put("auto.commit.interval.ms", "1000");
          ConsumerConfig conConfig = new ConsumerConfig(props);
          consumerConnector = Consumer.createJavaConsumerConnector(conConfig);
          
    }
    public void consume() {
          //Key = topic name, Value = No. of threads for topic
          Map<String, Integer> topicCount = new HashMap<String, Integer>();
          topicCount.put(topic, new Integer(1));
          //ConsumerConnector creates the message stream for each topic
          Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams =
                consumerConnector.createMessageStreams(topicCount);
          // Get Kafka stream for topic 'mytopic'
          List<KafkaStream<byte[], byte[]>> kStreamList =
                                               consumerStreams.get(topic);
          // Iterate stream using ConsumerIterator
          for (final KafkaStream<byte[], byte[]> kStreams : kStreamList) {
                 ConsumerIterator<byte[], byte[]> consumerIte = kStreams.iterator();
                 while (consumerIte.hasNext())
                 {	String out="Message consumed from topic[" + topic + "] : "       +
                         new String(consumerIte.next().message());
                     System.out.println(out);
                 }
          }
          //Shutdown the consumer connector
          if (consumerConnector != null)   consumerConnector.shutdown();
    }
}