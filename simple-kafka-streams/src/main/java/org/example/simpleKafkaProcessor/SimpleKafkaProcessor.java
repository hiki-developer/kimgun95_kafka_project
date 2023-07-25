package org.example.simpleKafkaProcessor;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

public class SimpleKafkaProcessor {

	private static String APPLICATION_NAME = "processor-application";
	private static String BOOTSTRAP_SERVERS = "my-kafka:9092";
	private static String STREAM_LOG = "stream_log";
	private static String STREAM_LOG_FILTER = "stream_log_filter";

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		// 프로세서 API를 사용한 토폴로지를 구성하기 위해 사용
		Topology topology = new Topology();
		// 소스 프로세서를 가져오기 위해 addSource() 사용. 소스프로세서의 이름, 토픽 이름을 파라미터로 넣는다.
		// 스트림 프로세서를 사용하기 위해 addProcessor() 사용. 스트림 프로세서의 이름, 사용자 정의 프로세서 인스턴스, 부모 노드를 파라미터로 넣는다.
		// 싱크 프로세서를 사용하기 위해 addSink() 사용. 싱크 프로세서의 이름, 저장할 토픽의 이름, 부모 노드를 파라미터로 넣는다.
		topology.addSource("Source",
				STREAM_LOG)
			.addProcessor("Process",
				() -> new FilterProcessor(),
				"Source")
			.addSink("Sink",
				STREAM_LOG_FILTER,
				"Process");

		// 카프카 스트림즈 생성 및 실행
		KafkaStreams streaming = new KafkaStreams(topology, props);
		streaming.start();
	}
}
