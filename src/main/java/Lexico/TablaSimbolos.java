package Lexico;

import java.util.HashMap;

public class TablaSimbolos {

	public HashMap<String, HashMap<String,String>> tablaSimbolos = new HashMap<String, HashMap<String,String>>();
	
	public boolean agregar(String variable, String tipo) {
		HashMap<String, String> informacion = new HashMap<String, String>();
		informacion.put("tipo", tipo);
		
		if (!tablaSimbolos.containsKey(variable)) {
			tablaSimbolos.put(variable,informacion);
		}
		else{
			return false;
		}
		return true;
	}

	
	public boolean existeClave (String clave) {
		return tablaSimbolos.containsKey(clave);
	}
	
	
}
