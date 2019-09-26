package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASErrorDigitoFaltante implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		
		Error nuevoError = new Error("Se espera un digito luego de ",AnalizadorLexico.nroLinea,"'"+cadena.charAt(cadena.length()-1)+"'","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);
		AnalizadorLexico.indiceLectura--;
		
		cadena.delete(0, cadena.length());
		return -1;
	}

}