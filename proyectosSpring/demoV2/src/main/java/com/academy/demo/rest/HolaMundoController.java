package com.academy.demo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoController {

	@GetMapping("/hola")
	public String holaMundo() {
		return "Hello World Academy Java";
	}

}
