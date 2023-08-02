package org.example.simpleSinkConnector;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class SingleFileSinkTask extends SinkTask {
	private SingleFileSinkConnectorConfig config;
	private File file;
	private FileWriter fileWriter;

	@Override
	public String version() {
		return "1.0";
	}

	@Override
	public void start(Map<String, String> props) {
		try {
			config = new SingleFileSinkConnectorConfig(props);
			file = new File(config.getString(config.DIR_FILE_NAME));
			fileWriter = new FileWriter(file, true);
		} catch (Exception e) {
			throw new ConnectException(e.getMessage(), e);
		}

	}

	// 일정 주기로 토픽의 데이터를 가져오는 put() 메서드는 데이터를 저장하는 코드를 작성한다.
	@Override
	public void put(Collection<SinkRecord> records) {
		try {
			// SinkRecord는 토픽의 레코드이며 초픽, 파티션, 타임스탬프 정보를 포함한다.
			for (SinkRecord record : records) {
				fileWriter.write(record.value().toString() + "\n");
			}
		} catch (IOException e) {
			throw new ConnectException(e.getMessage(), e);
		}
	}

	// put() 메서드에서 파일에 데이터를 저장하는 것은 버퍼에 데이터를 저장하는 것이다.
	// 실질적으로 파일 시스템에 데이터를 저장하려면 fileWriter 클래스의 flush() 메서드를 호출해야 한다.
	@Override
	public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
		try {
			fileWriter.flush();
		} catch (IOException e) {
			throw new ConnectException(e.getMessage(), e);
		}
	}

	@Override
	public void stop() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			throw new ConnectException(e.getMessage(), e);
		}
	}
}
