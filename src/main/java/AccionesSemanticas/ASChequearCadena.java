package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASChequearCadena implements IAccionSemantica{

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

		tablaSimbolos.agregar(salida,"String"); // Si es palabra reservbada no se agrega a la tabla de simbolos //TODO Mirar proque no es STRING
		return tablaTokens.getToken("IDENTIFICADOR");
		}
	
}