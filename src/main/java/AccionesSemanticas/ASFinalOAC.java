package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASFinalOAC implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.append(caracter);
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + ": " + cadena.toString());
		return tablaTokens.getToken(cadena.toString());
	}

}
