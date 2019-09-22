package com.g3.TpCompiladores;

public class ASAgregar implements InterfazAccionSemantica {
	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.append(caracter);
		//return tablaTokens.getToken(cadena.toString());  NO VA PORQUE SI TENEMOS: palabra y palabras, simepre corta en la mas corta
		return -1;
	}
}
