package com.g3.TpCompiladores;

import java.util.Hashtable;

public class TablaTokens {

	private Hashtable<String,Integer> tokens = new Hashtable<String,Integer>();
	
	public final static int ID=260;
	public final static int CTE=261;
	public final static int CADENA=262;
	public final static int ASIGNACION=263;
	public final static int MAYORIGUAL=264;
	public final static int MENORIGUAL=265;
	public final static int IGUAL=266;
	public final static int DISTINTO=267;
	public final static int IF=268;
	public final static int ELSE=269;
	public final static int END_IF=270;
	public final static int PRINT=271;
	public final static int INT=272;
	public final static int BEGIN=273;
	public final static int END=274;
	public final static int DO=275;
	public final static int UNTIL=276;
	public final static int FLOAT=277;
	
	public void CompletarTabla() {
		//Palabras Reservadas
		tokens.put("if", (int)IF);
		tokens.put("else", (int)ELSE);
		tokens.put("end_if", (int)END_IF);
		tokens.put("print", (int)PRINT);
		tokens.put("int", (int)INT);
		tokens.put("begin", (int)BEGIN);
		tokens.put("end", (int)END);
		tokens.put("do", (int)DO);
		tokens.put("until", (int)UNTIL);
		tokens.put("float", (int)FLOAT);
		//Identificador
		tokens.put("IDENTIFICADOR",(int)ID);
		//Constantes
		tokens.put("CONSTANTE",(int)CTE);
		//Cadena
		tokens.put("CADENA",(int)CADENA);
		//Asignacion
		tokens.put(":=",(int)ASIGNACION);
		//Operadores
		tokens.put("+",(int)'+');
		tokens.put("-",(int)'-');
		tokens.put("*",(int)'*');
		tokens.put("/",(int)'/');
		//Simbolos
		tokens.put("(",(int)'(');	
		tokens.put(")",(int)')');		
		tokens.put(",",(int)',');	
		tokens.put(";",(int)';');	
		tokens.put("[",(int)'[');		
		tokens.put("]",(int)']');
		// %?? _??
		//Comparadores
		tokens.put("==",(int)IGUAL);
		tokens.put("<>",(int)DISTINTO);
		tokens.put(">=",(int)MAYORIGUAL);
		tokens.put("<=",(int)MENORIGUAL);
		tokens.put(">",(int)'>');
		tokens.put("<",(int)'<');
		
		tokens.put("_",(int)'_');
		tokens.put(" ",(int)' ');
	}
	
	public int getToken(String clave) {
		return tokens.get(clave);
	}
	
}
