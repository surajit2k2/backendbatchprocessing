package com.ibm.redis.listerner;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.batch.excel.region.RegionJobLauncher;
import com.ibm.batch.excel.subgroup.SubGroupJobLauncher;



public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger counter = new AtomicInteger();
    @Autowired
    RegionJobLauncher regionJobLauncher;

    @Autowired
    SubGroupJobLauncher subGroupJobLauncher;
    
    
    public void receiveMessage(String message) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("Received in Batch<" + message + ">");
        if(message.toLowerCase().contains("region")) {
        	regionJobLauncher.launchExcelFileToRegionAPIJob();
        }else if(message.toLowerCase().contains("subgroup")){
        	subGroupJobLauncher.launchExcelFileToSubGroupAPIJob();
        }
        counter.incrementAndGet();
    }



	public int getCount() {
		// TODO Auto-generated method stub
		return counter.get();
	}
}
