package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASChequearFloat implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		
		// ver limites
		//if ()
		//Error nuevoError = new Error("El numero excede el mayor flotante posible",AnalizadorLexico.nroLinea," ","WARNING");
		//AnalizadorLexico.listaErrores.add(nuevoError);
		tablaSimbolos.agregar(cadena.toString(),"float");

		return tablaTokens.getToken("CONSTANTE F");
	}

}
