package com.ibm.batch.dto;

import java.time.LocalDate;


public class WeeklyCycleDTO {


	private Integer id;
	
	
	
	private LocalDate  weekEnding;
	

	private InvoiceCycleDTO invoiceCycle;

	public WeeklyCycleDTO() {

	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public LocalDate getWeekEnding() {
		return weekEnding;
	}


	public void setWeekEnding(LocalDate weekEnding) {
		this.weekEnding = weekEnding;
	}


	public InvoiceCycleDTO getInvoiceCycle() {
		return invoiceCycle;
	}


	public void setInvoiceCycle(InvoiceCycleDTO invoiceCycle) {
		this.invoiceCycle = invoiceCycle;
	}


	public WeeklyCycleDTO(Integer id, LocalDate weekEnding, InvoiceCycleDTO invoiceCycle) {
		super();
		this.id = id;
		this.weekEnding = weekEnding;
		this.invoiceCycle = invoiceCycle;
	}


	public WeeklyCycleDTO(Integer id) {
		super();
		this.id = id;
	}


	@Override
	public String toString() {
		return "WeeklyCycle [id=" + id + ", weekEnding=" + weekEnding + ", invoiceCycle=" + invoiceCycle + "]";
	}


	

	


	
}
