package AccionesSemanticas;



import Lexico.AnalizadorLexico;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Error;
import Lexico.Registro;
public class ASFinalEntero extends IAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		AnalizadorLexico.indiceLectura--;

		if(Integer.parseInt(cadena.toString())>= 32768) {		
			Error nuevoError = new Error("El numero " + cadena.toString() + " excede el mayor entero posible, por lo que se procede a asignar 32768. ",AnalizadorLexico.nroLinea," ","WARNING");
			AnalizadorLexico.listaWarning.add(nuevoError);
		    cadena.delete(0, cadena.length());
		    cadena.append("32768");
		}
		tablaSimbolos.agregar(cadena.toString(),new Registro("int"));
		AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Entera: " + cadena.toString());
		
		return tablaTokens.getToken("CONSTANTE E");
	}
	
}