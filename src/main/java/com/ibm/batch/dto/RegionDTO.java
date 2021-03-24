package com.ibm.batch.dto;

public class RegionDTO {

	private int id=0;
	
	private String regionName;
			
	public RegionDTO() {

	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}




	public String getRegionName() {
		return regionName;
	}




	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}



	@Override
	public String toString() {
		return "Region [id=" + id + ", regionName=" + regionName + "]";
	}




	public RegionDTO(int id, String regionName) {
		super();
		this.id = id;
		this.regionName = regionName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegionDTO )) return false;
        return id != 0 && id == (((RegionDTO) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
	

	


	
}
