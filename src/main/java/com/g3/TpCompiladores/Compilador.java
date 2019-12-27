package com.g3.TpCompiladores;



import java.io.FileWriter;
import java.io.IOException;

import CodigoIntermedio.AnalizadorTercetos;
import CodigoIntermedio.GeneradorAssembler;
import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Token;
import Sintactico.Parser;
import Lexico.Error;

public class Compilador {

	static int posicion;
	private TablaSimbolos tablaSimbolos;
	private TablaTokens tablaTokens;
	private AnalizadorLexico analizarLexico ;
	private FileWriter fw;	
	private AnalizadorTercetos analizadorTerceto;
	private String path;
	
	
	public Compilador(String args) throws IOException {
		this.path=args;
		this.tablaSimbolos = new TablaSimbolos();
		this.tablaTokens = new TablaTokens();
		this.analizarLexico = new AnalizadorLexico(tablaSimbolos,tablaTokens,args);
		this.analizadorTerceto = new AnalizadorTercetos();
	}
	
	public void mostrarInfoLexico() {
		int nroToken=-1;
		while (nroToken != 0) {
			nroToken=analizarLexico.yylex();
		}

		try {
			fw = new FileWriter("InformacionLexico.txt");
			StringBuilder informacion = new StringBuilder();
			
			if (!AnalizadorLexico.listaCorrectas.isEmpty()) {
				informacion.append("Tokens Detectados:" + System.lineSeparator());
				for (String info : AnalizadorLexico.listaCorrectas) {
					informacion.append(info + System.lineSeparator());
				}
			}
			else {
				informacion.append("Sin tokens" + System.lineSeparator());
			}
			
			if(!AnalizadorLexico.listaWarning.isEmpty()) {
				informacion.append(System.lineSeparator() + "Warnings Detectados:" + System.lineSeparator());
				for (Error warning : AnalizadorLexico.listaWarning) {
					informacion.append(warning + System.lineSeparator()); 
				}
			}
			else {
				informacion.append(System.lineSeparator() + "Sin Warnings detectados" + System.lineSeparator());
			}
			
			if (!AnalizadorLexico.listaErrores.isEmpty()) {
				informacion.append(System.lineSeparator() + "Errores Detectados:" + System.lineSeparator());
				for (Error errores : AnalizadorLexico.listaErrores) {
					informacion.append(errores + System.lineSeparator());
				}
			}
			else {
				informacion.append(System.lineSeparator() + "Sin errores detectados" + System.lineSeparator());
			}
			
			informacion.append(System.lineSeparator() + "Tabla de Simbolos:" + System.lineSeparator());
			for (String key : tablaSimbolos.getKeySet()) {
				informacion.append(key + "--> " + tablaSimbolos.getClave(key).getTipo() + System.lineSeparator());
				System.out.println("key: " + key + " tipo: " + tablaSimbolos.getClave(key).getTipo());
				System.out.println("key: " + key + " cantRef: " + tablaSimbolos.getClave(key).getCantRef());
			}
		
			fw.write(informacion.toString());
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void mostrarInfoSintactico(int sintactico,Parser parser) {
		
		try {
			
			
			StringBuilder informacion =new StringBuilder();
			
			if (sintactico != 0) {
				informacion.append("El programa no pudo ser compilado, debido a que una regla de la gramatica no esta contemplada." + System.lineSeparator());
			}
			else {
				informacion.append("El programa llego al final del analisis." + System.lineSeparator());
			
			
			if (!AnalizadorLexico.listaCorrectas.isEmpty()) {
				informacion.append(System.lineSeparator() + "Tokens Detectados:" + System.lineSeparator());
				for (String info : AnalizadorLexico.listaCorrectas) {
					informacion.append(info + System.lineSeparator());
				}
			}
			else {
				informacion.append(System.lineSeparator() + "Sin tokens" + System.lineSeparator());
			}
			
			informacion.append(parser.informacionEstructuras());
			
			informacion.append(parser.informacionError());
			
			if (!AnalizadorLexico.listaErrores.isEmpty()) {
				informacion.append(System.lineSeparator() + "Errores Lexicos Detectados:" + System.lineSeparator());
				for (Error errores : AnalizadorLexico.listaErrores) {
					informacion.append(errores + System.lineSeparator());
				}
			}
			else {
				informacion.append(System.lineSeparator() + "Sin errores lexicos" + System.lineSeparator());
			}
			
			if (!AnalizadorLexico.listaWarning.isEmpty()) {
				informacion.append(System.lineSeparator() + "Warning Lexicos Detectados:" + System.lineSeparator());
				for (Error errores : AnalizadorLexico.listaWarning) {
					informacion.append(errores + System.lineSeparator());
				}
			}
			else {
				informacion.append(System.lineSeparator() + "Sin warning lexicos" + System.lineSeparator());
			}
			
			if (analizadorTerceto.estaVacia()) 
				informacion.append(System.lineSeparator() + "Sin errores semanticos" + System.lineSeparator());
			else
				informacion.append(analizadorTerceto.imprimirErroresSemanticos());
			informacion.append(System.lineSeparator() + "Tabla de Simbolos:" + System.lineSeparator());
			for (String key : tablaSimbolos.getKeySet()) {
				Token clave = tablaSimbolos.getClave(key);
				informacion.append(key + ": Tipo: " + clave.getTipo() + ", Uso:" + clave.getUso()); 
				if (clave.getTamanioColeccion()!=0)
					informacion.append(", Tamaño de Coleccion: " + clave.getTamanioColeccion() + System.lineSeparator());
				else
					informacion.append(System.lineSeparator());
						
			}
			
			
			
			
			analizadorTerceto.imprimirErroresSemanticos();

			}	
			fw.write(informacion.toString());
			
		} catch (IOException e1) {
	
			e1.printStackTrace();
		}

		
	}
	
	public void ejecutar() throws IOException {
		Parser parser = new Parser(analizarLexico,tablaSimbolos,analizadorTerceto);
		int indiceBarra = path.lastIndexOf("\\");
		int indicePunto = path.lastIndexOf(".");
		String pathNombre = path.substring(indiceBarra + 1, indicePunto);
		String pathInfo = path.substring(0, indiceBarra + 1);
		pathInfo = pathInfo + "Informacion" + pathNombre + ".txt";
		int sintactico =parser.yyparser(); 
		fw = new FileWriter(pathInfo);
		String tercetos =""; 
		mostrarInfoSintactico(sintactico, parser);
		if (sintactico == 0) {
			if ((analizarLexico.estaVacia()) && (analizadorTerceto.estaVacia() && parser.estaVacia())){
				tercetos +=analizadorTerceto.imprimirTerceto();
				fw.write(tercetos);
				analizadorTerceto.getTercetoOptimos();
				tercetos =analizadorTerceto.imprimirTerceto();
				fw.write(tercetos);
				GeneradorAssembler assembler = new GeneradorAssembler(analizadorTerceto, tablaSimbolos, path);
				assembler.generarAssembler();
			}
			else
				fw.write("\nNo se pudo genera codigo intermedio por errores en el codigo\n"); 
		}
		fw.close();
	}
	
}
	

