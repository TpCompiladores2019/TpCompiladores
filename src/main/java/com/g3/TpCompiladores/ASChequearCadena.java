package com.g3.TpCompiladores;
public class ASChequearCadena implements InterfazAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens) {
		AnalizadorLexico.indiceLectura--; // Esta bien esto??
		String salida;
		if (cadena.length() > 25)
			salida = cadena.substring(0, 24);
		else
			salida = cadena.toString();	
		// FALTA HACER EL WARNING
		//cadena = salida; para que vuelva modifcado, hay que ver como se hace
		
		if (tablaTokens.contieneClave(salida))
			return tablaTokens.getToken(salida);
		return tablaTokens.getToken("IDENTIFICADOR");
		}
	
}