package com.kafkaproducer.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafkaproducer.Domain.Request;
import com.kafkaproducer.Service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	private RequestService reqservice;
	
	@PostMapping(path="/postrequest",consumes="application/json")
	public ResponseEntity<String> publishRequest(@Valid @RequestBody Request req){
		try {
			reqservice.publish(req);
			return new ResponseEntity<String>("Published",HttpStatus.CREATED);
		}catch(KafkaProducerException e) {
			return new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
