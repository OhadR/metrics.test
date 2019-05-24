package com.ohadr.metrics;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

@Component
public class SomeService
{
	private static Logger log = Logger.getLogger(SomeService.class);
	
    private Counter eventCounterMetric;

    @Resource
    MetricRegistry metrics;

	public SomeService()
	{
	}

    @PostConstruct
    public void init() {
        eventCounterMetric = new Counter();
    }

	public void send( final String message )
	{
	}
	
	public void send(String message, int amount) {
		for(int i = 0; i < amount; ++i)
		{
			send(message + "#" + i);
			eventCounterMetric.inc();
		}
		
	}

}
