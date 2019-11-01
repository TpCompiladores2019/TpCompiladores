package AccionesSemanticas;



import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Error;
import Lexico.Registro;
public class ASFinalEntero extends IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;
		Short nro;
		try {
			nro = Short.parseShort(cadena.toString());
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Entera: " + nro.toString());
		}catch (Exception e) {
			Error nuevoWarning = new Error("El numero " + cadena.toString() + " excede el mayor entero posible, se trunca a 32768. ",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaWarning.add(nuevoWarning);
			cadena.delete(0, cadena.length());
			cadena.append("32768");
		}	
		tablaSimbolos.agregar(cadena.toString(),new Registro("int"));
		return tablaTokens.getToken("CONSTANTE E");
	}
	
}