package com.icg.QuerySwitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")

public class QueryController {

	// private final RabbitTemplate rabbitTemplate;
	// private final Receiver receiver;
	@Autowired
	Producer producer;
	@Autowired
	SecurityTools securityTools;

	@Value("${ICG.logs.loggingapp}")
	private String loginapp;

	@Value("${ICG.QS.SYNCMESSAGES}")
	private String syncmessages;

	@Value("${ICG.logs.generalLOGExchange}")
	private String exchangelogs;

	@Value("${ICG.logs.generalLOGRouting}")
	private String routinglogs;

	@Value("${ICG.logs.description}")
	private String description;

	@Value("${ICG.logs.location}")
	private String location;

	@Value("${ICG.logs.system}")
	private String system;

	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;

	@Value("${jsa.rabbitmq.headername}")
	private String headername;

	@Value("${jsa.rabbitmq.headervalue}")
	private String headervalue;

	Logger logger = LoggerFactory.getLogger(QueryController.class);

	@CrossOrigin(origins = "*")
	// @RequestMapping(value = "/tenant/{tenantid}/valida/queries/v2/", method =
	// RequestMethod.POST)
	@RequestMapping(value = "/valida/queries/v3/", method = RequestMethod.POST)
	public Object SendQuery(@RequestBody String body, @RequestHeader(name = "Authorization") String token) {
		// body=body.toUpperCase();
		// se construye el json para general_log

		String issuer = securityTools.getIssuerFromToken(token);
		issuer = issuer.substring(issuer.lastIndexOf("/") + 1, issuer.length());
		issuer = issuer.replace("QS_", "");
		logger.info("token issuer is " + issuer);
		long elapsed = 0;
		long start = 0;
		try {
			// long elapsed = 0;
			elapsed = System.currentTimeMillis();
			logger.debug("start processing");
			logger.info("received message " + body);
			JSONObject json = new JSONObject();
			json.put("mensaje", body);
			json.put("Configuracion", loginapp);
			json.put("Descripcion", description);
			json.put("Sistema", system);
			json.put("Location", location);
			logger.debug("log built");
			producer.produceMsgNotRCP(exchangelogs, routinglogs, json.toString());
			logger.info("Message log produced");
			logger.debug("body " + body);
			logger.debug("exchange " + exchange);
			logger.debug("headerName " + headername);
			logger.debug("headerValue " + headervalue);
			logger.info("Sending message");
			JSONObject originalmessage = new JSONObject(body);
			logger.info(body);
			// version dos, se manda el tipo al header de routing
			// rtrujillo 120421

			String mt = originalmessage.getString("type");
			logger.info("got type " + mt);
			Object response = null;

			// mensaje sincrono (RCP call)
			String[] syncm = syncmessages.split(",");
			boolean iSync = false;
			for (String s : syncm) {
				if (s.compareTo(mt) == 0) {
					iSync = true;
				}
			}

			JSONObject jsonbody = new JSONObject(body);
			jsonbody.put("version", "3.0");
			logger.info(jsonbody.toString());
			logger.info("found source " + jsonbody.getString("Source"));
			logger.info("found issuer " + issuer);
			if (jsonbody.getString("Source").compareTo(issuer) != 0) {
				logger.info("request is forbidden, issuer no match " + issuer + " - " + jsonbody.getString("Source"));
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			if (iSync) {
				logger.debug("got sync message");
				response = producer.produceMsg(jsonbody.toString(), exchange, headername, headervalue);
			} else {
				logger.debug("got Async message");

				producer.produceMsgNANSWER(jsonbody.toString(), exchange, headername, headervalue);
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("code", "1");
				jsonResponse.put("message", "received");
				response = jsonResponse.toString();
			}
			// mensaje asincrono
			elapsed = start = System.currentTimeMillis();

			json = new JSONObject();
			json.put("mensaje", response);
			json.put("elapsed", elapsed);

			json.put("Configuracion", loginapp + "_02");
			json.put("Descripcion", description);
			json.put("Sistema", system);
			json.put("Location", location);
			logger.debug("log response built");
			producer.produceMsgNotRCP(exchangelogs, routinglogs, json.toString());
			logger.info("response got " + response.toString());
			logger.info("returning response " + response.toString());

			return response;
		} catch (Exception ex) {

			// Object response=producer.produceMsg(body,exchange,headername,headervalue);
			JSONObject json = new JSONObject();
			json = new JSONObject();
			elapsed = start = System.currentTimeMillis();

			json.put("elapsed", elapsed);
			json.put("mensaje", "TIMEDOUT " + body);
			json.put("Configuracion", loginapp + "_03");
			json.put("Descripcion", description);
			json.put("Sistema", system);
			json.put("Location", location);
			logger.debug("log response built");
			producer.produceMsgNotRCP(exchangelogs, routinglogs, json.toString());
			logger.error("Error:" + ex.getMessage());
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
		}
	}
	/*
	 * @CrossOrigin(origins = "*")
	 * 
	 * @RequestMapping(value = "/valida/queries/", method = RequestMethod.POST)
	 * public Object SendQueryv(@RequestBody String body) {
	 * // body=body.toUpperCase();
	 * //se construye el json para general_log
	 * try {
	 * logger.debug("start processing");
	 * logger.info("received message "+ body);
	 * JSONObject json = new JSONObject();
	 * json.put("mensaje", body );
	 * json.put("Configuracion",loginapp);
	 * json.put("Descripcion",description);
	 * json.put("Sistema",system);
	 * json.put("Location",location);
	 * logger.debug("log built");
	 * producer.produceMsgNotRCP(exchangelogs,routinglogs,json.toString());
	 * logger.info("Message log produced");
	 * logger.debug("body "+body);
	 * logger.debug("exchange "+exchange);
	 * logger.debug("headerName "+headername);
	 * logger.debug("headerValue "+ headervalue);
	 * logger.info("Sending message");
	 * JSONObject originalmessage=new JSONObject(body);
	 * logger.info(body);
	 * //version dos, se manda el tipo al header de routing
	 * //rtrujillo 120421
	 * 
	 * String mt=originalmessage.getString("type");
	 * logger.info("got type "+mt);
	 * Object response=null;
	 * 
	 * 
	 * //mensaje sincrono (RCP call)
	 * logger.info("SYNCMESSAGES:"+syncmessages);
	 * String[] syncm=syncmessages.split(",");
	 * boolean iSync=false;
	 * logger.info("searching mt "+mt);
	 * 
	 * logger.info("before searching isync is"+iSync);
	 * 
	 * for(String s:syncm){
	 * logger.info("searching "+s+"-"+mt);
	 * 
	 * if(s.compareTo(mt)==0) {
	 * iSync=true;
	 * }
	 * }
	 * 
	 * logger.info("after searching isync is"+iSync);
	 * logger.info("checking async");
	 * 
	 * 
	 * 
	 * if(iSync) {
	 * logger.info("waiting for response");
	 * logger.debug("got sync message");
	 * response=producer.produceMsg(body,exchange,headername,headervalue);
	 * }
	 * else {
	 * logger.info("not waiting for response");
	 * logger.debug("got Async message");
	 * 
	 * producer.produceMsgNANSWER(body,exchange,headername,headervalue);
	 * JSONObject jsonResponse=new JSONObject();
	 * jsonResponse.put("code", "1");
	 * jsonResponse.put("message", "received");
	 * response=jsonResponse.toString();
	 * }
	 * //mensaje asincrono
	 * 
	 * json = new JSONObject();
	 * json.put("mensaje", response );
	 * json.put("Configuracion",loginapp+"_02");
	 * json.put("Descripcion",description);
	 * json.put("Sistema",system);
	 * json.put("Location",location);
	 * logger.debug("log response built");
	 * producer.produceMsgNotRCP(exchangelogs,routinglogs,json.toString());
	 * 
	 * logger.info("response got "+ response.toString());
	 * logger.info("returning response "+response.toString());
	 * return response;
	 * }catch(Exception ex) {
	 * Object response=producer.produceMsg(body,exchange,headername,headervalue);
	 * JSONObject json = new JSONObject();
	 * json = new JSONObject();
	 * json.put("mensaje", "TIMEDOUT "+ex.getMessage() );
	 * json.put("Configuracion",loginapp+"_03");
	 * json.put("Descripcion",description);
	 * json.put("Sistema",system);
	 * json.put("Location",location);
	 * logger.debug("log response built");
	 * producer.produceMsgNotRCP(exchangelogs,routinglogs,json.toString());
	 * logger.error("Error:"+ex.getMessage());
	 * return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
	 * }
	 * }
	 */

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getQuery() {
		logger.debug("live probeness");
		return "Alive";
	}
}