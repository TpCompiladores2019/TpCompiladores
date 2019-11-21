package com.g3.TpCompiladores;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Compilador compilador = new Compilador(args[0]);
		compilador.ejecutar();
		System.out.println("termino");
		
			

	}

}
