package com.perisatto.fiapprj.menuguru.application.domain.model;

public enum ProductType {
	LANCHE(1),
	ACOMPANHAMENTO(2),
	BEBIDA(3),
	SOBREMESA(4);
	
	private Integer id;

	ProductType(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
