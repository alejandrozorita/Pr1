package Tp.pr1;

import java.util.Scanner;

import Tp.pr1.control.Controlador;
import Tp.pr1.logica.Partida;
import Tp.pr1.logica.Tablero;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Tablero tablero = new Tablero(6,5);
		tablero.reset();
		Partida partida = new Partida(tablero);
		Controlador controlador = new Controlador(partida, in);
		controlador.run();
	}
	//comentario pra GIT
	//otra linea

}
