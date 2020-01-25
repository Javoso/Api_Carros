package com.api.carros.model.dto;

import org.modelmapper.ModelMapper;

import com.api.carros.model.Carro;

import lombok.Data;

@Data
public class CarroDTO {

	private Long id;
	private String nome;
	private String tipo;

	public static CarroDTO create(Carro carro) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(carro, CarroDTO.class);
	}

}
