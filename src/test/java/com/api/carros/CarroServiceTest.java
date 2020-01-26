package com.api.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.carros.exception.ObjectNotFoundException;
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

		CarroDTO od = service.getById(id);
		assertNotNull(od);

		dto = od;

		assertEquals("Ferrari", dto.getNome());
		assertEquals("Esportivo", dto.getTipo());

		service.deleteById(dto.getId());

		try {
			assertNull(service.getById(id));
			fail("O carro não foi excluido");
		} catch (ObjectNotFoundException e) {
			// OK
		}
	}

	@Test
	public void testGet() {
		CarroDTO od = service.getById(11L);
		assertNotNull(od);
		assertEquals("Ferrari FF", od.getNome());
	}

	@Test
	public void listarTodosOsCarrosParaFazerUmaContagem() {
		List<CarroDTO> dtos = service.getCarros();

		System.out.println(dtos.toString());

		// assertEquals(30, dtos.size());
	}

	@Test
	public void getPorTipo() {
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(0, service.getCarrosByTipo("X").size());
	}

}
