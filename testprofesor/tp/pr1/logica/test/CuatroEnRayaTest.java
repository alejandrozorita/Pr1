package logica.test;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import Tp.pr1.logica.Partida;
import Tp.pr1.logica.Tablero;
import Tp.pr1.logica.Ficha;

public class CuatroEnRayaTest {

	
	private void testCuatroEnRaya(int posX[], int posY[], int ultima, Ficha color) {
		Partida p = new Partida();
		Tablero t = p.getTablero();
		for (int i = 0; i < posX.length; ++i){
			if (i != ultima){
				t.setCasilla(posX[i], posY[i], color);	
			}
		}
		if (!UtilsPartidaYTablero.preparaColocacionFicha(p, color, posX[ultima], posY[ultima]))
			fail("Error interno en los test :-?");
		assertFalse(
				"Partida terminada de forma anticipada con un tablero con tres fichas de color " + color,
				p.isTerminada()
				);
		assertTrue(p.ejecutaMovimiento(color, posX[ultima]));
		assertTrue("Partida no terminada tras cuatro en raya de " + color,
				p.isTerminada());
		assertEquals("Ganador incorrecto tras victoria de " + color, color, p.getGanador());
		
		for (int x = 1; x <= 7; ++x) {
			
			assertFalse("No se debe poder poner tras terminar la partida.", p.ejecutaMovimiento(Ficha.BLANCA, x));
			assertFalse("No se debe poder poner tras terminar la partida.", p.ejecutaMovimiento(Ficha.NEGRA, x));
		}
		
	}
	
	private void pruebaCuatroEnRaya(int posX[], int posY[]) {
		for (int i = 0; i < posX.length; ++i) {
			testCuatroEnRaya(posX, posY, i, Ficha.BLANCA);
			testCuatroEnRaya(posX, posY, i, Ficha.NEGRA);
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontal() {
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= 7 - 3; ++x) {
			for (int y = 1; y <= 6; ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRaya(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	@Test
	public void testCuatroEnRayaVertical() {
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= 7; ++x) {
			for (int y = 1; y <= 6 - 3; ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x;
					posY[l] = y + l;
				}
				
				testCuatroEnRaya(posX, posY, 0, Ficha.BLANCA);
				testCuatroEnRaya(posX, posY, 0, Ficha.NEGRA);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1() {
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= 12; ++i) {
			int sx = Math.max(1, i-5);
			int sy = Math.min(i, 6);
			while ((sy - 4 >= 0) && (sx + 3 <= 7)) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx + l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2() {
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= 12; ++i) {
			int sx = Math.min(i,  7);
			int sy = Math.min(13 - i, 6);
			while ((sy - 4 >= 0) && (sx - 4 >= 0)) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx - l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx--;
			}
		}
	}
	
}
