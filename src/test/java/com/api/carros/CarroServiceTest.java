package com.api.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.carros.model.Carro;
import com.api.carros.model.dto.CarroDTO;
import com.api.carros.service.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarroServiceTest {

	@Autowired
	private CarroService service;

	@Test
	public void testeDeveSalvarUmCarroNoBancoDeDados() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("Esportivo");
		CarroDTO dto = service.save(carro);

		assertNotNull(dto);
		Long id = dto.getId();
		assertNotNull(id);

		Optional<CarroDTO> od = service.getById(id);
		assertTrue(od.isPresent());

		dto = od.get();

		assertEquals("Ferrari", dto.getNome());
		assertEquals("Esportivo", dto.getTipo());

		service.deleteById(dto.getId());

		assertFalse(service.getById(id).isPresent());
	}

	@Test
	public void testGet() {
		Optional<CarroDTO> od = service.getById(11L);
		assertTrue(od.isPresent());
		assertEquals("Ferrari FF", od.get().getNome());
	}

	@Test
	public void listarTodosOsCarrosParaFazerUmaContagem() {
		List<CarroDTO> dtos = service.getCarros();

		System.out.println(dtos.toString());

		// assertEquals(30, dtos.size());
	}

	@Test
	public void getPorTipo() {
		assertEquals(20, service.getCarrosByTipo("esportivos").size());
		assertEquals(20, service.getCarrosByTipo("luxo").size());
		assertEquals(20, service.getCarrosByTipo("classicos").size());
		assertEquals(0, service.getCarrosByTipo("X").size());
	}

}
