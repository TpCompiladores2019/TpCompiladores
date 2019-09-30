package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASErrorCadenaMultilinea implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		System.out.println(cadena); //TODO  falta
		return -1;
	}

}
