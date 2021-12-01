package com.nttdata.practicaslogs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class TestController {
	
	private Counter counterConsulta;
	private Counter counterConversion;
	
	private final static Logger logger= LoggerFactory.getLogger(TestController.class);
	
	public TestController(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("Invocaciones.contadorConsultaTemp").description("Invocaciones totales").register(registry);
		this.counterConversion = Counter.builder("Invocaciones.contadorConversionTemp").description("Invocaciones totales").register(registry);
	}
	
	@GetMapping(path="/consultaTemp")
	public String consultaTemp() {
		logger.info("Llamada a consulta.");
		counterConsulta.increment();
		return "La temperatura en Farenheit es:"+convierteTemp(20);
	}
	
	@GetMapping(path="/convierteTemp/{tempC}")
	public int convierteTemp(@PathVariable int tempC) {
		logger.info("Llamada a convierte.");
		counterConversion.increment();
		return (tempC*9/5)+32;
	}

}
