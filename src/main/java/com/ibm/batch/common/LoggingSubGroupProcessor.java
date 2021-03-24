package com.ibm.batch.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.ibm.batch.dto.SubGroupDTO;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author 
 */
public class LoggingSubGroupProcessor implements ItemProcessor<SubGroupDTO, SubGroupDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingSubGroupProcessor.class);

    @Override
    public SubGroupDTO process(SubGroupDTO item) throws Exception {
        LOGGER.info("Processing SubGroup information: {}", item);
        return item;
    }
}
