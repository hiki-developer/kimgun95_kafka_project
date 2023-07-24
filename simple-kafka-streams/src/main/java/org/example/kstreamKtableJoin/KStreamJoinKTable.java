package org.example.kstreamKtableJoin;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class KStreamJoinKTable {

	private static String APPLICATION_NAME = "order-join-application";
	private static String BOOTSTRAP_SERVERS = "my-kafka:9092";
	private static String ADDRESS_TABLE = "address";
	private static String ORDER_STREAM = "order";
	private static String ORDER_JOIN_STREAM = "order_join";

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();
		// 소스 프로세서: KTable, KStream 생성
		KTable<String, String> addressTable = builder.table(ADDRESS_TABLE);
		KStream<String, String> orderStream = builder.stream(ORDER_STREAM);

		// 1. orderStream에서 조인을 위해 join() 메서드 사용.
		// 2. 첫 번째 파라미터로 조인할 KTable 주입
		// 3. 두 번째 파라미터는 어떤 데이터를 만들지 정의
		// 	- KStream과 KTable에서 동일한 메시지 키를 가진 데이터를 찾았을 경우
		// 4. 조인을 통해 생성된 데이터를 order_join 토픽에 저장하기 위해 to() 싱크 프로세서 사용
		orderStream.join(addressTable, (order, address) -> order + " send to " + address).to(ORDER_JOIN_STREAM);

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

	}
}
