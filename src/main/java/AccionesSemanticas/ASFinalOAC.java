package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASFinalOAC implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.append(caracter);
		return tablaTokens.getToken(cadena.toString());
	}

}
