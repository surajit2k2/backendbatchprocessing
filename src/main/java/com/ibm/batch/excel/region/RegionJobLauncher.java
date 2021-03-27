package com.ibm.batch.excel.region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
@Component
public class RegionJobLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegionJobLauncher.class);

	private final Job job;

	private final JobLauncher jobLauncher;

	@Autowired
	RegionJobLauncher(@Qualifier("excelFileToRegionAPIJob") Job job, JobLauncher jobLauncher) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}

//	@Scheduled(cron = "${excel.region.api.job.cron}")
	public void launchExcelFileToRegionAPIJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {
		LOGGER.info("Starting excelFileToAPI job");

		jobLauncher.run(job, newExecution());

		LOGGER.info("Stopping launchExcelFileToRegionAPIJob job");
	}

	private JobParameters newExecution() {
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();

		JobParameter parameter = new JobParameter(new Date());
		parameters.put("currentTime", parameter);

		return new JobParameters(parameters);
	}
}
