package com.ibm.batch.dto;




public class ProjectDTO {


	private Integer id;
	
	private String projectCode;
	
	private String projectName;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String projectStaus;
	
	private String projectManager;
	
	private String deliveryProjectManager;
	

	private SubGroupDTO subGroup;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProjectStaus() {
		return projectStaus;
	}

	public void setProjectStaus(String projectStaus) {
		this.projectStaus = projectStaus;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getDeliveryProjectManager() {
		return deliveryProjectManager;
	}

	public void setDeliveryProjectManager(String deliveryProjectManager) {
		this.deliveryProjectManager = deliveryProjectManager;
	}
	
	

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	

	public SubGroupDTO getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(SubGroupDTO subGroup) {
		this.subGroup = subGroup;
	}
	

	public ProjectDTO(Integer id, String projectCode, String projectName, String projectStartDate, String projectEndDate,
			String projectStaus, String projectManager, String deliveryProjectManager) {
		super();
		this.id = id;
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectStaus = projectStaus;
		this.projectManager = projectManager;
		this.deliveryProjectManager = deliveryProjectManager;
	}
	
	public ProjectDTO(){
		
	}
	
	public ProjectDTO(Integer id){
		super();
		this.id = id;
	}

	public ProjectDTO(Integer id, String projectCode, String projectName, String projectStartDate, String projectEndDate,
			String projectStaus, String projectManager, String deliveryProjectManager, SubGroupDTO subGroup) {
		super();
		this.id = id;
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectStaus = projectStaus;
		this.projectManager = projectManager;
		this.deliveryProjectManager = deliveryProjectManager;
		this.subGroup = subGroup;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", projectCode=" + projectCode + ", projectName=" + projectName
				+ ", projectManager=" + projectManager + ", deliveryProjectManager=" + deliveryProjectManager + "]";
	}

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectDTO )) return false;
        return id != null && id.equals(((ProjectDTO) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
	
	
	

	


	
}
