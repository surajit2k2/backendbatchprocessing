package com.ibm.batch.excel.region;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ibm.batch.common.FileMovingTasklet;
import com.ibm.batch.common.LoggingStudentProcessor;
import com.ibm.batch.dto.StudentDTO;

@Configuration
public class ExcelFileToDatabaseJobConfig {

	private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH = "excel.to.database.job.source.file.path";
	private static final String QUERY_INSERT_STUDENT = "INSERT "
			+ "INTO students(email_address, name, purchased_package) "
			+ "VALUES (:emailAddress, :name, :purchasedPackage)";

	@Bean
	ItemReader<StudentDTO> excelStudentReader(Environment environment)
			throws MalformedURLException, IllegalStateException {
		PoiItemReader<StudentDTO> reader = new PoiItemReader<StudentDTO>();
		reader.setLinesToSkip(1);
		// reader.setResource(new
		// ClassPathResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setResource(new FileSystemResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	private RowMapper<StudentDTO> excelRowMapper() {
		BeanWrapperRowMapper<StudentDTO> rowMapper = new BeanWrapperRowMapper<StudentDTO>();
		rowMapper.setTargetType(StudentDTO.class);
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
	ItemProcessor<StudentDTO, StudentDTO> excelStudentProcessor() {
		return new LoggingStudentProcessor();
	}

	/*
	 * @Bean ItemWriter<StudentDTO> excelStudentWriter() { return new
	 * LoggingStudentWriter(); }
	 */

	@Bean
	ItemWriter<StudentDTO> excelStudentWriter(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
		JdbcBatchItemWriter<StudentDTO> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(jdbcTemplate);

		databaseItemWriter.setSql(QUERY_INSERT_STUDENT);

		ItemSqlParameterSourceProvider<StudentDTO> sqlParameterSourceProvider = studentSqlParameterSourceProvider();
		databaseItemWriter.setItemSqlParameterSourceProvider(sqlParameterSourceProvider);

		return databaseItemWriter;
	}

	private ItemSqlParameterSourceProvider<StudentDTO> studentSqlParameterSourceProvider() {
		return new BeanPropertyItemSqlParameterSourceProvider<>();
	}

	@Bean
	Step excelFileToDatabaseStep(ItemReader<StudentDTO> excelStudentReader,
			ItemProcessor<StudentDTO, StudentDTO> excelStudentProcessor, ItemWriter<StudentDTO> excelStudentWriter,
			StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("excelFileToDatabaseStep").<StudentDTO, StudentDTO>chunk(1)
				.reader(excelStudentReader).processor(excelStudentProcessor).writer(excelStudentWriter).build();
	}

	@Bean
	public Step step2(ItemReader<StudentDTO> excelStudentReader,
			ItemProcessor<StudentDTO, StudentDTO> excelStudentProcessor, ItemWriter<StudentDTO> excelStudentWriter,
			StepBuilderFactory stepBuilderFactory) {
		FileMovingTasklet task = new FileMovingTasklet();
		Resource[] inputResources = new Resource[1];
		inputResources[0] = new FileSystemResource("c:/temp/students.xlsx");
		task.setResources(inputResources);
		return stepBuilderFactory.get("step2").tasklet(task).build();
	}

	@Bean
	Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
			@Qualifier("excelFileToDatabaseStep") Step excelStudentStep, @Qualifier("step2") Step step2) {
		return jobBuilderFactory.get("excelFileToDatabaseJob").incrementer(new RunIdIncrementer())
				// .flow(excelStudentStep)
				.start(excelStudentStep).next(step2)
				// .end()
				.build();
	}
}
