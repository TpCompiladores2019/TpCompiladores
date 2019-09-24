package com.g3.TpCompiladores;

public class ASCadenaUnaLinea implements InterfazAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
	
		tablaSimbolos.agregar(cadena.toString(),"cadena");
		return tablaTokens.getToken("CADENA");
	}

}
