package com.ibm.batch.dto;

public class RateChartDTO {

	private Integer id;

	private Integer year;

	private String ratecode;

	private Integer billcode;

	private double billrate;

	private String info;

	public RateChartDTO() {

	}

	public RateChartDTO(Integer id) {
		super();
		this.id = id;

	}
	
	public RateChartDTO(Integer id, Integer year,  String desc, Integer billcode,
			double billrate, String info) {
		super();
		this.id = id;
		this.year = year;
		this.ratecode = desc;
		this.billcode = billcode;
		this.billrate = billrate;
		this.info = info;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDesc() {
		return ratecode;
	}

	public void setDesc(String desc) {
		this.ratecode = desc;
	}

	public Integer getBillcode() {
		return billcode;
	}

	public void setBillcode(Integer billcode) {
		this.billcode = billcode;
	}

	public double getBillrate() {
		return billrate;
	}

	public void setBillrate(double billrate) {
		this.billrate = billrate;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "RateChart [id=" + id + ", year=" + year + ", desc=" + ratecode + ", billrate=" + billrate + "]";
	}

	public String getRatecode() {
		return ratecode;
	}

	public void setRatecode(String ratecode) {
		this.ratecode = ratecode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof RateChartDTO))
			return false;
		return id != null && id.equals(((RateChartDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
