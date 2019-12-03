package com.kafkaproducer.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.kafkaproducer.Domain.Request;

@Configuration
@EnableKafka
public class ProcuderConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapservers;

	@Bean
	public ProducerFactory producerfactory() {
		Map<String,Object>mapconfiguration= new HashMap<String,Object>();
		mapconfiguration.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapservers);
		mapconfiguration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		mapconfiguration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		mapconfiguration.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,10);
		ProducerFactory factory=new DefaultKafkaProducerFactory<String,Object>(mapconfiguration);
		return factory;
	}
	@Bean
	public KafkaTemplate<String,Request> kafkaTemplate() {
		KafkaTemplate<String,Request> template=new KafkaTemplate<String,Request>(producerfactory());
		return template;
	}
	@Bean
	public ConsumerFactory consumerfactory() {
		Map<String,Object>mapconfiguration= new HashMap<String,Object>();
		mapconfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapservers);
		mapconfiguration.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		mapconfiguration.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		mapconfiguration.put(ConsumerConfig.GROUP_ID_CONFIG,"consumer-apps");
		mapconfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
		ConsumerFactory factory=new DefaultKafkaConsumerFactory<String,Object>(
				mapconfiguration,new StringDeserializer(),
				new JsonDeserializer());
		return factory;
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,Object> consumercontainer() {
		ConcurrentKafkaListenerContainerFactory<String,Object> containerfactory= 
				new ConcurrentKafkaListenerContainerFactory<String,Object>();
		containerfactory.setConsumerFactory(consumerfactory());
		return containerfactory;
	}
}
