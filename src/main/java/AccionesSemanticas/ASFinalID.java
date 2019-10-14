package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASFinalID implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--; // 
		String salida;
		if (cadena.length() > 25) {
			salida = cadena.substring(0, 24);
			Error w = new Error("La variable "+ cadena + " fue truncada",AnalizadorLexico.nroLinea,"","WARNING");
			AnalizadorLexico.listaWarning.add(w);
		}
		else
			salida = cadena.toString();	
		//cadena = salida; para que vuelva modifcado, hay que ver como se hace
				
		if (tablaTokens.contieneClave(salida)) { // Es palabra reservada
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Palabra Reservada: " + cadena.toString());
			return tablaTokens.getToken(salida);
		}

		
		if (!tablaSimbolos.agregar(salida,"")) {
			Error w = new Error("La variable ya fue declarada antes",AnalizadorLexico.nroLinea,"","ERROR");
			AnalizadorLexico.listaErrores.add(w);
		}
		
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Identificador: " + salida);
		return tablaTokens.getToken("IDENTIFICADOR");
		}
	
}