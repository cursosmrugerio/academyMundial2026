package com.curso.v0;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas unitarias de la operación División")
class DivisionTest {

	@Test
	@DisplayName("División exacta: 10 / 2 = 5")
	void divisionExacta() {
		Division division = new Division(10, 2);
		assertEquals(5, division.ejecuta());
	}

	@Test
	@DisplayName("División entera trunca el decimal: 7 / 2 = 3")
	void divisionEnteraTrunca() {
		Division division = new Division(7, 2);
		assertEquals(3, division.ejecuta());
	}

	@Test
	@DisplayName("Un número dividido entre sí mismo es 1: 5 / 5 = 1")
	void divisionDeUnNumeroEntreSiMismo() {
		Division division = new Division(5, 5);
		assertEquals(1, division.ejecuta());
	}

	@Test
	@DisplayName("Dividir entre 1 devuelve el mismo dividendo: 8 / 1 = 8")
	void divisionEntreUno() {
		Division division = new Division(8, 1);
		assertEquals(8, division.ejecuta());
	}

	@Test
	@DisplayName("El dividendo cero da cero: 0 / 5 = 0")
	void dividendoCeroDaCero() {
		Division division = new Division(0, 5);
		assertEquals(0, division.ejecuta());
	}

	@Test
	@DisplayName("Dividendo negativo: -10 / 2 = -5")
	void divisionConDividendoNegativo() {
		Division division = new Division(-10, 2);
		assertEquals(-5, division.ejecuta());
	}

	@Test
	@DisplayName("Divisor negativo: 10 / -2 = -5")
	void divisionConDivisorNegativo() {
		Division division = new Division(10, -2);
		assertEquals(-5, division.ejecuta());
	}

	@Test
	@DisplayName("Ambos negativos dan positivo: -10 / -2 = 5")
	void divisionConAmbosNegativos() {
		Division division = new Division(-10, -2);
		assertEquals(5, division.ejecuta());
	}

	@Test
	@DisplayName("Dividir entre cero lanza ArithmeticException")
	void divisionEntreCeroLanzaExcepcion() {
		Division division = new Division(10, 0);
		assertThrows(ArithmeticException.class, () -> division.ejecuta());
	}
}
