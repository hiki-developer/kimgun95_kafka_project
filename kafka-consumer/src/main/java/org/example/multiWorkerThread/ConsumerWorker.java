package org.example.multiWorkerThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Runnable 인터페이스로 구현한 ConsumerWorker 클래스는 스레드로 실행됨
public class ConsumerWorker implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(ConsumerWorker.class);
	private String recordValue;

	ConsumerWorker(String recordValue) {
		this.recordValue = recordValue;
	}

	@Override
	public void run() {
		logger.info("thread:{}\trecord:{}", Thread.currentThread().getName(), recordValue);
	}
}
