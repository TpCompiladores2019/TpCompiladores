package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public abstract class IAccionSemantica {
	

	public abstract int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos);
	
	
	
}
      