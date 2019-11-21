package Lexico;

import java.util.HashMap;

public class TablaTokens {

	private HashMap<String,Integer> tokens = new HashMap<String,Integer>();
	
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
	public final static int FIRST=276;
	public final static int LAST = 277;
	public final static int LENGTH = 278;
	
	//keys;
	
	public final static String IF_KEY = "if";
	public final static String ELSE_KEY = "else";
	public final static String END_IF_KEY = "end_if";
	public final static String PRINT_KEY = "print";
	public final static String INT_KEY = "int";
	public final static String BEGIN_KEY = "begin";
	public final static String END_KEY = "end";
	public final static String DO_KEY = "do";
	public final static String UNTIL_KEY = "until";
	public final static String FLOAT_KEY = "float";
	public final static String FIRST_KEY = "first";
	public final static String LAST_KEY = "last";
	public final static String LENGTH_KEY = "length";
	public final static String ID_KEY = "IDENTIFICADOR";
	public final static String CTE_KEY = "CONSTANTE E";
	public final static String CTF_KEY = "CONSTANTE F";
	public final static String CADENA_KEY = "CADENA";
	public final static String ASIGNACION_KEY = ":=";
	public final static String MAS = "+";
	public final static String MENOS = "-";
	public final static String BARRA = "/";
	public final static String ASTERISCO = "*";
	public final static String PARENTESIS_A = "(";
	public final static String PARENTESIS_C = ")";
	public final static String COMA = ",";
	public final static String PUNTOCOMA= ";";
	public final static String CORCHETE_A = "[";
	public final static String CORCHETE_C = "]";
	public final static String PUNTO = ".";
	public final static String IGUAL_KEY = "==";
	public final static String DISTINTO_KEY= "<>";
	public final static String MAYORIGUAL_KEY = ">=";
	public final static String MENORIGUAL_KEY = "<=";
	public final static String MAYOR = ">";
	public final static String MENOR = "<";
	public final static String GUIONBAJO = "_";
	public final static String ESPACIO = " ";

	
	public void completarTabla() {
		//Palabras Reservadas
		tokens.put(IF_KEY, IF);
		tokens.put(ELSE_KEY, ELSE);
		tokens.put(END_IF_KEY, END_IF);
		tokens.put(PRINT_KEY,PRINT);
		tokens.put(INT_KEY, INT);
		tokens.put(BEGIN_KEY, BEGIN);
		tokens.put(END_KEY, END);
		tokens.put(DO_KEY, DO);
		tokens.put(UNTIL_KEY, UNTIL);
		tokens.put(FLOAT_KEY, FLOAT);
		tokens.put(FIRST_KEY,FIRST);
		tokens.put(LAST_KEY,LAST);
		tokens.put(LENGTH_KEY, LENGTH);
		//Identificador
		tokens.put(ID_KEY,ID);
		//Constantes
		tokens.put(CTE_KEY,CTE_E);
		tokens.put(CTF_KEY,CTE_F);
		//Cadena
		tokens.put(CADENA_KEY,CADENA);
		//Asignacion
		tokens.put(ASIGNACION_KEY,ASIGNACION);
		//Operadores
		tokens.put(MAS,(int)'+');
		tokens.put(MENOS,(int)'-');
		tokens.put(ASTERISCO,(int)'*');
		tokens.put(BARRA,(int)'/');
		//Simbolos
		tokens.put(PARENTESIS_A,(int)'(');	
		tokens.put(PARENTESIS_C,(int)')');		
		tokens.put(COMA,(int)',');	
		tokens.put(PUNTOCOMA,(int)';');	
		tokens.put(CORCHETE_A,(int)'[');		
		tokens.put(CORCHETE_C,(int)']');
		tokens.put(PUNTO,(int)'.');
		//Comparadores
		tokens.put(IGUAL_KEY,IGUAL);
		tokens.put(DISTINTO_KEY,DISTINTO);
		tokens.put(MAYORIGUAL_KEY,MAYORIGUAL);
		tokens.put(MENORIGUAL_KEY,MENORIGUAL);
		tokens.put(MAYOR,(int)'>');
		tokens.put(MENOR,(int)'<');
		
		tokens.put(GUIONBAJO,(int)'_');
		tokens.put(ESPACIO,(int)' ');
	}
	
	public int getToken(String clave) {
		return tokens.get(clave);
	}

	public boolean contieneClave(String clave) {
		return tokens.containsKey(clave);
	}
	
	
}
