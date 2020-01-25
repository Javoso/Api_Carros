package com.api.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.carros.model.Carro;
import com.api.carros.model.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiCarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiCarrosTest {

	@Autowired
	protected TestRestTemplate restTemplate;

	private ResponseEntity<CarroDTO> getCarro(String url) {
		return restTemplate.getForEntity(url, CarroDTO.class);
	}

	private ResponseEntity<List<CarroDTO>> getCarros(String url) {
		return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CarroDTO>>() {
		});
	}

	@Test
	public void testEndPointSalvar() {
		Carro carro = new Carro();
		carro.setNome("UNO");
		carro.setTipo("luxo");

		ResponseEntity response = restTemplate.postForEntity("/api/v1/carros/", carro, null);
		System.out.println(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		String location = response.getHeaders().get("location").get(0);

		CarroDTO dto = getCarro(location).getBody();

		assertEquals(carro.getNome(), dto.getNome());
		assertEquals(carro.getTipo(), dto.getTipo());

		restTemplate.delete(location);

		assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());

	}

	@Test
	public void testEndPointListarTodos() {
		List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
		assertNotNull(carros);
		assertEquals(60, carros.size());

	}

	@Test
	public void testEndPointNotFound() {
		ResponseEntity response = getCarro("/api/v1/carros/1100");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testEndPointListarCarrosPorTipo() {
		assertEquals(20, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
		assertEquals(20, getCarros("/api/v1/carros/tipo/luxo").getBody().size());
		assertEquals(20, getCarros("/api/v1/carros/tipo/classicos").getBody().size());

		assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xx").getStatusCode());
	}

	@Test
	public void testEndPointFindById() {
		ResponseEntity<CarroDTO> dto = getCarro("/api/v1/carros/11");
		assertEquals(HttpStatus.OK, dto.getStatusCode());

		CarroDTO car = dto.getBody();

		assertEquals("Ferrari FF", car.getNome());
	}

}
