package com.g3.TpCompiladores;


import java.io.File;

import java.io.FileWriter;
import java.io.IOException;



import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Sintactico.Parser;
import Lexico.Error;

public class Compilador {

	static int posicion;
	private TablaSimbolos tablaSimbolos;
	private TablaTokens tablaTokens;
	private AnalizadorLexico analizarLexico ;
	private FileWriter fw;	
	
	public Compilador(File ruta) throws IOException {
		this.tablaSimbolos = new TablaSimbolos();
		this.tablaTokens = new TablaTokens();
		this.analizarLexico = new AnalizadorLexico(tablaSimbolos,tablaTokens,ruta);
	}
	
	public void mostrarInfoLexico() {
		int nroToken=-1;
		while (nroToken != 0) {
			nroToken=analizarLexico.yylex();
		}

		try {
			fw = new FileWriter("InformacionLexico.txt");
			String informacion ="";
			
			if (!AnalizadorLexico.listaCorrectas.isEmpty()) {
				informacion = "Tokens Detectados: \n";
				for (String info : AnalizadorLexico.listaCorrectas) {
					informacion += info + "\n";
				}
			}
			else {
				informacion += "Sin tokens \n";
			}
			
			if(!AnalizadorLexico.listaWarning.isEmpty()) {
				informacion += "\nWarnings Detectados: \n";
				for (Error warning : AnalizadorLexico.listaWarning) {
					informacion += warning + "\n"; 
				}
			}
			else {
				informacion += "\nSin Warnings detectados \n";
			}
			
			if (!AnalizadorLexico.listaErrores.isEmpty()) {
				informacion += "\nErrores Detectados: \n";
				for (Error errores : AnalizadorLexico.listaErrores) {
					informacion += errores + "\n";
				}
			}
			else {
				informacion += "\nSin errores detectados \n";
			}
			
			informacion += "\nTabla de Simbolos: \n" ;
			for (String key : tablaSimbolos.tablaSimbolos.keySet()) {
				informacion += key + "--> " + tablaSimbolos.tablaSimbolos.get(key).getTipo() + "\n";
				System.out.println("key: " + key + " tipo: " + tablaSimbolos.tablaSimbolos.get(key).getTipo());
				System.out.println("key: " + key + " cantRef: " + tablaSimbolos.tablaSimbolos.get(key).getCantRef());
			}
		
			fw.write(informacion);
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void mostrarInfoSintactico() {
		
		try {
			fw = new FileWriter("InformacionSintactico.txt");
			Parser parser = new Parser(analizarLexico,tablaSimbolos);
			int sintactico =parser.yyparser(); 
			
			
			String informacion ="";
			
			if (sintactico == 0) {
				informacion = "El programa llego al final. \n";
			}
			else {
				informacion = "El programa no pudo compilar. \n";
			}
			
			if (!AnalizadorLexico.listaCorrectas.isEmpty()) {
				informacion += "\nTokens Detectados: \n";
				for (String info : AnalizadorLexico.listaCorrectas) {
					informacion += info + "\n";
				}
			}
			else {
				informacion += "\nSin tokens \n";
			}
			
			informacion += parser.informacionEstructuras();
			
			informacion += parser.informacionError();
			
			if (!AnalizadorLexico.listaErrores.isEmpty()) {
				informacion += "\nErrores Lexicos Detectados: \n";
				for (Error errores : AnalizadorLexico.listaErrores) {
					informacion += errores + "\n";
				}
			}
			else {
				informacion += "\nSin errores lexicos \n";
			}
			
			
			informacion += "\nTabla de Simbolos: \n" ;
			for (String key : tablaSimbolos.tablaSimbolos.keySet()) {
				informacion += key + "--> " + tablaSimbolos.tablaSimbolos.get(key).getTipo() + "\n";
			}
			
			
			fw.write(informacion);
			fw.close();
			

			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}
	
}
	

