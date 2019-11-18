package Lexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import Lexico.Token;

public class TablaSimbolos {

	public static String DD = "DD";
	public static String DW = "DW";
    public static String DB = "DB";

	private HashMap<String, Token> tablaSimbolos = new HashMap<String, Token>();
	private ArrayList<Token> listVarAux = new ArrayList<Token>();
	
	
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
	
	public void addVarAux(Token t) {
		listVarAux.add(t);
	}
	
	public String getDataAssembler() {
		String data = "";
		int i = 1;
		for (String clave : tablaSimbolos.keySet()) {
			
			Token t = tablaSimbolos.get(clave);
			if (t.getUso() ==null )
        		t.setUso("Cadena");

			if (t.getUso().equals("CTE")) {
				data = data + "_"+clave.replace('-', '@') + " " + DW + " " + clave + '\n';
			}
			else
				if (t.getUso().equals("CTF")) {
					
					data = data + "_" + clave.replace('.', '@').replace('-', '@').replace('+', '@')+ " " + DD + " " + clave + '\n';
				}
				else
					if (t.getUso().equals("Variable")) {
						if (t.getTipo().equals("int"))
							data = data + "_" + clave + " " + DW + " ?" + '\n';
						else
							data = data  + "_" + clave + " " + DD + " ?" + '\n';
					}
					else
						if (t.getTipo().equals("Cadena")) {
							data = data + "_" + clave.replace(' ', '@') + " " + DB + " \""  + clave + "\", 0" + '\n';
							t.setIdPrintCadena("_@print" + i);
							i++;
						}
						else
							if (t.getUso().equals("Variable Auxiliar")) {
								if (t.getTipo().equals("int"))
									data = data + "auxiliar"+t.getLexema() + " " + DW + " ?" + '\n';
								else
									data = data + "auxiliar"+t.getLexema() + " " + DD + " ?" + '\n';
							}
							else
								if (t.getUso().equals("Nombre de Coleccion")) {
									if (t.getTipo().equals("int")){
										data = data + "_" + clave + " " + DW ;
										for(int j = 0; j < t.getTamanioColeccion()-1; j++)
											 data = data + " ?," ; 
										data = data + " ?" + '\n';
									}
									else {
										data = data + "_" + clave + " " + DD ;
										for(int j = 0; j < t.getTamanioColeccion()-1; j++)
											 data = data + " ?," ; 
										data = data + " ?" + '\n';
									}
								}
		}
		

		return data;
	}
	
}
