package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASAumentarNumLinea implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.nroLinea++;
		return -1;
	}

}