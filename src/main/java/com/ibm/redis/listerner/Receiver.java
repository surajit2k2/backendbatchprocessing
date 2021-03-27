package com.ibm.redis.listerner;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {
        LOGGER.info("Received in Batch<" + message + ">");
        counter.incrementAndGet();
    }

    /*public int getCount() {
        return counter.get();
    }*/

	public int getCount() {
		// TODO Auto-generated method stub
		return counter.get();
	}
}
