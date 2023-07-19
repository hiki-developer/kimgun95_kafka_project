package org.example;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaAdminClient {
	private final static Logger logger = LoggerFactory.getLogger(KafkaAdminClient.class);
	private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";

	public static void main(String[] args) throws Exception {

		Properties configs = new Properties();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "my-kafka:9092");
		AdminClient admin = AdminClient.create(configs);

		// 브로커 정보 조회
		logger.info("== Get broker information");
		for (Node node : admin.describeCluster().nodes().get()) {
			logger.info("node : {}", node);
			ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
			DescribeConfigsResult describeConfigs = admin.describeConfigs(Collections.singleton(cr));
			describeConfigs.all().get().forEach((broker, config) -> {
				config.entries().forEach(configEntry -> logger.info(configEntry.name() + "= " + configEntry.value()));
			});
		}

		logger.info("== Get default num.partitions");
		for (Node node : admin.describeCluster().nodes().get()) {
			ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
			DescribeConfigsResult describeConfigs = admin.describeConfigs(Collections.singleton(cr));
			Config config = describeConfigs.all().get().get(cr);
			Optional<ConfigEntry> optionalConfigEntry = config.entries().stream().filter(v -> v.name().equals("num.partitions")).findFirst();
			ConfigEntry numPartitionConfig = optionalConfigEntry.orElseThrow(Exception::new);
			logger.info("{}", numPartitionConfig.value());
		}

		// 토픽 리스트 조회
		logger.info("== Topic list");
		for (TopicListing topicListing : admin.listTopics().listings().get()) {
			logger.info("{}", topicListing.toString());
		}

		// 토픽 정보 조회
		logger.info("== test topic information");
		Map<String, TopicDescription> topicInformation = admin.describeTopics(Collections.singletonList("test")).all().get();
		logger.info("{}", topicInformation);

		// 컨슈머 그룹 조회
		logger.info("== Consumer group list");
		ListConsumerGroupsResult listConsumerGroups = admin.listConsumerGroups();
		listConsumerGroups.all().get().forEach(v -> {
			logger.info("{}", v);
		});

		// 어드민 API 리소스 해제
		admin.close();
	}
}
