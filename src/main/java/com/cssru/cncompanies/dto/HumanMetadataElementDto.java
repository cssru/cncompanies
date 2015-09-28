package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Human;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class HumanMetadataElementDto {
	
	private Long id;
	
	private Integer type;
	
	private Long numValue;

	private String strValue;

	private byte[] rawData;

}
