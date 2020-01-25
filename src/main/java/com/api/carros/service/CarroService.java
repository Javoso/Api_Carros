package com.api.carros.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.carros.model.Carro;
import com.api.carros.model.dto.CarroDTO;
import com.api.carros.respository.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;

	public List<CarroDTO> getCarros() {
		return repository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public Optional<CarroDTO> getById(Long id) {
		return repository.findById(id).map(CarroDTO::create);
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return repository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO save(Carro carro) {
		Assert.isNull(carro.getId(), "Não Foi possivel inserir o registro");
		return CarroDTO.create(repository.save(carro));
	}

	public CarroDTO update(Long id, Carro carro) {
		Assert.notNull(id, "Não foi possivel atualizar o registro");
		Optional<Carro> optional = repository.findById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();
			db.setId(id);
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			db.setDescricao(carro.getDescricao());
			db.setUrl_foto(carro.getUrl_foto());
			db.setUrl_video(carro.getUrl_video());
			db.setLongitude(carro.getLongitude());
			db.setLatitude(carro.getLatitude());
			repository.save(db);
			return CarroDTO.create(db);
		} else {
			throw new RuntimeException("Não foi possivel atualizar o registro");
		}
	}

	public boolean deleteById(Long id) {
		if (getById(id).isPresent()) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

}
