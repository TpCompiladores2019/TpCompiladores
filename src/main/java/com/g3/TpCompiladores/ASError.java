package com.g3.TpCompiladores;

public class ASError implements InterfazAccionSemantica {


	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		return -100;
	}

}
