package com.g3.TpCompiladores;

import java.io.IOException;



public class MainSintactico {

	public static void main(String[] args) throws IOException {
			Compilador compilador = new Compilador(args[0]);
			compilador.mostrarInfoSintactico();
			System.out.println("termino");
	}
}
