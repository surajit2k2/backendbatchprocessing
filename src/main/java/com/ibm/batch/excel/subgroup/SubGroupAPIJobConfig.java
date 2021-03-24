package com.ibm.batch.excel.subgroup;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import com.ibm.batch.common.FileMovingTasklet;
import com.ibm.batch.common.LoggingSubGroupProcessor;
import com.ibm.batch.dto.SubGroupDTO;

@Configuration
public class SubGroupAPIJobConfig {

	private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH = "excel.subgroup.api.job.source.file.path";
	private static final String PROPERTY_REST_API_URL = "rest.api.url";

	@Bean
	ItemReader<SubGroupDTO> excelReader(Environment environment)
			throws MalformedURLException, IllegalStateException {
		PoiItemReader<SubGroupDTO> reader = new PoiItemReader<SubGroupDTO>();
		reader.setLinesToSkip(1);
		
		// reader.setResource(new
		// ClassPathResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setResource(new FileSystemResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	private RowMapper<SubGroupDTO> excelRowMapper() {
		BeanWrapperRowMapper<SubGroupDTO> rowMapper = new BeanWrapperRowMapper<SubGroupDTO>();
		rowMapper.setTargetType(SubGroupDTO.class);
		return rowMapper;
	}

	/**
	 * If your Excel document has no header, you have to create a custom row
	 * mapper and configure it here.
	 */
	/*
	 * private RowMapper<StudentDTO> excelRowMapper() { return new
	 * StudentExcelRowMapper(); }
	 */

	@Bean
	ItemProcessor<SubGroupDTO, SubGroupDTO> excelProcessor() {
		return new LoggingSubGroupProcessor();
	}

	/*
	 * @Bean ItemWriter<StudentDTO> excelStudentWriter() { return new
	 * LoggingStudentWriter(); }
	 */

	@Bean
	public ItemWriter<SubGroupDTO> excelWriter(Environment environment, RestTemplate restTemplate) {
		return new SubGroupAPIWriter(environment.getRequiredProperty(PROPERTY_REST_API_URL), restTemplate);
	}

	@Bean
	Step excelSubGroupStep1(ItemReader<SubGroupDTO> excelReader,
			ItemProcessor<SubGroupDTO, SubGroupDTO> excelProcessor,
			ItemWriter<SubGroupDTO> excelWriter, StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("excelSubGroupStep1").<SubGroupDTO, SubGroupDTO>chunk(1)
				.reader(excelReader).processor(excelProcessor)
				.writer(excelWriter).build();
	}

	@Bean
	public Step excelSubGroupStep2(ItemReader<SubGroupDTO> excelReader,
			ItemProcessor<SubGroupDTO, SubGroupDTO> excelProcessor, ItemWriter<SubGroupDTO> excelWriter,
			StepBuilderFactory stepBuilderFactory) {
		FileMovingTasklet task = new FileMovingTasklet();
		Resource[] inputResources = new Resource[1];
		inputResources[0] = new FileSystemResource("/data/temp/subgroup.xlsx");
		task.setResources(inputResources);
		task.setMoveFilePath("/data/tmp/");
		return stepBuilderFactory.get("excelSubGroupStep2").tasklet(task).build();
	}

	@Bean
	Job excelFileToSubGroupAPIJob(JobBuilderFactory jobBuilderFactory, @Qualifier("excelSubGroupStep1") Step excelSubGroupStep1,
			@Qualifier("excelSubGroupStep2") Step excelSubGroupStep2) {
		return jobBuilderFactory.get("excelFileToSubGroupAPIJob").incrementer(new RunIdIncrementer())
				.start(excelSubGroupStep1)
				.next(excelSubGroupStep2)
				.build();
	}
}
