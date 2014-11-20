package logica.test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import Tp.pr1.logica.Partida;
import Tp.pr1.logica.Ficha;

public class UndoTest {
	
	Partida p;
	
	@Before
	public void init() {
		p = new Partida();
	}
	
	@Test
	public void testUndoTrasMovimiento() {
		p.ejecutaMovimiento(Ficha.BLANCA, 1);
		assertTrue("Tras un movimiento, undo() debería funcionar", p.undo());
		assertTrue("Al hacer undo tras un movimiento, el tablero debe quedar vacío.", UtilsPartidaYTablero.tableroVacio(p.getTablero()));
		assertEquals("Al hacer undo tras un movimiento, debe ser turno de las blancas.", Ficha.BLANCA, p.getTurno());
		assertFalse("Al hacer undo tras un movimiento, la partida no ha debido terminar.", p.isTerminada());
	}
	
	@Test
	public void testUndo10Veces() {		
		for (int i = 1; i <= 3; ++i)
			for (int x = 1; x <= 7; ++x) {
				System.out.println(i + " " + x);
				p.ejecutaMovimiento(p.getTurno(), x);
				p.getTablero().pintarTablero();
				assertFalse(p.isTerminada());
			}
		
		for (int i = 0; i < 10; ++i) {
			Ficha turno = p.getTurno();
			assertTrue("El undo debería poder hacerse al menos 10 veces.", p.undo());
			p.getTablero().pintarTablero();
			assertTrue("Tras undo el turno no cambio.", turno != p.getTurno());
			assertFalse(p.isTerminada());
		}
		
		for (int i = 0; i < 28; ++i) {
			int x = 1 + (i % 7);
			int y = 6 - (i / 7);
			Ficha color = (i % 2 == 0) ? Ficha.BLANCA : Ficha.NEGRA;
			if (i >= 11) color = Ficha.VACIA;
			assertEquals("Tras el undo el tablero no se mantiene igual en (" + x + ", " + y + ").", color,
					p.getTablero().getCasilla(x,  y));
		}
	}
	
	@Test
	public void testNoUndoTrasReset() {
		assertTrue(p.ejecutaMovimiento(p.getTurno(), 3));
		p.reset();
		assertFalse("Tras reset, undo() no debe funcionar.", p.undo());
	}
}
