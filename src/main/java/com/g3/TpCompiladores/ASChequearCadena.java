package com.g3.TpCompiladores;
public class ASChequearCadena implements InterfazAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--; // Esta bien esto??
		String salida;
		if (cadena.length() > 25)
			salida = cadena.substring(0, 24);
		else
			salida = cadena.toString();	
		// FALTA HACER EL WARNING
		//cadena = salida; para que vuelva modifcado, hay que ver como se hace
				
		if (tablaTokens.contieneClave(salida)) // Es palabra reservada
			return tablaTokens.getToken(salida);

		tablaSimbolos.agregar(salida,"String"); // Si es palabra reservbada no se agrega a la tabla de simbolos
		return tablaTokens.getToken("IDENTIFICADOR");
		}
	
}