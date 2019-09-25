package Lexico;

import java.util.HashMap;

public class TablaSimbolos {

	private HashMap<String, HashMap<String,String>> tablaSimbolos = new HashMap<String, HashMap<String,String>>();
	
	public void agregar(String variable, String tipo) {
		HashMap<String, String> informacion = new HashMap<String, String>();
		informacion.put("tipo", tipo);
		
		if (!tablaSimbolos.containsKey(variable)) {
			System.out.println("Se agrega " + variable + " a la tabla de simbolos");
			tablaSimbolos.put(variable,informacion);
		}
		else{
			System.out.println("La variable ya esta declarada"); // Hay que ver que se hace en este caso
		}
	}

	public void imprimir() {

		for (String key : tablaSimbolos.keySet()) {
			System.out.println(key + "--> " + tablaSimbolos.get(key.toString()));
		}
	}
	
}
