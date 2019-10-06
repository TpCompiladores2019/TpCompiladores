package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Error;

public class ASError implements IAccionSemantica {


	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		Error nuevoError = new Error("Token ",AnalizadorLexico.nroLinea,"'"+caracter+"' invalido","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);
		if (cadena.length() != 0) {// es porque la cadena quiere arrancar con este caracter
			IAccionSemantica ASaux = new ASFinalID();
			ASaux.ejecutar(caracter, cadena, tablaTokens, tablaSimbolos);
			AnalizadorLexico.indiceLectura++;
		}
		return -1;
	}

}
