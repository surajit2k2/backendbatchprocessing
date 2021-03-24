package com.ibm.batch.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.ibm.batch.dto.RegionDTO;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author 
 */
public class LoggingRegionProcessor implements ItemProcessor<RegionDTO, RegionDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRegionProcessor.class);

    @Override
    public RegionDTO process(RegionDTO item) throws Exception {
        LOGGER.info("Processing Region information: {}", item);
        return item;
    }
}
