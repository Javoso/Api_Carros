package com.api.carros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.carros.model.Carro;
import com.api.carros.service.CarroService;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {

	@Autowired
	public CarroService service;

	@GetMapping()
	public ResponseEntity<Iterable<Carro>> getCarros() {
		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carro> getById(@PathVariable("id") Long id) {
		return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<Carro>> getCarrosByTipo(@PathVariable("tipo") String tipo) {
		List<Carro> carros = service.getCarrosByTipo(tipo);
		return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
	}

	@PostMapping
	public String post(@RequestBody Carro carro) {
		Carro c = service.save(carro);
		return "Carro salvo com sucesso, ID: " + c.getId();
	}

	@PutMapping("/{id}")
	public Carro put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		System.out.println(carro.getNome());
		System.out.println(id);
		return service.update(id, carro);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.deleteById(id);
		return "Registro excluído com sucesso";
	}
}
