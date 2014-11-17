package logica.test;

import Tp.pr1.logica.Ficha;
import Tp.pr1.logica.Partida;
import Tp.pr1.logica.Tablero;

public class UtilsPartidaYTablero {

	public static boolean tableroVacio(Tablero t) {
		for (int x = 1; x <= t.getAncho(); ++x)
			if (!columnaVacia(t, x))
				return false;
		return true;
	}

	private static boolean columnaVacia(Tablero t, int x) {
		for (int y = 1; y <= t.getAlto(); ++y)
			if (t.getCasilla(x,  y) != Ficha.VACIA)
				return false;
		return true;
	}
	
	private static int columnaAdecuada(Tablero t, Ficha col, int noUses) {
		
		if (noUses == 1) return 6;
		if (noUses == 6) return 1;
		
		if (columnaVacia(t, 1) && columnaVacia(t, 2))
			return 1;
		
		return 6;
	}
	
	// Prepara la partida para que se pueda colocar, en el siguiente movimiento
	// la ficha del color dado en la posición indicada. Para eso utiliza
	// las reglas de la partida de C4. Para que pueda hacerlo, debe haber una columna
	// vacía en el tablero y que la columna donde se quiere colocar
	// cumpla las restricciones del conecta 4...
	// Y también asume que no habrá riesgo de hacer C4 si se pone en
	// alguna de las columnas vacías.
	public static boolean preparaColocacionFicha(Partida p, Ficha color, int x, int y) {
		if (p.isTerminada()) return false;
		Tablero t = p.getTablero();

		// Sanity-check: encima de y no hay nada
		for (int i = y; i >= 1; --i)
			if (t.getCasilla(x, i) != Ficha.VACIA)
				return false;
		
		// Sacamos la fila sobre la que nos apoyaríamos
		int ultimaConFicha = y + 1;
		while ((ultimaConFicha <= t.getAlto()) && (t.getCasilla(x, ultimaConFicha) == Ficha.VACIA))
			ultimaConFicha++;
		
		int aPoner = ultimaConFicha - y; // Con la ficha final que no pondremos

		if ((aPoner % 2 == 1) != (p.getTurno() == color)) {
			// Hay que poner una en una columna dummy para ajustar
			// turno
			int aux = columnaAdecuada(t, p.getTurno(), x);
			if (aux == 0) return false;
			p.ejecutaMovimiento(p.getTurno(), aux);
		}
		
		// Antes de poner, garantizamos que no hay huecos por
		// debajo...
		for (int i = ultimaConFicha + 1; i <= t.getAlto(); ++i) {
			if (t.getCasilla(x,i) == Ficha.VACIA)
				t.setCasilla(x, i, (color == Ficha.BLANCA) ? Ficha.NEGRA : Ficha.BLANCA);
		}
		
		while (aPoner > 1) {
			p.ejecutaMovimiento(p.getTurno(), x);
			aPoner--;
		}
		return true;
	}
	
}
