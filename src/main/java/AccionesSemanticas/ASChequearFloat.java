package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASChequearFloat implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		
		// ver limites
		tablaSimbolos.agregar(cadena.toString(),"float");

		return tablaTokens.getToken("CONSTANTE F");
	}

}
