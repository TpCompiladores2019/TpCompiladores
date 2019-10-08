package Lexico;

import java.util.HashMap;

public class TablaSimbolos {

	private HashMap<String, HashMap<String,String>> tablaSimbolos = new HashMap<String, HashMap<String,String>>();
	
	public boolean agregar(String variable, String tipo) {
		HashMap<String, String> informacion = new HashMap<String, String>();
		informacion.put("tipo", tipo);
		
		if (!tablaSimbolos.containsKey(variable)) {
			System.out.println("Se agrega " + variable + " a la tabla de simbolos");
			tablaSimbolos.put(variable,informacion);
		}
		else{
			return false;
		}
		return true;
	}

	public void imprimir() {

		for (String key : tablaSimbolos.keySet()) {
			System.out.println(key + "--> " + tablaSimbolos.get(key.toString()));
		}
	}
	
	public boolean existeClave (String clave) {
		return tablaSimbolos.containsKey(clave);
	}
	
}
