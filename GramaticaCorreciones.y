%{
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
	import Lexico.Registro;
%}

%token
	ID
	CTE_E
	CTE_F
	CADENA
	ASIGNACION
	
	/* Comparadores */
	MAYORIGUAL 
	MENORIGUAL 
	DESTINTO
	IGUAL
	 
	
	/* Palabras reservadas */
	IF
	ELSE
	END_IF
	PRINT
	INT
	BEGIN
	END
	DO
	UNTIL
	FLOAT
	FIRST
	LAST
	LENGHT
	
%start programa
%left '+' '-' 
%left '*' '/'

%%

	programa : conjunto_sentencias
			 ;

	conjunto_sentencias : sentencias_declarativas
						| sentencias_declarativas BEGIN sentencias_ejecutables END
						| BEGIN sentencias_ejecutables END
						| sentencias_ejecutables END {this.addError("Error, falta BEGIN.",((Token)$1.obj).getNroLinea());}
						| BEGIN sentencias_ejecutables  {this.addError("Error, falta END.",((Token)$2.obj).getNroLinea());}
						| sentencias_ejecutables {this.addError("Error, falta los delimitadores BEGIN y END.",((Token)$1.obj).getNroLinea());}
						| sentencias_declarativas sentencias_ejecutables END {this.addError("Error, falta BEGIN.",((Token)$1.obj).getNroLinea());}
						| sentencias_declarativas BEGIN sentencias_ejecutables {this.addError("Error, falta END.",((Token)$1.obj).getNroLinea());}
						| sentencias_declarativas sentencias_ejecutables {this.addError("Error, falta los delimitadores BEGIN  y END.",((Token)$1.obj).getNroLinea());}
						| BEGIN END {this.addError("Error, falta sentencias ejecutables.",((Token)$1.obj).getNroLinea());}
						;
	
	sentencias_declarativas : sentencias_declarativas sentencia_declarativa
							| sentencia_declarativa 
							
							;
							
	sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable
							| sentencia_ejecutable
							;
							
	sentencia_declarativa : tipo lista_variables ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia declarativa");
														actualizarTablaVariables(((Token)$1.obj),"Variable",((Token)$1.obj));}
						  | tipo ID '[' CTE_E ']' ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia declarativa");
														listaVariables.add(((Token)$2.obj));
														actualizarTablaVariables(((Token)$1.obj),"Nombre de Coleccion",((Token)$4.obj));}
						  | ID '[' CTE_E ']' ';' {this.addError("Error en declaracion, falta definir el tipo.",((Token)$2.obj).getNroLinea());}
						  | lista_variables ';' {this.addError("Error en declaracion, falta definir el tipo.",((Token)$1.obj).getNroLinea());}
						  | tipo ID '[' CTE_E ']'  {this.addError("Error en declaracion, falta ';'.",((Token)$2.obj).getNroLinea());}
						  | tipo lista_variables  {this.addError("Error en declaracion, falta ';'.",((Token)$2.obj).getNroLinea());}
						  | tipo ID '[' CTE_E ';' {this.addError("Error en declaracion, falta ']'.",((Token)$2.obj).getNroLinea());}
						  | tipo ID CTE_E ']' ';' {this.addError("Error en declaracion, falta '['.",((Token)$2.obj).getNroLinea());}
						  | tipo ';' {this.addError("Error en declaracion, falta variables.",((Token)$2.obj).getNroLinea());}
						  | tipo '[' CTE_E ']' ';' {this.addError("Error en declaracion, falta identIFicador.",((Token)$2.obj).getNroLinea());}
						  | tipo ID '[' error ']' ';' {this.addError("Error en declaracion, la coleccion solo permite constantes enteras.",((Token)$2.obj).getNroLinea());}
						  ;
							
	tipo : INT 
	 	 | FLOAT
		 ;
		 
	lista_variables : lista_variables ',' ID {listaVariables.add(((Token)$3.obj));}
					| ID {listaVariables.add(((Token)$1.obj));}
			//		| lista_variables ID {this.addError("Error, falta ','",((Token)$2.obj).getNroLinea());} MIRARRRRRRRRRRRRRRRRR
					;
					
	sentencia_ejecutable : seleccion_ejecutable ';'
						  | control_ejecutable 
						  | salida_ejecutable
						  | asign
					//	  | sentencia_declarativa {this.addError("Error, no se permite sentencia declarativa dentro de las ejecutables",((Token)$1.obj).getNroLinea());} 
						  | seleccion_ejecutable {this.addError("Error, falta ';'",((Token)$1.obj).getNroLinea());}
						  ;
	
	seleccion_ejecutable : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia IF-ELSE");}
						| IF '(' condicion ')' bloque_sentencias END_IF {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia IF");}
						| IF '(' condicion ')' bloque_sentencias bloque_sentencias END_IF {this.addError("Falta 'ELSE'.",((Token)$6.obj).getNroLinea());}
						| IF '(' condicion ')' bloque_sentencias ELSE END_IF {this.addError("Falta bloque de sentencias.",((Token)$6.obj).getNroLinea());}
						| IF '(' condicion ')' ELSE bloque_sentencias END_IF {this.addError("Falta bloque de sentencias.",((Token)$4.obj).getNroLinea());}
						| IF '(' condicion bloque_sentencias ELSE bloque_sentencias END_IF {this.addError( "Falta ')'.",((Token)$3.obj).getNroLinea());}
						| IF '(' ')' bloque_sentencias ELSE bloque_sentencias END_IF {this.addError("Falta condicion.",((Token)$2.obj).getNroLinea());}
						| IF condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {this.addError("Falta ')'.",((Token)$1.obj).getNroLinea());}
						| '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {this.addError("Falta 'IF'.",((Token)$1.obj).getNroLinea());}
						| '(' condicion ')' bloque_sentencias END_IF {this.addError("Falta 'IF'.",((Token)$1.obj).getNroLinea());}
						| IF condicion ')' bloque_sentencias END_IF {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
						| IF '(' ')' bloque_sentencias END_IF {this.addError("Falta condicion.",((Token)$2.obj).getNroLinea());}
						| IF '(' condicion bloque_sentencias END_IF {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
						| IF '(' condicion ')' END_IF {this.addError("Falta bloque de sentencias.",((Token)$4.obj).getNroLinea());}
					//	| IF '(' condicion ')' bloque_sentencias {this.addError("Falta 'end_if.",((Token)$5.obj).getNroLinea());}
						;
						
	condicion : expresion_aritmetica operador expresion_aritmetica {if (!sonCompatibles(tablaSimbolos.getToken(((Token)$1.obj).getLexema()).getTipo()))	
																this.addError("Error tipos incompatibles en comparacion.",((Token)$3.obj).getNroLinea());
															listaCompatibilidad.clear();}
			  | operador expresion_aritmetica {this.addError("Falta expresion del lado izquierdo.",((Token)$1.obj).getNroLinea());}
			  | expresion_aritmetica operador error{this.addError("Falta expresion del lado derecho.",((Token)$2.obj).getNroLinea());}
			  ;
			 
	expresion_aritmetica : expresion_aritmetica '+' termino 
						 | expresion_aritmetica '-' termino 
						 | termino 
						 ;
						 
	termino : termino '*' factor 
			| termino '/' factor 
			| factor {listaCompatibilidad.add((Token)$1.obj).getLexema().getTipo();)}
			;
			
	factor : variable {}
	       | CTE_E {actualizarTablaPositivo(((Token)$1.obj).getLexema())}
		   | CTE_F 
		   | '-' CTE_E {actualizarTablaNegativo(((Token)$2.obj).getLexema());}
		   | '-' CTE_F {actualizarTablaNegativoFloat(((Token)$2.obj).getLexema());}
		   | invocacion_metodo
		   ;
	
	variable : ID '[' ID ']' {	if (!estaDeclarada(((Token)$1.obj)))
									this.addError("Error coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",((Token)$1.obj).getNroLinea());
								else
									if (tablaSimbolos.getToken(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
										this.addError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",((Token)$1.obj).getNroLinea());
								if (!estaDeclarada(((Token)$3.obj)))
									this.addError("Error variable '"+((Token)$3.obj).getLexema() + "' no declarada.",((Token)$3.obj).getNroLinea());
								else
									if (!tablaSimbolos.getToken(((Token)$3.obj).getLexema()).getTipo().equals("int"))
										this.addError("Error El tipo del subindice no es entero",((Token)$3.obj).getNroLinea());
								else
									if (tablaSimbolos.getToken(((Token)$3.obj).getLexema()).getUso().equals("Nombre de Coleccion"))
										this.addError("Error '"+((Token)$3.obj).getLexema() + "' es una coleccion.",((Token)$3.obj).getNroLinea());}
										
			 | ID '[' CTE_E ']' {	if (!estaDeclarada(((Token)$1.obj)))
										this.addError("Error coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",((Token)$1.obj).getNroLinea());
									else
										if (tablaSimbolos.getToken(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
											this.addError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",((Token)$1.obj).getNroLinea());}
			 | ID {	if (!estaDeclarada(((Token)$1.obj)))
						this.addError("Error variable '"+((Token)$1.obj).getLexema() + "' no declarada.",((Token)$1.obj).getNroLinea());}
			 ;
	
	operador : '<'
			 | MENORIGUAL
			 | '>'
			 | MAYORIGUAL
			 | DESTINTO
			 | IGUAL
			 ;
			
	bloque_sentencias : sentencia_ejecutable 
			| BEGIN sentencias_ejecutables END
			| BEGIN END
		//	| sentencias_ejecutables END {this.addError("Error, falta BEGIN dentro del bloque de sentencias.",((Token)$1.obj).getNroLinea());}
		//	| BEGIN sentencias_ejecutables {this.addError("Error, falta END dentro del bloque de sentencias.",((Token)$1.obj).getNroLinea());}
		//	| sentencias_ejecutables {this.addError("Error, falta BEGIN y END dentro del bloque de sentencias.",((Token)$1.obj).getNroLinea());}
			;
	
	control_ejecutable : DO bloque_sentencias UNTIL '(' condicion ')' ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia until");}
					   | DO UNTIL '(' condicion ')' ';' {this.addError("Falta bloque de sentencias.",((Token)$1.obj).getNroLinea());}
					   | DO bloque_sentencias '(' condicion ')' ';' {this.addError("Falta 'until'.",((Token)$2.obj).getNroLinea());}
					   | DO bloque_sentencias UNTIL condicion ')' ';' {this.addError("Falta '('.",((Token)$3.obj).getNroLinea());}
					   | DO bloque_sentencias UNTIL '(' ')' ';' {this.addError("Falta condicion.",((Token)$4.obj).getNroLinea());}
					   | DO bloque_sentencias UNTIL '(' condicion ';' {this.addError("Falta ')'.",((Token)$5.obj).getNroLinea());}
					   | DO bloque_sentencias UNTIL '(' condicion ')' {this.addError("Falta ';'.",((Token)$6.obj).getNroLinea());}
					//   | bloque_sentencias UNTIL '(' condicion ')' {this.addError("Falta ';'.",((Token)$1.obj).getNroLinea());}
					   ;
	
	salida_ejecutable : PRINT '(' CADENA ')' ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia PRINT");}
					  | '(' CADENA ')' ';' {this.addError("Falta 'PRINT'.",((Token)$1.obj).getNroLinea());}
					  | PRINT CADENA ')' ';' {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
					  | PRINT '(' error ')' ';' {this.addError("Solo se puede definir una cadena.",((Token)$2.obj).getNroLinea());}
					  | PRINT '(' CADENA ';' {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
					  | PRINT '(' CADENA ')' {this.addError("Falta ';'.",((Token)$4.obj).getNroLinea());}
					  ;

	asign : variable ASIGNACION expresion_aritmetica ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Asignacion");
															
															if (!sonCompatibles(tablaSimbolos.getToken(((Token)$1.obj) .getLexema()).getTipo()))	
																this.addError("Error tipos incompatibles.",((Token)$3.obj).getNroLinea());
																
															listaCompatibilidad.clear();	
														}
		  | ASIGNACION expresion_aritmetica ';' {this.addError("Falta variable.",((Token)$1.obj).getNroLinea());}
		  | variable expresion_aritmetica ';' {this.addError("Falta ':='.",((Token)$1.obj).getNroLinea());}
		  | variable ASIGNACION ';' {this.addError("Falta expresion.",((Token)$2.obj).getNroLinea());}
		  | variable ASIGNACION expresion_aritmetica {this.addError("Falta ';'.",((Token)$3.obj).getNroLinea());}
		  ;
		  
	metodo : FIRST
			| LAST
			| LENGHT
			;
			
	invocacion_metodo : ID '.' metodo '(' ')' 
					  | ID '.' metodo ')' {this.addError("Falta '(.'",((Token)$3.obj).getNroLinea());}
					  | ID '.' '(' ')' {this.addError("Falta metodo.",((Token)$2.obj).getNroLinea());}
					  ;

%%




private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

private ArrayList<Token> listaVariables = new ArrayList<Token>();
private ArrayList<String> listaCompatibilidad = new ArrayList<String>();


public Parser(AnalizadorLexico lexico, TablaSimbolos tablaSimbolos) {
	this.lexico = lexico;
	this.tablaSimbolos = tablaSimbolos;
}

public int yylex() {
	int idToken = lexico.yylex();
	yylval = new ParserVal(lexico.getToken());
	return idToken;
	}

public void addError(String descripcion, int nroLinea){
	listaErrores.add(new Error(descripcion,nroLinea,"","Error"));
}

private void yyerror(String string) {
	// TODO Auto-generated method stub
	
}

public int yyparser() {
	return yyparse();
}

public String informacionEstructuras() {
	String informacion="";
	if (!listaCorrectas.isEmpty()){
		informacion = "\nEstructuras Sintacticas: \n";
		for (String info : listaCorrectas) {
			informacion+=info + "\n";
		}
	}
	return informacion;
}

public String informacionError() {
	String informacion = "";
	if (!listaErrores.isEmpty()){
		informacion = "\nErrores Sintacticos: \n";
		for (Error e : listaErrores) {
			informacion += e +"\n";
		}
	}
	return informacion;
}



public void actualizarTablaPositivo(String lexema) {
		if(lexema == "32768"){
			if (tablaSimbolos.tablaSimbolos.get(lexema).getCantRef()==1){
				tablaSimbolos.tablaSimbolos.remove(lexema);
			}
			else
				tablaSimbolos.tablaSimbolos.get(lexema).decrementarRef();
		
		if(tablaSimbolos.tablaSimbolos.containsKey("32767")) {
			tablaSimbolos.tablaSimbolos.get("32767").incrementarRef();
		}
		else
			tablaSimbolos.agregar("32767", new Registro("int"));
	}
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.tablaSimbolos.containsKey(lexema)) {
		if (tablaSimbolos.tablaSimbolos.get(lexema).getCantRef()==1){
			tablaSimbolos.tablaSimbolos.remove(lexema);
		}
		else
			tablaSimbolos.tablaSimbolos.get(lexema).decrementarRef();
	}
	if(tablaSimbolos.tablaSimbolos.containsKey(lexemaNuevo)) {
		tablaSimbolos.tablaSimbolos.get(lexemaNuevo).incrementarRef();
	}
	else
		tablaSimbolos.agregar(lexemaNuevo, new Registro("int"));
}


public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if ((Integer.parseInt(lexemaNuevo))>= Short.MIN_VALUE){
		if(tablaSimbolos.existeClave(lexema)) {
			if (tablaSimbolos.getToken(lexema).getCantRef()==1)
				tablaSimbolos.eliminarClave(lexema);
			else
				tablaSimbolos.getToken(lexema).decrementarRef();
		}
		if(tablaSimbolos.existeClave(lexemaNuevo)) {
			tablaSimbolos.getToken(lexemaNuevo).incrementarRef();
		}
		else
			tablaSimbolos.agregar(lexemaNuevo, new Registro("int"));
	}
}

public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if ((Float.parseFloat(lexemaNuevo)< -Float.MIN_NORMAL && Float.parseFloat(lexemaNuevo) > -Float.MAX_VALUE)) {
		if (tablaSimbolos.existeClave(lexema))
			if(tablaSimbolos.tablaSimbolos.get(lexema).getCantRef() == 1) 
				tablaSimbolos.tablaSimbolos.remove(lexema);
			else
				tablaSimbolos.tablaSimbolos.get(lexema).decrementarRef();
		if(tablaSimbolos.tablaSimbolos.containsKey(lexemaNuevo)) 
			tablaSimbolos.tablaSimbolos.get(lexemaNuevo).incrementarRef();
		else
			tablaSimbolos.agregar(lexemaNuevo, new Registro("float"));
	}
}

public void actualizarTablaVariables(Token tipo,String uso,Token cteE) {
	String lexema = "";
	Token t = null;
	
	while(!listaVariables.isEmpty()) {
		t = listaVariables.get(0);
		listaVariables.remove(0);
		lexema = t.getLexema();
		if(!tablaSimbolos.getToken(lexema).getDeclarada()) {
			tablaSimbolos.getToken(lexema).setTipo(tipo.getLexema());
			tablaSimbolos.getToken(lexema).setDeclarada(true);
			tablaSimbolos.getToken(lexema).setUso(uso);
			if(tablaSimbolos.getToken(lexema).getUso().equals("Nombre de Coleccion")) {
				tablaSimbolos.getToken(lexema).setTamanioColeccion(Integer.parseInt(cteE.getLexema()));
			}
		}
		else
			this.addError("Error en declaracion, variable o coleccion '" + lexema + "' redeclarada",t.getNroLinea());
	}
}

public boolean estaDeclarada(Token t){
	if (tablaSimbolos.getToken(t.getLexema()).getDeclarada())
		return true;
	else
		return false;
}

public boolean esCompatible(Token t1, Token t2){
	if (tablaSimbolos.getToken(t1.getLexema()).getTipo().equals(tablaSimbolos.getToken(t2.getLexema()).getTipo()))
		return true;
	return false;
}

public boolean sonCompatibles(String tipoAComparar){
	for (String tipo : listaCompatibilidad){
		if (!tipo.equals(tipoAComparar))
			return false;
	}
	return true;
}
