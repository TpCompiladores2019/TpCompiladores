package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Error;
public class ASChequearEntero implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		if (Integer.parseInt(cadena.toString())< -32768) { // TODO ver que onda porque llega sin signo en verdad
			cadena = new StringBuilder();
			cadena.append(-32768); // TODO se agrega con signo o sin???
			Error nuevoError = new Error("El numero excede el men",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaErrores.add(nuevoError);

			// TODO warning de fuera de rango y cambio de valor al minimo
		}
		else if(Integer.parseInt(cadena.toString())> 32767) {
			cadena = new StringBuilder();
			cadena.append(32767);

			Error nuevoError = new Error("El numero excede el mayor entero posible",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaErrores.add(nuevoError);

			// TODO warning de fuera de rango y cambio de valor al MAXIMO

		}
		tablaSimbolos.agregar(cadena.toString(),"int");

		return tablaTokens.getToken("CONSTANTE E");
	}
	
}