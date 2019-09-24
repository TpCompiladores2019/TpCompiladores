package com.g3.TpCompiladores;

public class ASAumentarNumLinea implements InterfazAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.nroLinea++;
		return -1;
	}

}
