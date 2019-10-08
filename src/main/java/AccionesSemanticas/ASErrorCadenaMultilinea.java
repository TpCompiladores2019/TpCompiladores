package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASErrorCadenaMultilinea implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		cadena.delete(0, cadena.length());
		Error nuevoError = new Error("",AnalizadorLexico.nroLinea,"","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);	
		AnalizadorLexico.nroLinea++;
		return -1;
	}

}
