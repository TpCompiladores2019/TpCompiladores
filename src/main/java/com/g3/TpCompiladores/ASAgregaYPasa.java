package com.g3.TpCompiladores;

public class ASAgregaYPasa implements InterfazAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.append(caracter);
		return tablaTokens.getToken(cadena.toString());
	}

}
