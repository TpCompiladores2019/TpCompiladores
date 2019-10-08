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
		//ParserVal parserVal = new ParserVal();
		String path = "C:\\Users\\Juan\\Desktop\\datos.txt";
		AnalizadorLexico analizarLexico = new AnalizadorLexico(tablaSimbolos,tablaTokens,path);
		Parser parser = new Parser(analizarLexico,tablaSimbolos);
		
		int nroToken=-1;
		/*while (nroToken != 0) {
			nroToken=analizarLexico.yylex();
		}*/

		System.out.println(parser.yyparser());
		tablaSimbolos.imprimir();
		for ( Lexico.Error e: AnalizadorLexico.listaErrores) {
			System.out.println(e.toString());
		}
}
}