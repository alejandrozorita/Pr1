package logica.test;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import Tp.pr1.logica.Tablero;
import Tp.pr1.logica.Ficha;

public class TableroTest {
	
	private static final int TX = 10;
	private static final int TY = 8;

	@Test
	public void testCtor() {

		for (int x = 1; x <= 10; ++x) {
			for (int y = 1; y <= 10; ++y) {
				Tablero t = new Tablero(x, y);
				assertEquals("El tamaño del tablero no concuerda con el tamaño dado al constructor", x, t.getAncho());
				assertEquals("El tamaño del tablero no concuerda con el tamaño dado al constructor", y, t.getAlto());
			}
		}
	}
	
	@Test
	public void testCtorParamsIncorrectos() {
		try {
			for (int x = -10; x <= 0; ++x) {
				for (int y = -10; y <= 0; ++y) {
					Tablero t = new Tablero(x, y);
					assertEquals("El constructor del tablero debería construir un tablero de (1, 1) con parámetros incorrectos (menores que 1).", 1, t.getAncho());
					assertEquals("El constructor del tablero debería construir un tablero de (1, 1) con parámetros incorrectos (menores que 1).", 1, t.getAlto());
				}
			}
		} catch (Exception ex) {
			fail("El constructor del tablero debería construir un tablero de (1, 1) con parámetros incorrectos (menores que 1).");
		}
	}
	
	@Test
	public void testCtorVaciaTablero() {
		Tablero t = new Tablero(TX, TY);
		assertTrue("Las celdas del tablero deben empezar vacias.", UtilsPartidaYTablero.tableroVacio(t));
	}
	
	@Test
	public void testGetCasilla() {
		Tablero t = new Tablero(TX, TY);
		for (int x = 1; x <= TX; ++x) {
			for (int y = 1; y <= TY; ++y) {
				t.setCasilla(x,  y,  Ficha.BLANCA);
				assertEquals("getCasilla de Tablero no devuelve el valor que acabamos de establecer.", Ficha.BLANCA, t.getCasilla(x,  y));
				t.setCasilla(x,  y,  Ficha.NEGRA);
				assertEquals("getCasilla de Tablero no devuelve el valor que acabamos de establecer.", Ficha.NEGRA, t.getCasilla(x,  y));
				t.setCasilla(x,  y,  Ficha.VACIA);
				assertEquals("getCasilla de Tablero no parece permitir usarse para borrar la ficha con Ficha.VACIA.", Ficha.VACIA, t.getCasilla(x,  y));
			}
		}
	}
	
	@Test
	public void testGetCasillaIncorrecta() {
		try {
			Tablero t = new Tablero(TX, TY);
			for (int x = -TX; x <= 2*TX; ++x) {
				if ((1 <= x) && (x <= TX)) continue;
				for (int y = -TY; y <= 2*TY; ++y) {
					if ((1 <= y) && (y <= TY)) continue;
					assertEquals("getCasilla debe devolver Ficha.VACIA si se pregunta por posiciones incorrectas.", Ficha.VACIA, t.getCasilla(x,  y));
				}
			}
		} catch (Exception ex) {
			fail("getCasilla() no debería fallar si le preguntamos por una posición inválida.");
		}
	}
}
