package com.g3.TpCompiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class main {

	static int posicion;
	
	public static void main(String[] args) throws IOException {

		TablaSimbolos tablaSimbolos =  new TablaSimbolos();
		AnalizadorLexico analizarLexico = new AnalizadorLexico(tablaSimbolos);
		for (int i =0 ; i < 5 ; i++) {
			System.out.println("Nro de token: " + analizarLexico.getToken());

		}
		analizarLexico.imprimirTablaSimbolos();
}
}