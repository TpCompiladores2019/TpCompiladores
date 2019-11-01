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
		if (numeroFloat > Float.MAX_VALUE) {
			Error nuevoWarning = new Error("El numero " + cadena.toString() + " excede el rango de los Float, fue truncado a " + Float.MAX_VALUE +".",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaWarning.add(nuevoWarning);
			cadena.delete(0, cadena.length());
			cadena.append(String.valueOf(Float.MAX_VALUE));
		}
		else
			if ((numeroFloat<Float.MIN_NORMAL) && (numeroFloat != 0.0)) {
				Error nuevoWarning = new Error("El numero " + cadena.toString() + " excede el rango de los Float, fue truncado a " + Float.MIN_NORMAL + ".",AnalizadorLexico.nroLinea," ","WARNING");
				AnalizadorLexico.listaWarning.add(nuevoWarning);
				cadena.delete(0, cadena.length());
				cadena.append(String.valueOf(Float.MIN_NORMAL));
			}
		
		tablaSimbolos.agregar(cadena.toString(),new Registro("float"));
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Float: " + cadena.toString());
		return tablaTokens.getToken("CONSTANTE F");
	}

}
