package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASChequearFloat implements IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		
		if ((Float.parseFloat(cadena.toString())> Float.MIN_NORMAL && Float.parseFloat(cadena.toString()) < Float.MAX_VALUE) || (Float.parseFloat(cadena.toString())< -Float.MIN_NORMAL && Float.parseFloat(cadena.toString()) > -Float.MAX_VALUE)
			|| (Float.parseFloat(cadena.toString()) == 0.0)) {
				tablaSimbolos.agregar(cadena.toString(),"float");
				return tablaTokens.getToken("CONSTANTE F");
		}
		else {
			Error nuevoError = new Error("El numero excede el rango de los Float",AnalizadorLexico.nroLinea," ","ERROR");
			AnalizadorLexico.listaErrores.add(nuevoError);
			return -1;
		}
	}

}
