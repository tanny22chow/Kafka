package com.kafkaproducer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.kafkaproducer.Domain.Request;

@Service
public class RequestService {
	@Autowired
	private KafkaTemplate tmplate;

public void publish(Request req) {
	tmplate.send("producer-consumer", req).addCallback(new ListenableFutureCallback<String>() {
		@Override
		public void onFailure(Throwable ex) {
		}
		@Override
		public void onSuccess(String result) {
			System.out.println("messege published");
		}
	});
	tmplate.flush();
}
@KafkaListener(containerFactory="consumercontainer",topics="producer-consumer")
public void consume(@Payload Request req,@Header MessageHeaders header) {
	System.out.println(req.getType()+"-----------"+req.getSLA());

}
}
