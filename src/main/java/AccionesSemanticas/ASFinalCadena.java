package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASFinalCadena implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
	
	//	tablaSimbolos.agregar(cadena.toString(),"cadena");
		return tablaTokens.getToken("CADENA");
	}

}
