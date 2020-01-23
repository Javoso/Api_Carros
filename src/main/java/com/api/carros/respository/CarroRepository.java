package com.api.carros.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.carros.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

	List<Carro> findByTipo(String tipo);
	
}
