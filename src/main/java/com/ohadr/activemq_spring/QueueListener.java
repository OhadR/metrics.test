package com.ohadr.activemq_spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements MessageListener
{
	private static Logger log = Logger.getLogger(QueueListener.class);
	private static long sleepSecs = 15;

	public void onMessage( final Message message )
	{
		if ( message instanceof TextMessage )
		{
			final TextMessage textMessage = (TextMessage) message;
			try
			{
				log.info("read message from Queue: " + textMessage.getText());
				log.info("processing message...");
				try 
				{
					Thread.sleep(sleepSecs * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("Done.");
			}
			catch (final JMSException e)
			{
				e.printStackTrace();
			}
		}
	}
}
