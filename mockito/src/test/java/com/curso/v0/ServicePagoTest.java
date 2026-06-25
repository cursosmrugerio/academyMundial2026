package com.curso.v0;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Prueba unitaria de {@link ServicePago} con Mockito.
 *
 * Idea clave: ServicePago NO calcula el interes, lo DELEGA en la interface
 * ServiceCalculoInteres. Por eso esa dependencia se sustituye por un MOCK:
 * controlamos su respuesta (stub) y verificamos que ServicePago la usa bien.
 */
@ExtendWith(MockitoExtension.class)
class ServicePagoTest {

	// Doble de prueba (MOCK) de la dependencia. No ejecuta logica real.
	@Mock
	private ServiceCalculoInteres sri;

	// Mockito crea el ServicePago e inyecta el mock por su constructor.
	@InjectMocks
	private ServicePago servicePago;

	@Test
	@DisplayName("calcularInteres delega en la dependencia y retorna su resultado")
	void calcularInteres_delegaYRetornaResultadoDelMock() {
		// Arrange: configuramos (stub) que devolvera el mock
		double cantidad = 1000.0;
		double interesEsperado = 25.0;
		when(sri.calculoInteresesMoratorios(cantidad)).thenReturn(interesEsperado);

		// Act
		double resultado = servicePago.calcularInteres(cantidad);

		// Assert: ServicePago devolvio tal cual lo que entrego la dependencia
		assertEquals(interesEsperado, resultado, 0.0001);
	}

	@Test
	@DisplayName("calcularInteres invoca exactamente una vez a la dependencia")
	void calcularInteres_invocaUnaVezAlMock() {
		when(sri.calculoInteresesMoratorios(500.0)).thenReturn(10.0);

		servicePago.calcularInteres(500.0);

		// Verificamos la interaccion (comportamiento), no solo el valor de retorno
		verify(sri, times(1)).calculoInteresesMoratorios(500.0);
		verifyNoMoreInteractions(sri);
	}
}
