package com.ohadr.activemq_spring;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JmsExceptionListener implements ExceptionListener
{
	private static Logger log = Logger.getLogger(JmsExceptionListener.class);
	
	public void onException( final JMSException e )
	{
		log.error("beeeeeeeeeeee", e);
		e.printStackTrace();
	}
}