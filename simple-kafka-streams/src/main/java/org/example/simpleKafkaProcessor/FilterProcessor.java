package org.example.simpleKafkaProcessor;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

// 스트림 프로세서 클래스를 생성하기 위해 kafka-streams 라이브러리에서 제공하는 Processor 인터페이스를 사용해야 한다.
public class FilterProcessor implements Processor<String, String> {

	// 프로세서에 대한 정보를 담고 있다. 현재 스트림 처리 중인 토폴로지의 토픽 정보, 애플리케이션 아이디를 조회할 수 있다.
	// 혹은 schedule(), forward(), commit() 등의 프로세싱 처리에 필요한 메서드를 사용할 수도 있다.
	private ProcessorContext context;

	// init() 메서드는 스트림 프로세서의 생성자
	@Override
	public void init(ProcessorContext context) {
		this.context = context;
	}

	// 실질적으로 프로세싱 로직이 들어가는 부분
	// 1개의 레코드를 받는 것을 가정하여 데이터를 처리한다.
	@Override
	public void process(String key, String value) {
		// 필터링된 데이터의 경우 forward() 메서드를 통해 다음 토폴로지(프로세서)로 넘어가도록 한다.
		if (value.length() > 5) {
			context.forward(key, value);
		}
		// 처리가 완료된 이후에는 commit() 을 통해 명시적으로 데이터가 처리되었음을 선언한다.
		context.commit();
	}

	// 프로세서가 종료되기 전에 호출되는 메서드
	// 프로세싱을 하기 위해 사용했던 리소스를 해제하는 구문을 넣는다.
	@Override
	public void close() {
	}

}
