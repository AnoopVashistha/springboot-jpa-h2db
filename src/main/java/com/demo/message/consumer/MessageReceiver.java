package com.demo.message.consumer;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;

@Component
public class MessageReceiver {
	static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

	private static final String ORDER_RESPONSE_QUEUE = "employee-queue";

	@Autowired
	EmployeeService employeeService;

	@JmsListener(destination = ORDER_RESPONSE_QUEUE)
	public void receiveMessage(final Message<Employee> message) throws JMSException {
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		MessageHeaders headers = message.getHeaders();
		LOG.info("Application : headers received : {}", headers);

		Employee response = message.getPayload();
		LOG.info("Application : response received : {}", response);

		employeeService.updateEmployee(response);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
}
