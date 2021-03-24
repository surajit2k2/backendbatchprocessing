package com.ibm.batch.excel.region;

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
import com.ibm.batch.common.LoggingRegionProcessor;
import com.ibm.batch.dto.RegionDTO;

@Configuration
public class RegionAPIJobConfig {

	private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH = "excel.region.api.job.source.file.path";
	private static final String PROPERTY_REST_API_URL = "rest.api.url";

	@Bean
	ItemReader<RegionDTO> excelRegionReader(Environment environment)
			throws MalformedURLException, IllegalStateException {
		PoiItemReader<RegionDTO> reader = new PoiItemReader<RegionDTO>();
		reader.setLinesToSkip(1);
		// reader.setResource(new
		// ClassPathResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setResource(new FileSystemResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	private RowMapper<RegionDTO> excelRowMapper() {
		BeanWrapperRowMapper<RegionDTO> rowMapper = new BeanWrapperRowMapper<RegionDTO>();
		rowMapper.setTargetType(RegionDTO.class);
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
	ItemProcessor<RegionDTO, RegionDTO> excelRegionProcessor() {
		return new LoggingRegionProcessor();
	}

	/*
	 * @Bean ItemWriter<StudentDTO> excelStudentWriter() { return new
	 * LoggingStudentWriter(); }
	 */

	@Bean
	public ItemWriter<RegionDTO> excelRegionWriter(Environment environment, RestTemplate restTemplate) {
		return new RegionAPIWriter(environment.getRequiredProperty(PROPERTY_REST_API_URL), restTemplate);
	}

	@Bean
	Step excelRegionStep1(ItemReader<RegionDTO> excelRegionReader,
			ItemProcessor<RegionDTO, RegionDTO> excelRegionProcessor,
			ItemWriter<RegionDTO> excelRegionWriter, StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("excelRegionStep1").<RegionDTO, RegionDTO>chunk(1)
				.reader(excelRegionReader).processor(excelRegionProcessor)
				.writer(excelRegionWriter).build();
	}

	@Bean
	public Step excelRegionStep2(ItemReader<RegionDTO> excelRegionReader,
			ItemProcessor<RegionDTO, RegionDTO> excelRegionProcessor, ItemWriter<RegionDTO> excelRegionWriter,
			StepBuilderFactory stepBuilderFactory) {
		FileMovingTasklet task = new FileMovingTasklet();
		Resource[] inputResources = new Resource[1];
		inputResources[0] = new FileSystemResource("/data/temp/region.xlsx");
		task.setResources(inputResources);
		task.setMoveFilePath("/data/tmp/");
		return stepBuilderFactory.get("excelRegionStep2").tasklet(task).build();
	}

	@Bean
	Job excelFileToRegionAPIJob(JobBuilderFactory jobBuilderFactory, @Qualifier("excelRegionStep1") Step excelRegionStep1,
			@Qualifier("excelRegionStep2") Step excelRegionStep2) {
		return jobBuilderFactory.get("excelFileToRegionAPIJob").incrementer(new RunIdIncrementer())
				.start(excelRegionStep1)
				.next(excelRegionStep2)
				.build();
	}
}
