package com.api.carros.controller;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.carros.model.Carro;
import com.api.carros.model.dto.CarroDTO;
import com.api.carros.service.CarroService;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {

	@Autowired
	public CarroService service;

	@GetMapping()
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CarroDTO>> getCarros() {
		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarroDTO> getById(@PathVariable("id") Long id) {
		return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/tipo/{tipo}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CarroDTO>> getCarrosByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carros = service.getCarrosByTipo(tipo);
		return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
	}

	@PostMapping
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity post(@RequestBody Carro carro) {
		try {
			CarroDTO carroDTO = service.save(carro);
			URI location = getURL(carroDTO.getId());
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	private URI getURL(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		CarroDTO carroDTO = service.update(id, carro);
		return carroDTO != null ? ResponseEntity.ok(carroDTO) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		boolean ok = service.deleteById(id);
		return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
