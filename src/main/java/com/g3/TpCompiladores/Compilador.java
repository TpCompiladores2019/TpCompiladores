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
	
	public Compilador(String args) throws IOException {
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
			for (String key : tablaSimbolos.getKeySet()) {
				informacion += key + "--> " + tablaSimbolos.getClave(key).getTipo() + "\n";
				System.out.println("key: " + key + " tipo: " + tablaSimbolos.getClave(key).getTipo());
				System.out.println("key: " + key + " cantRef: " + tablaSimbolos.getClave(key).getCantRef());
			}
		
			fw.write(informacion);
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void mostrarInfoSintactico(int sintactico,Parser parser) {
		
		try {
			

			
			
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
			
			if (!AnalizadorLexico.listaWarning.isEmpty()) {
				informacion += "\nWarning Lexicos Detectados: \n";
				for (Error errores : AnalizadorLexico.listaWarning) {
					informacion += errores + "\n";
				}
			}
			else {
				informacion += "\nSin warning lexicos \n";
			}
			
			if (analizadorTerceto.estaVacia()) 
				informacion += "\nSin errores semanticos \n";
			else
				informacion += analizadorTerceto.imprimirErroresSemanticos();
			informacion += "\nTabla de Simbolos: \n" ;
			for (String key : tablaSimbolos.getKeySet()) {
				Token clave = tablaSimbolos.getClave(key);
				informacion += key + ": Tipo: " + clave.getTipo() + ", Uso:" + clave.getUso(); 
				if (clave.getTamanioColeccion()!=0)
					informacion += ", Tamaño de Coleccion: " + clave.getTamanioColeccion() + "\n";
				else
					informacion += "\n";
						
			}
			
			
			fw.write(informacion);
			
			
		//	analizadorTerceto.imprimirTerceto();
			analizadorTerceto.imprimirErroresSemanticos();

					
			
		} catch (IOException e1) {
	
			e1.printStackTrace();
		}

		
	}
	
	public void ejecutar() throws IOException {
		Parser parser = new Parser(analizarLexico,tablaSimbolos,analizadorTerceto);
		int sintactico =parser.yyparser(); 
		fw = new FileWriter("Informacion.txt");
		String tercetos =""; 
		mostrarInfoSintactico(sintactico, parser);
		if (sintactico == 0) {
			if ((analizarLexico.estaVacia()) && (analizadorTerceto.estaVacia() && parser.estaVacia())){
				System.out.println(analizadorTerceto.imprimirTerceto());
				tercetos +=analizadorTerceto.imprimirTerceto();
				fw.write(tercetos);
				GeneradorAssembler assembler = new GeneradorAssembler(analizadorTerceto, tablaSimbolos);
				assembler.generarAssembler();
			}
			else
				fw.write("\nNo se genera codigo intermedio por errores en el codigo\n"); 
		}
		fw.close();
	}
	
}
	

