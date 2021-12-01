package com.nttdata.practicaslogs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class TestController {
	
	@Value("${some.valueFar}")
	private String gradoTest;
	
	private Counter counterConsulta;
	private Counter counterConversion;
	
	private final static Logger logger= LoggerFactory.getLogger(TestController.class);
	
	public TestController(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("Invocaciones.contadorConsultaTemp").description("Invocaciones totales").register(registry);
		this.counterConversion = Counter.builder("Invocaciones.contadorConversionTemp").description("Invocaciones totales").register(registry);
	}
	
	@GetMapping(path="/consultaTemp/{tempC}")
	public String consultaTemp(@PathVariable int tempC) {
		counterConsulta.increment();
		logger.info("Se ha llamado a consulta "+counterConsulta.count()+" veces");
		
		if(gradoTest.equals("Celsius")) {
			return "La temperatura "+ tempC +" en fahrenheit es "+convierteTemp(tempC);
		} else {
			return "La temperatura "+tempC+ " en celsius es "+convierteTemp(tempC);
		}
	}
	
	@GetMapping(path="/convierteTemp/{tempC}")
	public int convierteTemp(@PathVariable int tempC) {
		counterConversion.increment();
		
		if(gradoTest.equals("Celsius")) {
			logger.info("Se ha llamado a convierte "+counterConversion.count()+" veces");
			return (tempC*9/5)+32;
		} else {
			logger.info("Se ha llamado a convierte "+counterConversion.count()+" veces");
			return (tempC-32)*(5/9);
		}
		
	}

}
