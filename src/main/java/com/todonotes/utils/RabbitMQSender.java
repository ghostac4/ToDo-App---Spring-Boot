package com.todonotes.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

	private RabbitTemplate rabbitTemplate;

	public RabbitMQSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendEmailQueue(String email) {
		rabbitTemplate.convertAndSend("email-exchange","email-key",email );
	}
}
