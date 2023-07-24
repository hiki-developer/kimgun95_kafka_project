package org.example.simpleKafkaStreams;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class SimpleStreamApplication {

	private static String APPLICATION_NAME = "streams-application";
	private static String BOOTSTRAP_SERVERS = "my-kafka:9092";
	private static String STREAM_LOG = "stream_log";
	private static String STREAM_LOG_COPY = "stream_log_copy";

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		// 스트림 토폴리지 정의. 토폴리지를 이루는 노드를 프로세서, 노드를 이은 선을 스트림이라 한다.
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> stream = builder.stream(STREAM_LOG); // 스트림 생성
		stream.to(STREAM_LOG_COPY); // 스트림 경로 설정

		// 카프카 스트림 애플리케이션 : StreamBuilder로 정의한 토폴리지와 스트림즈 실행 옵션으로 생성
		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		// 실행 시작
		streams.start();

	}
}
