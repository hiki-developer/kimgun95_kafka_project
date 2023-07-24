package org.example.kstreamGlobalktableJoin;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

public class KStreamJoinGlobalKTable {

	private static String APPLICATION_NAME = "global-table-join-application";
	private static String BOOTSTRAP_SERVERS = "my-kafka:9092";
	private static String ADDRESS_GLOBAL_TABLE = "address_v2";
	private static String ORDER_STREAM = "order";
	private static String ORDER_JOIN_STREAM = "order_join";

	public static void main(String[] args) {
		// 언뜻 결과만 보면 KTable과 다르지 않아 보일 수 있다.
		// GlobalKTable로 선언한 토픽은 토픽에 존재하는 모든 데이터를 태스크마다 저장하고 조인 처리를 수행할 수 있다.
		// 그리고 조인 수행시 KStream의 메시지 키뿐만 아니라 값을 기준으로도 매칭이 가능하다.

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();
		// GlobalKTable 생성
		GlobalKTable<String, String> addressGlobalTable = builder.globalTable(ADDRESS_GLOBAL_TABLE);
		KStream<String, String> orderStream = builder.stream(ORDER_STREAM);

		// GlobalKTable은 KTable 조인과 다르게 레코드를 매칭할 때 KStream의 메시지 키와 값 둘 다 사용 가능
		// 	- 여기선 key로 설정
		orderStream.join(addressGlobalTable,
				(orderKey, orderValue) -> orderKey,
				(order, address) -> order + " send to " + address)
			.to(ORDER_JOIN_STREAM);

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

	}
}
