package Lexico;

import java.util.HashMap;
import java.util.Set;
import Lexico.Token;

public class TablaSimbolos {

	private HashMap<String, Token> tablaSimbolos = new HashMap<String, Token>();
	
	public void agregar(String variable,Token token) {
		if (!tablaSimbolos.containsKey(variable)) 
			tablaSimbolos.put(variable,token);
		else
			tablaSimbolos.get(variable).incrementarRef();
		
	}

	
	public boolean existeClave (String clave) {
		return tablaSimbolos.containsKey(clave);
	}
	
	public Token getClave(String clave) {
		return tablaSimbolos.get(clave);
	}
	
	public void eliminarClave(String clave) {
		tablaSimbolos.remove(clave);
	}
	
	public Set<String> getKeySet() {
		return tablaSimbolos.keySet();
	}
	
}
