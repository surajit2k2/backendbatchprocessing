package com.ibm.batch.dto;

public class SubGroupDTO {


	private Integer id;

	private String subGroupName;


	private int regionId;
	private String regionName;


	public SubGroupDTO() {

	}

	
	
	

	

	public SubGroupDTO(Integer id, String subGroupName) {
		super();
		this.id = id;
		this.subGroupName = subGroupName;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	



	public int getRegionId() {
		return regionId;
	}







	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}







	public String getRegionName() {
		return regionName;
	}







	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}







	@Override
	public String toString() {
		return "SubGroup [id=" + id + ", subGroupName=" + subGroupName + "]";
	}



	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SubGroupDTO))
			return false;
		return id != null && id.equals(((SubGroupDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
