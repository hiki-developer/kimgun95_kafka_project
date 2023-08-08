package org.example.multiThread;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerWorker implements Runnable {
	private final static Logger logger = LoggerFactory.getLogger(ConsumerWorker.class);
	private Properties prop;
	private String topic;
	private String threadName;
	private KafkaConsumer<String, String> consumer;

	// KafkaConsumer 인스턴스에 들어갈 옵션을 생성자 변수로 담아온다.
	ConsumerWorker(Properties prop, String topic, int number) {
		this.prop = prop;
		this.topic = topic;
		this.threadName = "consumer-thread-" + number;
	}

	@Override
	public void run() {
		// KafkaConsumer 클래스는 thread-safe 하지 않음. 스레드별로 인스턴스를 별개로 만들어서 운영해야 함
		consumer = new KafkaConsumer<>(prop);
		// 생성자 변수로 받은 노픽을 명시적으로 구독하기 시작
		consumer.subscribe(Arrays.asList(topic));
		while (true) {
			// poll() 메서드를 통해 리턴받은 레코드들을 처리
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
			for (ConsumerRecord<String, String> record : records) {
				logger.info("{}", record);
			}
			consumer.commitSync();
		}
	}
}
