package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Error;
import Lexico.Registro;
public class ASFinalEntero implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		if (Integer.parseInt(cadena.toString())< -32768) { // TODO ver que onda porque llega sin signo en verdad
			cadena = new StringBuilder();
			cadena.append(-32768); // TODO se agrega con signo o sin???
			Error nuevoError = new Error("El numero excede el menor entero posible.",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaWarning.add(nuevoError);

			// TODO warning de fuera de rango y cambio de valor al minimo
		}
		else if(Integer.parseInt(cadena.toString())> 32767) {
			cadena.delete(0, cadena.length());
			cadena.append(32768);
			
			Error nuevoError = new Error("El numero excede el mayor entero posible.",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaWarning.add(nuevoError);

			// TODO warning de fuera de rango y cambio de valor al MAXIMO

		}
		tablaSimbolos.agregar(cadena.toString(),new Registro("int"));
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Entera: " + cadena.toString());

		return tablaTokens.getToken("CONSTANTE E");
	}
	
}