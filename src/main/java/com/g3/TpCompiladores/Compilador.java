package com.g3.TpCompiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Sintactico.Parser;

public class Compilador {

	static int posicion;
	
	public static void main(String[] args) throws IOException {
		
		TablaSimbolos tablaSimbolos =  new TablaSimbolos();
		TablaTokens tablaTokens  = new TablaTokens();
		String path = "C:\\Users\\Larda\\Desktop\\datos.txt";
		
		AnalizadorLexico analizarLexico = new AnalizadorLexico(tablaSimbolos,tablaTokens,path);
		for (int i =0 ; i < 7 ; i++) {
			System.out.println("Nro de token: " + analizarLexico.yylex());

		}
		tablaSimbolos.imprimir();
		for ( Lexico.Error e: AnalizadorLexico.listaErrores) {
			System.out.println(e.toString());
		}
}
}