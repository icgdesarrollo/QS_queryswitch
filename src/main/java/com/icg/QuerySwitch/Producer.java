package com.icg.QuerySwitch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	Logger logger = LoggerFactory.getLogger(Producer.class);
	@Value("${ICG.QS.timeout}")
	private String timeout;

	@Autowired
	private RabbitTemplate template;

	public Object produceMsg(String msg, String exchange, String headername, String headervalue) {
		this.template.setExchange(exchange);
		String msgtemp = msg.toUpperCase();
		JSONObject json = new JSONObject(msgtemp);
		MessageProperties props = new MessageProperties();
		// version dos, se manda el tipo al header de routing
		// rtrujillo 120421
		props.setHeader("DESTINATION", json.get("DESTINATION").toString());
		props.setHeader("SOURCE", json.get("SOURCE").toString());
		props.setHeader("TYPE", json.get("TYPE").toString());
		this.template.setReplyTimeout(Long.parseLong(timeout));
		props.setHeader(headername, headervalue);
		props.setContentType("text");

		logger.info("SETTING HEADER DESTINATION" + json.get("DESTINATION").toString());
		logger.info("SETTING HEADER TYPE" + json.get("TYPE").toString());
		return template.convertSendAndReceive(new Message(msg.getBytes(), props));

	}

	public void produceMsgNANSWER(String msg, String exchange, String headername, String headervalue) {
		this.template.setExchange(exchange);
		String msgtemp = msg.toUpperCase();
		JSONObject json = new JSONObject(msgtemp);
		MessageProperties props = new MessageProperties();
		// version dos, se manda el tipo al header de routing
		// rtrujillo 120421
		// String hv=headervalue+
		// json.get("TYPE").toString()+"_"+json.get("DESTINATION").toString().toUpperCase();
		props.setHeader("DESTINATION", json.get("DESTINATION").toString());
		props.setHeader("SOURCE", json.get("SOURCE").toString());
		props.setHeader("TYPE", json.get("TYPE").toString());
		// logger.info(hv);
		props.setHeader(headername, headervalue);
		props.setContentType("text");
		template.convertAndSend(new Message(msg.getBytes(), props));
	}

	public void produceMsgNotRCP(String exchange, String routing, String msg) {
		// this.template.setExchange(exchange);

		// JSONObject json = new JSONObject(msg);

		// rtrujillo 271123 cambiar los valores para que no se use routing si no
		// exchange headers por problemas de threading
		// this.template.setRoutingKey(routing);
		// this.template.setExchange(exchange);

		template.convertAndSend(exchange, routing, msg.getBytes());

		// template.convertAndSend(msg.getBytes());
	}
}