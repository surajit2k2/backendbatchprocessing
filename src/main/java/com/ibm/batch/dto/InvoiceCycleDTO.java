package com.ibm.batch.dto;

import java.time.LocalDate;

public class InvoiceCycleDTO {

	private Integer id;

	private Integer year;

	private String month;

	private LocalDate invoiceStartDate;

	private LocalDate invoiceEndDate;

	private LocalDate timeSheetDueDate;

	private Integer noOfWeekInCycle;

	private Integer weekNumber;

	public InvoiceCycleDTO() {

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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public LocalDate getInvoiceStartDate() {
		return invoiceStartDate;
	}

	public void setInvoiceStartDate(LocalDate invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}

	public LocalDate getInvoiceEndDate() {
		return invoiceEndDate;
	}

	public void setInvoiceEndDate(LocalDate invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}

	public LocalDate getTimeSheetDueDate() {
		return timeSheetDueDate;
	}

	public void setTimeSheetDueDate(LocalDate timeSheetDueDate) {
		this.timeSheetDueDate = timeSheetDueDate;
	}

	public Integer getNoOfWeekInCycle() {
		return noOfWeekInCycle;
	}

	public void setNoOfWeekInCycle(Integer noOfWeekInCycle) {
		this.noOfWeekInCycle = noOfWeekInCycle;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	public InvoiceCycleDTO(Integer id) {
		super();
		this.id = id;
	}
	
	public InvoiceCycleDTO(Integer id, Integer year, String month, LocalDate invoiceStartDate, LocalDate invoiceEndDate,
			LocalDate timeSheetDueDate, Integer noOfWeekInCycle, Integer weekNumber) {
		super();
		this.id = id;
		this.year = year;
		this.month = month;
		this.invoiceStartDate = invoiceStartDate;
		this.invoiceEndDate = invoiceEndDate;
		this.timeSheetDueDate = timeSheetDueDate;
		this.noOfWeekInCycle = noOfWeekInCycle;
		this.weekNumber = weekNumber;
	}

	@Override
	public String toString() {
		return "InvoiceCycle [id=" + id + ", year=" + year + ", month=" + month + ", invoiceStartDate="
				+ invoiceStartDate + ", invoiceEndDate=" + invoiceEndDate + "]";
	}

}
