package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASConsumirComentario extends IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		AnalizadorLexico.comentarioAbierto = true;
		cadena.delete(0,cadena.length());
		return -1;
	}
	
}
