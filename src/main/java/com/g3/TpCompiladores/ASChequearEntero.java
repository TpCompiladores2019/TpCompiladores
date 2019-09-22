package com.g3.TpCompiladores;
public class ASChequearEntero implements InterfazAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		if (Integer.parseInt(cadena.toString())< Integer.MIN_VALUE) {
			cadena.delete(0, cadena.length()-1);
			cadena.append(Integer.MIN_VALUE);
		}
		else if(Integer.parseInt(cadena.toString())> 32767) {
			cadena.delete(0, cadena.length()-1);
			cadena.append(Integer.MAX_VALUE);
		}
		tablaSimbolos.agregar(cadena.toString(),"int");

		return tablaTokens.getToken("CONSTANTE E");
	}
	
}