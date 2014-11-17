package Tp.pr1.logica;

public class Tablero {
	
	private Ficha [ ][ ] tablero;
	private int ancho;
	private int alto;

	public Tablero(int tx, int ty){
		ancho = tx;
		alto = ty;
		if (ancho <= 0 || alto <= 0) {
			ancho = 1;
			alto = 1;
		}
		this.tablero  = new Ficha[ancho][alto];
		for (int i =0; i < ancho; ++i) {
			for (int j = 0; j < alto; ++j) { 
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	
	public Tablero(){
		this.tablero  = new Ficha[8][10];
	}

	public void pintarTablero(){
		System.out.println(toString());
	}
	
	private String pintarFicha(Ficha ficha){
		String tipoFicha = "";
		switch (ficha) {
		case VACIA:
			tipoFicha = " ";
			break;
		case NEGRA:
			tipoFicha = "X";
			break;
		case BLANCA:
			tipoFicha = "O";
			break;		
		default:
			break;
		}
		return tipoFicha;
	}
	
	public String toString(){
		String strTablero = "";
		for (int y = 0; y < alto; y++) {
			strTablero += "|";
			for (int x = 0; x < ancho; x++) { 
				strTablero += pintarFicha(tablero[x][y]);
			}
			strTablero += "|";
			strTablero += '\n';
		}
		strTablero += "+";
		for (int i = 0; i < ancho; i++) {
			strTablero += "-";
		}
		strTablero += "+";
		strTablero += '\n';
		strTablero += " ";
		for (int i = 0; i < ancho; i++) {
			strTablero += i+1;
		}
		strTablero += " ";
		
		return strTablero;
	}
	
	public void reset(){
		for (int i =0; i < ancho; ++i) {
			for (int j = 0; j < alto; ++j) { 
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	
	public int getAlto(){
		return alto;
	}
	
	public int getAncho(){
		return ancho;
	}
	
 	public Ficha getCasilla(int x, int y) {
 		int aux_x, aux_y;
 		aux_x = x - 1;
 		aux_y = y - 1;
		if (aux_x < 0 || aux_x >= getAncho()) {
			return Ficha.VACIA;
		}
 		else if(aux_y < 0 || aux_y >= getAlto()){
 			return Ficha.VACIA;
 		}
 		return tablero[aux_x][aux_y];
 	}
 	
 	public void setCasilla(int x,int y, Ficha color) {
 		tablero[x-1][y-1] = color;
 	}
	
 	public int fichaUltimaJugada(int x) {
 		int fila =-1;
 		for (int i = 0; i < alto; i++) {
 			if (tablero[x - 1][i].equals(Ficha.VACIA) ) {
				fila = i; 
			}
		}
 		return fila;
 	}
	
public static void main(String[] args) {
}
}
