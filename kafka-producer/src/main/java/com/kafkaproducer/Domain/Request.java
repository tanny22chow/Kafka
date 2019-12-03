package com.kafkaproducer.Domain;

public class Request {

	private String type;
	private Integer SLA;
	public Request() {
		super();
	}
	public Request(String type, Integer sLA) {
		super();
		this.type = type;
		SLA = sLA;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSLA() {
		return SLA;
	}
	public void setSLA(Integer sLA) {
		SLA = sLA;
	}
	
}
