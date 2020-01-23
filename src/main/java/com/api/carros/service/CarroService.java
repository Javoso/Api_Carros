package com.api.carros.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.carros.model.Carro;
import com.api.carros.respository.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;

	public Iterable<Carro> getCarros() {
		return repository.findAll();
	}

	public Optional<Carro> getById(Long id) {
		return repository.findById(id);
	}

	public List<Carro> getCarrosByTipo(String tipo) {
		return repository.findByTipo(tipo);
	}

	public Carro save(Carro carro) {
		Assert.isNull(carro.getId(), "Não Foi possivel inserir o registro");
		return repository.save(carro);
	}

	public Carro update(Long id, Carro carro) {
		Assert.notNull(id, "Não foi possivel atualizar o registro");
		Optional<Carro> optional = getById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();
			db.setId(id);
			db.setDescricao(carro.getDescricao());
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			db.setUrl_foto(carro.getUrl_foto());
			db.setUrl_video(carro.getUrl_video());
			db.setLongitude(carro.getLongitude());
			db.setLatitude(carro.getLatitude());
			repository.save(db);
			return db;
		} else {
			throw new RuntimeException("Não foi possivel atualizar o registro");
		}
	}
	
	public void deleteById(Long id) {
		Optional<Carro> db = getById(id);
		if(db.isPresent()) {
			repository.deleteById(id);
		}
	}

}
