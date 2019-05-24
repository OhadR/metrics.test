package com.ohadr.activemq_spring;

import java.util.Set;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueueSender
{
	private static Logger log = Logger.getLogger(QueueSender.class);
	
	private final JmsTemplate jmsTemplate;
	
	@Value("${ohadr.queue-name}")
	protected String queueName;
	
	@Value("${jms.url}")
	protected String brokerUrl;

	@Autowired
	public QueueSender( final JmsTemplate jmsTemplate )
	{
		this.jmsTemplate = jmsTemplate;
	}
	public void send( final String message )
	{
		jmsTemplate.convertAndSend( queueName, message );
	}
	
	public void send(String message, int amount) {
		for(int i = 0; i < amount; ++i)
			send(message + "#" + i);
	}

	public void delete() throws JMSException
	{
//this is not working, because Spring returns a proxy to the connection, so I cannot call its' 'destoryDestination()':
//		Connection cn = jmsTemplate.getConnectionFactory().createConnection();

		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( brokerUrl );
		Connection connection = factory.createConnection();
		
		ActiveMQDestination destination = ActiveMQDestination.createDestination(queueName, ActiveMQDestination.QUEUE_TYPE);             
		
		ActiveMQConnection amqConn = (ActiveMQConnection)connection;
		
		amqConn.destroyDestination(destination);
	}

	public void deleteAll() throws JMSException 
	{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( brokerUrl );
		Connection connection = factory.createConnection();
		ActiveMQConnection amqConn = (ActiveMQConnection)connection;
		
		connection.start();

		DestinationSource ds = amqConn.getDestinationSource();
		Set<ActiveMQQueue> queues = ds.getQueues();
		
		for(ActiveMQQueue queue : queues)
		{
			String queueName1 = queue.getQueueName();
	        System.out.println(queueName1);
			ActiveMQDestination destination = ActiveMQDestination.createDestination(queueName1, ActiveMQDestination.QUEUE_TYPE);             
			try
			{
				amqConn.destroyDestination(destination);
			}
			catch(JMSException jsmEx)
			{
				//do nothing, keep going
				log.warn("cannot delete destination " + queueName1);
			}
		}

		
		
	}
}
