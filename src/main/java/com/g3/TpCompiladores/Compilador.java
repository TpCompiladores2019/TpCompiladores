package com.g3.TpCompiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class Compilador {

	static int posicion;
	
	public static void main(String[] args) throws IOException {

		TablaSimbolos tablaSimbolos =  new TablaSimbolos();
		TablaTokens tablaTokens  = new TablaTokens();
		AnalizadorLexico analizarLexico = new AnalizadorLexico(tablaSimbolos,tablaTokens);
		for (int i =0 ; i < 2 ; i++) {
			System.out.println("Nro de token: " + analizarLexico.getToken());

		}
		tablaSimbolos.imprimir();
		for ( Lexico.Error e: AnalizadorLexico.listaErrores) {
			System.out.println(e.toString());
		}
			
}
}