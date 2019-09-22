package com.g3.TpCompiladores;

public class ASChequearFloat implements InterfazAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		
		// ver limites
		tablaSimbolos.agregar(cadena.toString(),"float");

		return tablaTokens.getToken("CONSTANTE F");
	}

}
