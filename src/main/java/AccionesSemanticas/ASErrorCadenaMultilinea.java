package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASErrorCadenaMultilinea implements IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		//System.out.println("cadena: "+ cadena); //TODO  falta
		cadena.delete(0, cadena.length());
		Error nuevoError = new Error("Cadena multilinea ",AnalizadorLexico.nroLinea,"","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);		
		return -1;
	}

}
