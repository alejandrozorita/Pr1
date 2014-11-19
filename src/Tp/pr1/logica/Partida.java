package Tp.pr1.logica;


public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private Ficha ganador;
	private int [] arrayJugadas;
	private int contadorArrayJugadas = 0;
	private int tablas = 0;
	
	public Partida(Tablero tablero){
		this.tablero = tablero;
		turno = Ficha.BLANCA;
		ganador = Ficha.VACIA;
		terminada = false;
		arrayJugadas = new int[10];
	}
	
	public Partida(){
		this.tablero = new Tablero(7,6);
		turno = Ficha.BLANCA;
		ganador = Ficha.VACIA;
		terminada = false;
		arrayJugadas = new int[10];
	}
	
	public void reset(){
		tablero.reset();
		turno = Ficha.BLANCA;
		contadorArrayJugadas = 0;
		arrayJugadas[contadorArrayJugadas] = -1;
	}
	
	private void desplazarArray(int auxDesArray){
		
		for (int i = 0; i < arrayJugadas.length-1;i++) {
			arrayJugadas[i] = arrayJugadas[i + 1];
		}
		arrayJugadas[arrayJugadas.length-2] = auxDesArray;
	}
	
	private void aumentarContador(int auxDesArray){
		if(contadorArrayJugadas == 11) {
			desplazarArray(auxDesArray);
		}
	}
	public int getTablas(){
		return tablas;
	}
	public void setGanador(Ficha ficha){
		ganador = ficha;
	}
	
	private void disminuirContador(){
		if (contadorArrayJugadas != 0) {
			contadorArrayJugadas--;
		}
	}
	
	public int getContadorArrayJugadas(){
		return contadorArrayJugadas;
	}
	

	public boolean ejecutaMovimiento(Ficha color, int col){
		boolean posible = true;
		if(terminada == false){	
			if (col > 0 && col < tablero.getAncho()+1) {	
				if ((turno == Ficha.VACIA) ||(turno != color) || (tablero.fichaUltimaJugada(col) < 0)) {
					posible = false;
				}
				if (posible) {
					int auxDesArray = 0;
					tablero.setCasilla(col, tablero.fichaUltimaJugada(col) + 1, color);
					if (turno == Ficha.BLANCA) {
						turno = Ficha.NEGRA;
					} else if(turno == Ficha.NEGRA) {
						turno = Ficha.BLANCA;
					}
					
					if (contadorArrayJugadas == 10) {
						auxDesArray = arrayJugadas[contadorArrayJugadas-1];
						arrayJugadas[contadorArrayJugadas-1] = col;
						++contadorArrayJugadas;
					}
					else if (contadorArrayJugadas == 11)
					{
						auxDesArray = arrayJugadas[contadorArrayJugadas-2];
						arrayJugadas[contadorArrayJugadas-2] = col;
					}
					else
					{
						arrayJugadas[contadorArrayJugadas] = col;
						++contadorArrayJugadas;
					}
						aumentarContador(auxDesArray);
					//mueves el array una posici�n a la izquierda
					tablas++;
				}
			}
			else {
				posible = false;
			}
		}else {
			posible = false;
		}
		return posible;
	}
	
	private int GetColumnaUltimoMovimiento(){
		return arrayJugadas[contadorArrayJugadas - 1];
	}
	
	private int GetFilaUltimoMovimiento(){
		int fila = 0;
		fila = tablero.fichaUltimaJugada(arrayJugadas[contadorArrayJugadas - 1]);
		return fila;
	}
	
	public boolean undo(){
		boolean ok = true;
		//Iniciamos contador para no machacar contador original de partida
		int auxContadorArrayJugadas = 0;
		
		//Si contador es mayor que length de array es porque apunta a ultima direcccion
		if (contadorArrayJugadas > arrayJugadas.length - 1) {
			auxContadorArrayJugadas = arrayJugadas.length-1;
		}
		else {
			auxContadorArrayJugadas = contadorArrayJugadas;
		}
		if (arrayJugadas[auxContadorArrayJugadas] < 0) {
			ok = false;
		}
		else if (turno == Ficha.BLANCA && auxContadorArrayJugadas == 0){
			ok = false;
		} 
		else{System.out.println("else");
			//recibe la i-1 donde esta colocada la ficha que hay que deshacer
			int posicionUltimaFichaEnI = (tablero.fichaUltimaJugada(arrayJugadas[auxContadorArrayJugadas - 1]+1));
			//Pasamos X e Y a tablero para que ponga vacia en la posicion de la ultima jugada

			tablero.setCasilla(/*Pasamos la x*/arrayJugadas[auxContadorArrayJugadas - 1] + 1, /*Pasamos la i*/(posicionUltimaFichaEnI + 2), /*Pasamos la vacia*/Ficha.VACIA);
			//Ponemos a -1 launcher posicion del array
			arrayJugadas[auxContadorArrayJugadas] = -1;
			contadorArrayJugadas = auxContadorArrayJugadas;
			disminuirContador();
			tablas--;
			setTurno();
		}
		return ok;
	}
	
	
	public void setTurno() {
		turno = getTurnoAnterior();
	}


	public Ficha getGanador(){
		if (isTerminada()) {
			if (getTablas() == getTablero().getAlto() *getTablero().getAncho()) {
				ganador = Ficha.VACIA;
			} else{
				ganador = getTurnoAnterior();
			}
		}
		return ganador;
	}
	
	public boolean isTerminada() {
		terminada = false;
		if (tablas == 0) {
			terminada = false;
		}
		else if (comprobarAncho() || comprobarAlto() || comprobarDiagonal() || getTablas() == getTablero().getAlto() *getTablero().getAncho()) {
			terminada = true;
		}
		else if (contadorArrayJugadas == 0) {
			terminada = false;
		}
		return terminada;
	}
	
	private boolean comprobarAlto() {
		boolean altoOk = false;
		int fila, columna, contadorAlto = 0;
		Ficha casilla, siguienteCasilla;
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		//COMPROBAR ALTO HACIA ABAJO
				if (fila + 2 < tablero.getAlto()) {
					do {
						casilla = tablero.getCasilla(columna, fila);
						siguienteCasilla = tablero.getCasilla(columna, fila + 1);
						if (casilla.equals(siguienteCasilla)) {
							contadorAlto++;
							fila++;
						} 
					} while (casilla.equals(siguienteCasilla) && contadorAlto < 3);
				}
		//------------------*/
		if (contadorAlto >= 3) {
			altoOk = true;
		}
		return altoOk;
	}

	private boolean comprobarAncho() {
		boolean anchoOk = false;
		int fila, columna, contadorAncho = 0;
		Ficha casilla, siguienteCasilla;
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		//COMPROBAR EL ANCHO HACIA LA DERECHA 
		if (columna  < tablero.getAncho()) {
			do {
				casilla = tablero.getCasilla(columna, fila);
				siguienteCasilla = tablero.getCasilla(columna + 1, fila);
				if (casilla.equals(siguienteCasilla)) {
					contadorAncho++;
					columna++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAncho < 3);
		}
		//------------------
		if (contadorAncho != 3) {
			contadorAncho = 0;
		}
		//COMPROBAR EL ANCHO HACIA LA IZQUIERDA 
		if(contadorAncho == 0){
			if (columna - 2 > 0) {
				do {
					casilla = tablero.getCasilla(columna, fila);
					siguienteCasilla = tablero.getCasilla(columna -1, fila);
					if (casilla.equals(siguienteCasilla)) {
						contadorAncho++;
						columna--;
					} 
				} while (casilla.equals(siguienteCasilla) && contadorAncho < 3);
			}
		}
		if (contadorAncho >= 3) {
			anchoOk = true;
		}
		return anchoOk;
	}

	private boolean comprobarDiagonal(){
		int fila, columna;
		Ficha casilla, siguienteCasilla;
		boolean ok = false;
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		//COMPROBAR DIAGONAL HACIA ARRIBA DERECHA
		int diagonalArriDerOk = 0;
			while ((fila > 0) && (columna < tablero.getAncho())) {
				casilla = tablero.getCasilla(columna, fila);
				siguienteCasilla = tablero.getCasilla(columna +1, fila - 1);
				if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
					diagonalArriDerOk++;
				}
				fila--;
				columna++;		
			}
		if (diagonalArriDerOk >= 3) {
			ok = true;
		}
		//COMPROBAR DIAGONAL HACIA ARRIBA IZQUIERDA	
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila > 0) && (columna > 0)) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna -1, fila - 1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalArriDerOk++;
			}
			fila--;
			columna--;		
		}
		if (diagonalArriDerOk >= 3) {
			ok = true;
		}
		//COMPROBAR DIAGONAL ABAJO IZQUIERDA
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila < tablero.getAlto()) && (columna > 0)) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna -1, fila +1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalArriDerOk++;
			}
			fila++;
			columna--;		
		}
		if (diagonalArriDerOk >= 3) {
			ok = true;
		}
		//COMPROBAR DIAGONAL ABAJO DERECHA
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila < tablero.getAlto()) && (columna < tablero.getAncho())) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna +1, fila +1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalArriDerOk++;
			}
			fila++;
			columna++;		
		}
		if (diagonalArriDerOk >= 3) {
			ok = true;
		}

		return ok;
	}
	
	public Tablero getTablero(){
		return tablero;
	} 
	public Ficha getTurno(){
		return turno;
	}
	public Ficha getTurnoAnterior(){
		if (turno == Ficha.BLANCA) {
			turno = Ficha.NEGRA;
		} else if (turno == Ficha.NEGRA){
			turno = Ficha.BLANCA;
		}
		return turno;
	}
	public static void main(String[] args) {
		
		Tablero NuevoTablero = new Tablero(5,5);
		NuevoTablero.reset();
		Partida nuevaPartida = new Partida(NuevoTablero);
		for (int i = 1; i <= 6; i++) {		
			for (int j = 0; j < nuevaPartida.arrayJugadas.length; j++) {
				System.out.print(nuevaPartida.arrayJugadas[j]);
			}
			System.out.println();
			nuevaPartida.ejecutaMovimiento(nuevaPartida.getTurno(), i);
			NuevoTablero.pintarTablero();
			System.out.println("-----------------");
		}
		System.err.println("segundo bucle");
		for (int i = 1; i <= 6; i++) {		
			for (int j = 0; j < nuevaPartida.arrayJugadas.length; j++) {
				System.out.print(nuevaPartida.arrayJugadas[j]);
			}
			System.out.println();
			nuevaPartida.ejecutaMovimiento(nuevaPartida.getTurno(), i);
			NuevoTablero.pintarTablero();
			System.out.println("---------------");
		}
		System.err.println("tercer bucle");
		for (int i = 1; i <= 6; ++i) {		
			for (int j = 0; j < nuevaPartida.arrayJugadas.length; j++) {
				System.out.print(nuevaPartida.arrayJugadas[j]);
			}
			System.out.println();
			nuevaPartida.ejecutaMovimiento(nuevaPartida.getTurno(), i);
			NuevoTablero.pintarTablero();
			System.out.println("----------------");
		}
		for (int i = 0; i < 11; i++) {
			System.out.println("undo!");
			nuevaPartida.undo();
			NuevoTablero.pintarTablero();
		}
		
	}
}


