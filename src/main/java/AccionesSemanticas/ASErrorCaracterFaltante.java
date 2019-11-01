package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public class ASErrorCaracterFaltante extends IAccionSemantica{

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		
		String simboloEsperado = new String();
		char ultimoChar = cadena.charAt(cadena.length()-1);
		if ( ultimoChar == '.')
			simboloEsperado = "un digito o un identificador";		
		else if ( (ultimoChar == 'E') || (ultimoChar == 'e') || (ultimoChar == '+') || (ultimoChar == '-'))
			simboloEsperado="un digito";
		else
			simboloEsperado = "=";
		
	
		
		Error nuevoError = new Error("Se espera " + simboloEsperado+ " luego de ",AnalizadorLexico.nroLinea,"'"+ultimoChar+"'","ERROR");
		AnalizadorLexico.listaErrores.add(nuevoError);
		AnalizadorLexico.indiceLectura--;
		
		cadena.delete(0, cadena.length());
		return -1;
	}

}