package org.example.simpleSourceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 소스 커넥터를 상속 받아 커넥트에서 사용하게 된다.
// 플러그인으로 추가하여 사용 시에는 패키지 이름과 함께 붙여서 사용된다.
public class SingleFileSourceConnector extends SourceConnector {

	private final Logger logger = LoggerFactory.getLogger(SingleFileSourceConnector.class);

	private Map<String, String> configProperties;

	@Override
	public String version() {
		return "1.0";
	}

	@Override
	public void start(Map<String, String> props) {
		this.configProperties = props;
		try {
			// 커넥트를 생설할 때 받은 설정값들을 초기화
			new SingleFileSourceConnectorConfig(props);
		} catch (ConfigException e) { // 설정 초기화 시 필수 설정값이 빠져있다면 ConnectException
			throw new ConnectException(e.getMessage(), e);
		}
	}

	// 사용할 태스크의 클래스 이름 지정
	@Override
	public Class<? extends Task> taskClass() {
		return SingleFileSourceTask.class;
	}

	// 태스크가 2개 이상인 경우 태스크 마다 다른 설정값을 줄 때 사용한다.
	// 여기서는 그냥 동일한 설정값을 모두 담았다.
	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		List<Map<String, String>> taskConfigs = new ArrayList<>();
		Map<String, String> taskProps = new HashMap<>();
		taskProps.putAll(configProperties);
		for (int i = 0; i < maxTasks; i++) {
			taskConfigs.add(taskProps);
		}
		return taskConfigs;
	}

	// 커넥터에서 사용할 설정값을 지정
	@Override
	public ConfigDef config() {
		return SingleFileSourceConnectorConfig.CONFIG;
	}

	// 커넥터 종료 시 해제해야 할 리소스가 없으므로 빈칸으로 둔다.
	@Override
	public void stop() {
	}
}
