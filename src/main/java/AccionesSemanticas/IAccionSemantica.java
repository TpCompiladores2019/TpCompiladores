package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public interface IAccionSemantica {
	
	// TODO LA TABLA DE SIMBOLOS ES POR PARAMETROS O LA HACEMOS STATIC Y ENTONCES: TABLASIMBOLOS.GETTOKEN() ??	
	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos);
	
	
	
}
      