package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASErrorCadenaMultilinea extends IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.delete(0, cadena.length());
		Error nuevoError = new Error("La cadena no permite salto de linea",AnalizadorLexico.nroLinea,"","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);	
		AnalizadorLexico.nroLinea++;
		return -1;
	}

}
