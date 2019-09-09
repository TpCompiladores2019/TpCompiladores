package com.g3.TpCompiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class main {

	static int posicion;
	
	public int getLardaTrolo() {
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		
		HashMap<String,Integer> tablaTokens = new HashMap<String,Integer>();
		
		tablaTokens.put("ID",1);
		/*tablaTokens.put("CTE",2);
		tablaTokens.put("IF",3);
		tablaTokens.put("ELSE",4);
		tablaTokens.put("END_IF",5);
		tablaTokens.put("PRINT",6);
		tablaTokens.put("INT",7);
		tablaTokens.put("BEGIN",8);
		tablaTokens.put("END",9);
		*/
		
		FileReader fr = new FileReader("C:\\Users\\Juan\\Desktop\\datos.txt");
		BufferedReader bf = new BufferedReader(fr);
		String linea;
		linea=bf.readLine();
		
		while(linea!=null) {
			posicion = 0;
			while (posicion < linea.length()) {
				StringBuilder cadena = leerCadena(posicion,linea);
				System.out.println("Posicion cambia de valor a " + posicion);
				System.out.println("cadena leida " + cadena);
			}
			linea=bf.readLine();
		}
	}
	
	public static StringBuilder leerCadena(int indice, String linea) {
		
		char c = 'a';
		StringBuilder cadenaLeida = new StringBuilder();
		while ( (indice < linea.length() ) && (c!=' ') ){
			c = linea.charAt(indice);
			cadenaLeida.append(c);
			indice++;
		}
		if (c == ' ')
			posicion = indice++;
		
		return cadenaLeida;
	}
	
}
