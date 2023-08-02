package org.example.simpleSourceConnector;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

public class SingleFileSourceConnectorConfig extends AbstractConfig {

	// 어떤 파일을 읽을 것인지
	public static final String DIR_FILE_NAME = "file";
	private static final String DIR_FILE_NAME_DEFAULT_VALUE = "/tmp/kafka.txt";
	private static final String DIR_FILE_NAME_DOC = "읽을 파일 경로와 이름";

	// 읽은 파일을 어느 토픽으로 보낼지
	public static final String TOPIC_NAME = "topic";
	private static final String TOPIC_DEFAULT_VALUE = "test";
	private static final String TOPIC_DOC = "보낼 토픽명";

	// ConfigDef는 커넥터에서 사용할 옵션값들에 대한 정의를 표현
	public static ConfigDef CONFIG = new ConfigDef().define(DIR_FILE_NAME,
			Type.STRING,
			DIR_FILE_NAME_DEFAULT_VALUE,
			Importance.HIGH,
			DIR_FILE_NAME_DOC)
		.define(TOPIC_NAME,
			Type.STRING,
			TOPIC_DEFAULT_VALUE,
			Importance.HIGH,
			TOPIC_DOC);

	public SingleFileSourceConnectorConfig(Map<String, String> props) {
		super(CONFIG, props);
	}
}
