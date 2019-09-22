package com.g3.TpCompiladores;
public class ASChequearEntero implements InterfazAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		if (Integer.parseInt(cadena.toString())< -32768) { // TODO ver que onda porque llega sin signo en verdad
			cadena = new StringBuilder();
			cadena.append(-32768); // TODO se agrega con signo o sin???
			// TODO warning de fuera de rango y cambio de valor al minimo
		}
		else if(Integer.parseInt(cadena.toString())> 32767) {
			cadena = new StringBuilder();
			cadena.append(32767);
			// TODO warning de fuera de rango y cambio de valor al MAXIMO

		}
		tablaSimbolos.agregar(cadena.toString(),"int");

		return tablaTokens.getToken("CONSTANTE E");
	}
	
}