package com.perisatto.fiapprj.menuguru.adapter.in;

public class CreateCustomerRequestDTO {
	private String documentNumber;
	private String name;
	private String eMail;
	
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
}
