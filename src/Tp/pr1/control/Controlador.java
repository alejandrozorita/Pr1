package Tp.pr1.control;

import java.util.Scanner;

import Tp.pr1.logica.Ficha;
import Tp.pr1.logica.Partida;
import Tp.pr1.logica.Tablero;

public class Controlador {
	private Partida partida;
	private Scanner in;
	public Controlador(Partida p, Scanner in){
		partida = p;
		this.in = in;
	}
	public void run(){
		boolean enPartida = true;
		boolean ok;
		Ficha winner;
		int columna;
		do {
			partida.getTablero().pintarTablero();
			System.out.println("Juegan: " + partida.getTurno());
			System.out.println("¿Que quieres hacer?");
			String eleccion = in.next();
			switch (eleccion) {
			case "poner":
			case "Poner":
				do {
					System.out.println("Introduce una columna valida: " + "(1-" + partida.getTablero().getAncho() + ")");
					columna = in.nextInt();
				} while ((columna < 0) || (columna > partida.getTablero().getAncho()));
				partida.ejecutaMovimiento(partida.getTurno(), columna);
				ok = partida.isTerminada();
				
				if (ok && partida.getTablas() == partida.getTablero().getAlto() *partida.getTablero().getAncho()) {
					partida.getTablero().pintarTablero();
					partida.setGanador(Ficha.VACIA);
					System.out.println("La partida ha acabado en tablas");
					enPartida = false;
				}
				else if(ok){
					winner = partida.getTurnoAnterior();
					partida.getTablero().pintarTablero();
					partida.setGanador(winner);
					System.out.println(winner + " is the winner!!");
					enPartida = false;
				}
				break;
			//EN ESPERA A TERMINAR PA TI ENTERO ALE
			case "deshacer":
			case "Deshacer":
				if (!partida.undo()) {
					System.out.println("Imposible deshacer");
				}
				break;
			case "reiniciar":
			case "Reiniciar":
				partida.reset();
				System.out.println("Partida reiniciada");	
				break;
			case "salir":
			case "Salir":
				enPartida = false; 
				break;	
			default:
				System.out.println("Opcion no valida (Salir, Poner, Deshacer, Reiniciar)");
				break;
			}
		} while (enPartida);
	}	
	
//	private boolean enTablas(int tablas){
//		boolean ok = true;
//		if (tablas == partida.getTablero().getAlto() * partida.getTablero().getAncho()) {
//			ok = false;
//			System.out.println("La partida ha acabado en tablas");
//		}
//		return ok;
//	}
//	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		Tablero tablero = new Tablero(6,5);
		tablero.reset();
		Partida partida = new Partida(tablero);
		Controlador controlador = new Controlador(partida, in);
		controlador.run();
	}
}
