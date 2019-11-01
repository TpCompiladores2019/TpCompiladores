package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASAgregar extends IAccionSemantica {
	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.append(caracter);
		//return tablaTokens.getToken(cadena.toString());  NO VA PORQUE SI TENEMOS: palabra y palabras, simepre corta en la mas corta
		return -1;
	}
}
