package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.Registro;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASFinalFloat extends IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		Double numeroFloat = Double.parseDouble(cadena.toString());
		
		if ((numeroFloat<Float.MIN_NORMAL && numeroFloat > Float.MAX_VALUE) || numeroFloat != 0.0) {
			Error nuevoError = new Error("El numero " + cadena.toString() + " excede el rango de los Float",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaWarning.add(nuevoError);
			cadena.delete(0, cadena.length());
			cadena.append(String.valueOf(Float.MAX_VALUE));
		}
		tablaSimbolos.agregar(cadena.toString(),new Registro("float"));
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Float: " + cadena.toString());
		return tablaTokens.getToken("CONSTANTE F");
	}

}
