package Lexico;

import java.util.HashMap;

public class TablaSimbolos {

	public HashMap<String, Registro> tablaSimbolos = new HashMap<String, Registro>();
	
	public boolean agregar(String variable,Registro registro) {
		if (!tablaSimbolos.containsKey(variable)) {
			tablaSimbolos.put(variable,registro);
		}
		else{
			tablaSimbolos.get(variable).incrementarRef();
			return false;
		}
		return true;
	}

	
	public boolean existeClave (String clave) {
		return tablaSimbolos.containsKey(clave);
	}
	
	
	
}
