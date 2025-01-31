package com.perisatto.fiapprj.user_management.infra.controllers.dtos;

import java.util.LinkedHashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;

public class GetUserListResponseDTO {
	
	@JsonProperty(value = "_page")
	private Integer page;
	@JsonProperty(value = "_size")
	private Integer size;
	@JsonProperty(value = "_pageElements")
	private Integer pageElements;
	
	@JsonProperty(value = "_content")
	private Set<GetUserResponseDTO> content = new LinkedHashSet<>();

	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
		
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPageElements() {
		return pageElements;
	}

	public void setPageElements(Integer pageElements) {
		this.pageElements = pageElements;
	}

	public Set<GetUserResponseDTO> getContent() {
		return content;
	}

	public void setContent(Set<GetUserResponseDTO> content) {
		this.content = content;
	}
	
	public void setContent(Set<User> content, Integer page, Integer size) {
		this.setPage(page);
		this.setSize(size);
		this.setPageElements(content.size());
		ModelMapper customerMapper = new ModelMapper();
		
		for(User customer : content) {
			GetUserResponseDTO newCustomerResponse = customerMapper.map(customer, GetUserResponseDTO.class);
			this.content.add(newCustomerResponse);
		}
	}
}
