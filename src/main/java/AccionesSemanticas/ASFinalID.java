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
			AnalizadorLexico.listaWarning.add(w);//AGREGAR A WARNIGN
		}
		else
			salida = cadena.toString();	
		//cadena = salida; para que vuelva modifcado, hay que ver como se hace
				
		if (tablaTokens.contieneClave(salida)) // Es palabra reservada
			return tablaTokens.getToken(salida);

		tablaSimbolos.agregar(salida,"String"); // Si es palabra reservbada no se agrega a la tabla de simbolos //TODO Mirar proque no es STRING
		return tablaTokens.getToken("IDENTIFICADOR");
		}
	
}