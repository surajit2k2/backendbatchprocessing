package com.ibm.batch.excel.region;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import com.ibm.batch.dto.RegionDTO;

/**
 * This class demonstrates how we can implement a row mapper that maps a row
 * found from an Excel document into a {@code StudentDTO} object. If the Excel
 * document has no header, we have to use this method for transforming the input
 * data into {@code StudentDTO} objects.
 *
 * 
 */
public class RegionExcelRowMapper implements RowMapper<RegionDTO> {

	@Override
	public RegionDTO mapRow(RowSet rowSet) throws Exception {
		RegionDTO region = new RegionDTO();

		region.setRegionName(rowSet.getColumnValue(0));

		return region;
	}
}
