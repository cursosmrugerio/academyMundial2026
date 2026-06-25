package com.curso.v0;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@DisplayName("Pruebas unitarias de la operación Compleja")
class OpeComplejaTest {

	@Test
	@DisplayName("Test Operacion Compleja")
	void operacionComplejaResultado() {
		OperacionCompleja oc = new OperacionCompleja((byte)1,(char)2,(short)3,4,5,6.0,7.0F);
		assertEquals(100, oc.ejecuta());
	}
	
	
	@Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void metodoDebeEjecutarseEnMenosDe1Segundo() {
		OperacionCompleja oc = new OperacionCompleja((byte)1,(char)2,(short)3,4,5,6.0,7.0F);
		oc.ejecuta();
    }


}
