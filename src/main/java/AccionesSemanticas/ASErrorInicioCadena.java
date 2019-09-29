package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Error;

public class ASErrorInicioCadena implements IAccionSemantica {


	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		Error nuevoError = new Error("La cadena no puede iniciar con ",AnalizadorLexico.nroLinea,"'"+caracter+"'","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);		 
		return -100;
	}

}
