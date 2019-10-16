package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Registro;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASFinalCadena implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
	

		tablaSimbolos.agregar(cadena.toString(),new Registro("Cadena"));
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Cadena: " + cadena.toString());

		return tablaTokens.getToken("CADENA");
	}

}
