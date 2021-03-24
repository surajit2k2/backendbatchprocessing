package com.ibm.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.ibm.batch.dto.StudentDTO;

public class LoggingItemWriter implements ItemWriter<StudentDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingItemWriter.class);

    @Override
    public void write(List<? extends StudentDTO> list) throws Exception {
        LOGGER.info("Writing students: {}", list);
    }
}
