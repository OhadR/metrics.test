package com.ohadr.activemq_spring.web;

import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohadr.activemq_spring.QueueSender;


@Controller
public class WebApi 
{
	@Autowired
	private QueueSender sender;

	private static Logger log = Logger.getLogger(WebApi.class);


	@ResponseBody
	@RequestMapping(value = "/send",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public String getExtractionsMetrics(
			@RequestParam("message") String message,
			@RequestParam("amount") int amount
			) throws IOException 
	{
		log.info("::send request, message is: " + message
				+ ", amount: " + amount);
		
		sender.send(message, amount);
		
		return message;
	}

	@ResponseBody
	@RequestMapping(value = "/peek",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public void writeExtractionsMetricsToFile(
			HttpServletResponse response) throws IOException 
	{
		log.info("::receive request. *** CURRENTLY DOES NOTHING ***");
	}

	@ResponseBody
	@RequestMapping(value = "/delete",
					method = RequestMethod.DELETE)
	public void deleteQueue(
			HttpServletResponse response) throws IOException, JMSException 
	{
		log.info("::delete request.");
		sender.delete();
	}

	@ResponseBody
	@RequestMapping(value = "/deleteAll",
					method = RequestMethod.DELETE)
	public void deleteAllQueues(
			HttpServletResponse response) throws IOException, JMSException 
	{
		log.info("::deleteAll request.");
		sender.deleteAll();
	}
}
