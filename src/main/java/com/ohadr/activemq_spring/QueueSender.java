package com.ohadr.activemq_spring;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSender
{
	private static Logger log = Logger.getLogger(QueueSender.class);
	

	@Autowired
	public QueueSender(  )
	{
	}
	public void send( final String message )
	{
	}
	
	public void send(String message, int amount) {
		for(int i = 0; i < amount; ++i)
			send(message + "#" + i);
	}

}
