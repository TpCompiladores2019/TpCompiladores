package Lexico;

import java.util.Hashtable;

public class TablaTokens {

	private Hashtable<String,Integer> tokens = new Hashtable<String,Integer>();
	
	public final static int YYERRCODE=256;
	public final static int ID=257;
	public final static int CTE_E=258;
	public final static int CTE_F =259;
	public final static int CADENA=260;
	public final static int ASIGNACION=261;
	public final static int MAYORIGUAL=262;
	public final static int MENORIGUAL=263;
	public final static int DISTINTO=264;
	public final static int IGUAL=265;
	public final static int IF=266;
	public final static int ELSE=267;
	public final static int END_IF=268;
	public final static int PRINT=269;
	public final static int INT=270;
	public final static int BEGIN=271;
	public final static int END=272;
	public final static int DO=273;
	public final static int UNTIL=274;
	public final static int FLOAT=275;
	
	public void CompletarTabla() {
		//Palabras Reservadas
		tokens.put("if", IF);
		tokens.put("else", ELSE);
		tokens.put("end_if", END_IF);
		tokens.put("print",PRINT);
		tokens.put("int", INT);
		tokens.put("begin", BEGIN);
		tokens.put("end", END);
		tokens.put("do", DO);
		tokens.put("until", UNTIL);
		tokens.put("float", FLOAT);
		//Identificador
		tokens.put("IDENTIFICADOR",ID);
		//Constantes
		tokens.put("CONSTANTE E",CTE_E);
		tokens.put("CONSTANTE F",CTE_F);
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

	public boolean contieneClave(String clave) {
		
		return tokens.containsKey(clave);
	}
	
	
}
