package com.g3.TpCompiladores;

public class ASChequearFloat implements InterfazAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens) {
		AnalizadorLexico.indiceLectura--;
		
		// ver limites
		
		return tablaTokens.getToken("CONSTANTE F");
	}

}
