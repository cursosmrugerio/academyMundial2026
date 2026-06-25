package com.curso.v0;

// Importamos SOLO las assertions estáticas que vamos a usar.
// Una "assertion" es una afirmación: le decimos a JUnit "esto DEBE ser así".
// Si no se cumple, la prueba falla y JUnit nos dice qué esperábamos vs. qué obtuvimos.
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

/**
 * Prueba unitaria de la clase {@link Suma}.
 *
 * IMPORTANTE - dónde vive este archivo: Está en la carpeta de fuentes "test"
 * (separada de "src", donde vive el código de producción). Es una buena
 * práctica: el código de pruebas no se mezcla ni se empaqueta con el código
 * real, pero ambos comparten el MISMO paquete.
 *
 * Mantenemos el paquete com.curso.v0 a propósito: el constructor de Suma es de
 * paquete (no tiene public/private), así que solo podemos crear objetos Suma
 * desde una clase del mismo paquete. En Java lo que importa es el PAQUETE, no
 * la carpeta física, por eso "test/com/curso/v0" y "src/com/curso/v0" se ven
 * como el mismo paquete.
 *
 * Concepto clave: una prueba UNITARIA prueba UNA sola unidad (aquí, la clase
 * Suma) de forma aislada, sin depender de otras clases del proyecto.
 */
class SumaTest {

	/**
	 * Cada método anotado con @Test es UN caso de prueba independiente. JUnit crea
	 * una instancia nueva de SumaTest y ejecuta el método por separado, así una
	 * prueba no contamina a las demás.
	 *
	 * @DisplayName solo cambia el nombre legible que se ve en el reporte de JUnit;
	 *              no afecta la lógica, pero ayuda a entender qué se está probando.
	 */
	@Test
	@DisplayName("ejecuta() debe sumar dos números positivos")
	void ejecuta_dosPositivos_devuelveLaSuma() {

		int x = 8;
		int y = 4;

		// 1) PREPARAR (Arrange): construimos el objeto con datos conocidos.
		Suma suma = new Suma(x, y);

		// 2) ACTUAR (Act): llamamos al método que queremos probar.
		int resultado = suma.ejecuta();

		// 3) VERIFICAR (Assert): comprobamos que el resultado sea el esperado.
		// assertEquals(valorEsperado, valorReal): falla si NO son iguales.
		assertEquals(12, resultado, "8 + 4 debería dar 12");
	}

	@Test
	@DisplayName("ejecuta() debe funcionar con números negativos")
	void ejecuta_conNegativos_devuelveLaSuma() {
		// Probamos un caso "frontera": sumar negativos.
		// Es buena práctica probar más que el caso feliz para confiar en la clase.
		Suma suma = new Suma(-5, -3);

		assertEquals(-8, suma.ejecuta(), "-5 + -3 debería dar -8");
	}

	@Test
	@DisplayName("ejecuta() con cero como sumando devuelve el otro número")
	void ejecuta_conCero_devuelveElOtroNumero() {
		// Caso especial: el 0 es el elemento neutro de la suma.
		Suma suma = new Suma(7, 0);

		assertEquals(7, suma.ejecuta(), "7 + 0 debería dar 7");
	}

	@Test
	@DisplayName("ejecuta() NO debe devolver un resultado incorrecto")
	void ejecuta_resultadoIncorrecto_noCoincide() {
		// assertNotEquals afirma lo contrario: el resultado NO debe ser ese valor.
		// Sirve para demostrar que la prueba realmente distingue valores correctos
		// de incorrectos (no que "siempre pasa").
		Suma suma = new Suma(2, 2);

		assertNotEquals(5, suma.ejecuta(), "2 + 2 nunca debería dar 5");
	}

	@Test
	@DisplayName("toString() debe devolver el texto con el formato esperado")
	void toString_devuelveFormatoEsperado() {
		// También podemos probar otros métodos públicos de la clase.
		// Aquí verificamos que la representación en texto sea exactamente la esperada.
		Suma suma = new Suma(8, 4);

		assertEquals("Suma: [x=8, y=4]", suma.toString());
	}

	@Test
	@DisplayName("CARACTERIZACIÓN: MAX_VALUE + 1 se desborda a MIN_VALUE")
	void suma_overflow_daLaVuelta() {
		Suma suma = new Suma(Integer.MAX_VALUE, 1); // el escenario de Principal2
		assertEquals(Integer.MIN_VALUE, suma.ejecuta()); // -2147483648
	}

}
