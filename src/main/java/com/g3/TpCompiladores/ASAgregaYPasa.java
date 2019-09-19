package com.g3.TpCompiladores;

public class ASAgregaYPasa implements InterfazAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens) {
		cadena.append(caracter);
		return tablaTokens.getToken(cadena.toString());
	}

}
