package AccionesSemanticas;

import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public interface IAccionSemantica {
	
	// TODO LA TABLA DE SIMBOLOS ES POR PARAMETROS O LA HACEMOS STATIC Y ENTONCES: TABLASIMBOLOS.GETTOKEN() ??
	
	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos);
	
	// HAY QUE HACER UNA ACCION SEMANTICA PARA LA \N QUE AUMENTE EL NROD E LINEA 
	
	
}
      