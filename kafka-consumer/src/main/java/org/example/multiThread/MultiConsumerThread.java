package org.example.multiThread;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiConsumerThread {

	private final static String TOPIC_NAME = "test";
	private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";
	private final static String GROUP_ID = "test-group";
	// 스레드 개수를 지정
	private final static int CONSUMER_COUNT = 3;

	public static void main(String[] args) {
		// KafkaConsumer 인스턴스를 생성하기 위한 옵션들을 지정
		Properties configs = new Properties();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		ExecutorService executorService = Executors.newCachedThreadPool();
		// CONSUMER_COUNT 개의 스레드를 실행한다.
		for (int i = 0; i < CONSUMER_COUNT; i++) {
			ConsumerWorker worker = new ConsumerWorker(configs, TOPIC_NAME, i);
			executorService.execute(worker);
		}
	}
}
