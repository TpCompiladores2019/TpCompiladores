package Lexico;

import java.util.HashMap;
import java.util.Set;
import Lexico.Registro;

public class TablaSimbolos {

	private HashMap<String, Registro> tablaSimbolos = new HashMap<String, Registro>();
	
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
	
	public Registro getToken(String clave) {
		return tablaSimbolos.get(clave);
	}
	
	public void eliminarClave(String clave) {
		tablaSimbolos.remove(clave);
	}
	
	public Set<String> getKeySet() {
		return tablaSimbolos.keySet();
	}
	
}
