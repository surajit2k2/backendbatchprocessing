package com.ibm.batch.excel.subgroup;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import com.ibm.batch.dto.RegionDTO;
import com.ibm.batch.dto.SubGroupDTO;

/**
 * This class demonstrates how we can implement a row mapper that maps a row
 * found from an Excel document into a {@code StudentDTO} object. If the Excel
 * document has no header, we have to use this method for transforming the input
 * data into {@code StudentDTO} objects.
 *
 * 
 */
public class SubGroupExcelRowMapper implements RowMapper<SubGroupDTO> {

	@Override
	public SubGroupDTO mapRow(RowSet rowSet) throws Exception {
		System.out.println("************************************************************");
		SubGroupDTO subGroup = new SubGroupDTO();
		subGroup.setSubGroupName(rowSet.getColumnValue(0));
		subGroup.setRegionName(rowSet.getColumnValue(1));
		System.out.println(subGroup.toString());
		return subGroup;
	}
}
