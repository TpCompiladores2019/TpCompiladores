%{
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
	import CodigoIntermedio.AnalizadorTercetos;
	import CodigoIntermedio.TercetoIndividual;
	import CodigoIntermedio.TercetoS;
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
			//		| CTE_E {this.addError("Error, no se puede declarar constante enteras",((Token)$1.obj).getNroLinea());}
			//		| CTE_F {this.addError("Error, no se puede declarar constante flotantes",((Token)$1.obj).getNroLinea());}
			//		| lista_variables ',' ID {listaVariables.add(((Token)$3.obj));}
			//		| lista_variables ID {this.addError("Error, falta ','",((Token)$2.obj).getNroLinea());} MIRARRRRRRRRRRRRRRRRR
					;
					
	sentencia_ejecutable : seleccion_ejecutable ';'
						  | control_ejecutable ';'
						  | salida_ejecutable ';'
						  | asign 
						  | seleccion_ejecutable {this.addError("Error, falta ';'",((Token)$1.obj).getNroLinea());}
						  | control_ejecutable {this.addError("Error, falta ';'",((Token)$1.obj).getNroLinea());}
						  | salida_ejecutable {this.addError("Error, falta ';'",((Token)$1.obj).getNroLinea());}
						  ;
	
	
	condicion_if : condicion { TercetoS terceto = new TercetoS ( new TercetoIndividual( (new Token( "BF") ) ), new TercetoIndividual(new Token("@" + 			analizadorTerceto.getNumeroTerceto()) ), null, analizadorTerceto.getProximoTerceto());
							analizadorTerceto.addTerceto(terceto);
							analizadorTerceto.apilar();
											}
					;
	
	seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias ELSE {TercetoS terceto = new TercetoS ( new TercetoIndividual( new Token("BI")  ), null, null, analizadorTerceto.getProximoTerceto() );
                                                             	analizadorTerceto.addTerceto (terceto);
                                                             	analizadorTerceto.desapilar();
                                                             	analizadorTerceto.apilar();}

						bloque_sentencias END_IF {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia IF-ELSE");
												analizadorTerceto.desapilar();}
						
						| IF '(' condicion_if ')' bloque_sentencias END_IF {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia IF");
																			analizadorTerceto.desapilar();}
						| IF '(' condicion_if ')' bloque_sentencias bloque_sentencias END_IF {this.addError("Falta 'ELSE'.",((Token)$6.obj).getNroLinea());}
						| IF '(' condicion_if ')' bloque_sentencias ELSE END_IF {this.addError("Falta bloque de sentencias.",((Token)$6.obj).getNroLinea());}
						| IF '(' condicion_if ')' ELSE bloque_sentencias END_IF {this.addError("Falta bloque de sentencias.",((Token)$4.obj).getNroLinea());}
						| IF '(' condicion_if bloque_sentencias ELSE bloque_sentencias END_IF {this.addError( "Falta ')'.",((Token)$3.obj).getNroLinea());}
						| IF '(' ')' bloque_sentencias ELSE bloque_sentencias END_IF {this.addError("Falta condicion.",((Token)$2.obj).getNroLinea());}
						| IF condicion_if ')' bloque_sentencias ELSE bloque_sentencias END_IF {this.addError("Falta ')'.",((Token)$1.obj).getNroLinea());}
						| '(' condicion_if ')' bloque_sentencias ELSE bloque_sentencias END_IF {this.addError("Falta 'IF'.",((Token)$1.obj).getNroLinea());}
						| '(' condicion_if ')' bloque_sentencias END_IF {this.addError("Falta 'IF'.",((Token)$1.obj).getNroLinea());}
						| IF condicion_if ')' bloque_sentencias END_IF {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
						| IF '(' ')' bloque_sentencias END_IF {this.addError("Falta condicion.",((Token)$2.obj).getNroLinea());}
						| IF '(' condicion_if bloque_sentencias END_IF {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
						| IF '(' condicion_if ')' END_IF {this.addError("Falta bloque de sentencias.",((Token)$4.obj).getNroLinea());}
					//	| IF '(' condicion_if ')' bloque_sentencias ELSE bloque_sentencias {this.addError("Falta 'end_if.",((Token)$7.obj).getNroLinea());}
					//	| IF '(' condicion_if ')' bloque_sentencias {this.addError("Falta 'end_if.",((Token)$5.obj).getNroLinea());}
						;
						
	condicion : expresion_aritmetica operador expresion_aritmetica {
															if( esCompatible(((Token)$1.obj),((Token)$3.obj))){
																TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(((Token)$2.obj)), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
																analizadorTerceto.addTerceto(tercetoAsignacion);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);									
															}
			  | operador expresion_aritmetica {this.addError("Falta expresion del lado izquierdo.",((Token)$1.obj).getNroLinea());}
			  | expresion_aritmetica operador error{this.addError("Falta expresion del lado derecho.",((Token)$2.obj).getNroLinea());}
			  ;
			 
	expresion_aritmetica : expresion_aritmetica '+' termino { if( esCompatible(((Token)$1.obj),((Token)$3.obj))){
																TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(((Token)$2.obj)), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
																analizadorTerceto.addTerceto(tercetoAsignacion);
																Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																nuevo.setTipo(((Token)$1.obj).getTipo());
																$$ = new ParserVal(nuevo);
																}
																else
																	analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															}
						 | expresion_aritmetica '-' termino {
															if( esCompatible(((Token)$1.obj),((Token)$3.obj))){
																TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(((Token)$2.obj)), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
																analizadorTerceto.addTerceto(tercetoAsignacion);
					
																Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																nuevo.setTipo(((Token)$1.obj).getTipo());
																$$ = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															}
						 | termino 
						 ;
						 
			termino : termino '*' factor {	if( esCompatible(((Token)$1.obj),((Token)$3.obj))){
															TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(((Token)$2.obj)), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
															analizadorTerceto.addTerceto(tercetoAsignacion);
															Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
															nuevo.setTipo(((Token)$1.obj).getTipo());
															$$ = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															
															}
			| termino '/' factor { if( esCompatible(((Token)$1.obj),((Token)$3.obj))){
															TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(((Token)$2.obj)), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
															analizadorTerceto.addTerceto(tercetoAsignacion);
															Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
															nuevo.setTipo(((Token)$1.obj).getTipo());
															$$ = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															}												
															

			| factor {}
		 
			;
			
	factor : variable {}
	       | CTE_E {actualizarTablaPositivo(((Token)$1.obj).getLexema());
					((Token)$1.obj).setTipo("int");
					$$ = $1;
					}
		   | CTE_F {((Token)$1.obj).setTipo("float");
					$$ = $1;
					}
		   | '-' CTE_E {actualizarTablaNegativo(((Token)$2.obj).getLexema());
						Token tNegativo = new Token("-"+((Token)$2.obj).getLexema());
						tNegativo.setTipo("int");
						$$ = new ParserVal(tNegativo);}
		   | '-' CTE_F {actualizarTablaNegativoFloat(((Token)$2.obj).getLexema());
						Token tNegativo = new Token("-"+((Token)$2.obj).getLexema());
						tNegativo.setTipo("float");
						$$ = new ParserVal(tNegativo);}
		   | invocacion_metodo
		   | '-' variable{Token tNegativo = new Token("-"+((Token)$2.obj).getLexema());
							tNegativo.setTipo(((Token)$2.obj).getTipo());
							$$ = new ParserVal(tNegativo);}
		   | '-' invocacion_metodo
		   
		   ;
	
	variable : ID '[' ID ']' {	if (!estaDeclarada(((Token)$1.obj))){
									analizadorTerceto.agregarError("Error coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",lexico.nroLinea);
									tablaSimbolos.eliminarClave(((Token)$1.obj).getLexema());
								}
								else
									if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
										analizadorTerceto.agregarError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",lexico.nroLinea);
									
								if (!estaDeclarada(((Token)$3.obj))){
									analizadorTerceto.agregarError("Error variable '"+((Token)$3.obj).getLexema() + "' no declarada.",lexico.nroLinea);
									this.addError("Error variable '"+((Token)$3.obj).getLexema() + "' no declarada.",((Token)$3.obj).getNroLinea());
									tablaSimbolos.eliminarClave(((Token)$3.obj).getLexema());
								}
								else
									if (!tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getTipo().equals("int"))
										analizadorTerceto.agregarError("El tipo del subindice no es entero",lexico.nroLinea);
								else
									if (tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getUso().equals("Nombre de Coleccion"))
										
										analizadorTerceto.agregarError("Error '"+((Token)$3.obj).getLexema() + "' es una coleccion.",lexico.nroLinea);
										
									else{
										TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(new Token("OFFSET")), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
										analizadorTerceto.addTerceto(tercetoAsignacion);
										Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
										nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
										$$ = new ParserVal(nuevo);
									}
								}
									
			 | ID '[' CTE_E ']' {	if (!estaDeclarada(((Token)$1.obj))){
										analizadorTerceto.agregarError("Coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",lexico.nroLinea);
										tablaSimbolos.eliminarClave(((Token)$1.obj).getLexema());
									}
									else{
										if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
											analizadorTerceto.agregarError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",lexico.nroLinea);
										else{
											TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(new Token("OFFSET")), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
											analizadorTerceto.addTerceto(tercetoAsignacion);
											Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
											nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
											$$ = new ParserVal(nuevo);
										}
									}
								}
			 | ID {	
					Token t = ((Token)$1.obj);
					if (!estaDeclarada(t)){
						analizadorTerceto.agregarError("Error variable '"+t.getLexema() + "' no declarada.",lexico.nroLinea);
						tablaSimbolos.eliminarClave(t.getLexema());}
					else{
						t.setTipo(tablaSimbolos.getClave(t.getLexema()).getTipo());
						$$ = new ParserVal(t);
					}
				}
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
		//	| sentencias_ejecutables END {this.addError("Error, falta BEGIN dentro del bloque de sentencias.",((Token)$1.obj).getNroLinea());}  //GENERA SHIFT REDUCE
		//	| BEGIN sentencias_ejecutables {this.addError("Error, falta END dentro del bloque de sentencias.",((Token)$1.obj).getNroLinea());}  //GENERA SHIFT REDUCE
		//	| sentencias_ejecutables {this.addError("Error, falta BEGIN y END dentro del bloque de sentencias.",((Token)$1.obj).getNroLinea());} GENERA SHIFT REDUCE Y REDUCE REDUCE
			;
			
	
	
	inicio_do : DO {analizadorTerceto.apilar();}
	
	control_ejecutable : inicio_do bloque_sentencias UNTIL '(' condicion ')'  {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia until");
	TercetoS tercetoDO = new TercetoS(new TercetoIndividual( new Token("BF")  ), null,null,analizadorTerceto.getProximoTerceto());
															analizadorTerceto.addTerceto(tercetoDO);
															analizadorTerceto.desapilarControl();
	}
					   | inicio_do UNTIL '(' condicion ')'  {this.addError("Falta bloque de sentencias.",((Token)$1.obj).getNroLinea());}
					   | inicio_do bloque_sentencias '(' condicion ')'  {this.addError("Falta 'until'.",((Token)$2.obj).getNroLinea());}
					   | inicio_do bloque_sentencias UNTIL condicion ')' {this.addError("Falta '('.",((Token)$3.obj).getNroLinea());}
					   | inicio_do bloque_sentencias UNTIL '(' ')'  {this.addError("Falta condicion.",((Token)$4.obj).getNroLinea());}
					   | inicio_do bloque_sentencias UNTIL '(' condicion {this.addError("Falta ')'.",((Token)$5.obj).getNroLinea());}
				//	   | inicio_do bloque_sentencias UNTIL '(' condicion ')' {this.addError("Falta ';'.",((Token)$6.obj).getNroLinea());}
				//     | bloque_sentencias UNTIL '(' condicion ')' {this.addError("Falta 'do'.",((Token)$1.obj).getNroLinea());} // TIRA REDUCE/REDUCE FALTA DO
					   ;
	
	imprimir: factor
			| CADENA
	
	salida_ejecutable : PRINT '(' imprimir ')'  {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia PRINT");
												TercetoS terceto = new TercetoS ( new TercetoIndividual( (Token)$1.obj ),new TercetoIndividual( (Token)$3.obj ), null, analizadorTerceto.getProximoTerceto() );
												analizadorTerceto.addTerceto(terceto);
	
	}
					  | '(' imprimir ')'  {this.addError("Falta 'PRINT'.",((Token)$1.obj).getNroLinea());}
					  | PRINT imprimir ')'  {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
					  | PRINT '(' ')'  {this.addError("Falta cadena .",((Token)$2.obj).getNroLinea());}
					  | PRINT '(' imprimir  {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
				//	  | PRINT '(' imprimir ')' {this.addError("Falta ';'.",((Token)$4.obj).getNroLinea());}
					  ;

	asign : variable ASIGNACION expresion_aritmetica ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Asignacion");
														
														if( esCompatible(((Token)$1.obj),((Token)$3.obj))){
															TercetoS tercetoAsignacion = new TercetoS(new TercetoIndividual(((Token)$2.obj)), new TercetoIndividual(((Token)$1.obj)), new TercetoIndividual(((Token)$3.obj)),analizadorTerceto.getProximoTerceto());
															analizadorTerceto.addTerceto(tercetoAsignacion);
														}
														else
															System.out.println("no son compatibles as");
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
				//	  | ID '.' metodo '('  {this.addError("Falta ')'.",((Token)$4.obj).getNroLinea());} GENERA SHIFT/REDUCE
					  ;

%%




private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

private ArrayList<Token> listaVariables = new ArrayList<Token>();

private AnalizadorTercetos analizadorTerceto;


public Parser(AnalizadorLexico lexico, TablaSimbolos tablaSimbolos, AnalizadorTercetos terceto) {
	this.lexico = lexico;
	this.tablaSimbolos = tablaSimbolos;
	this.analizadorTerceto= terceto;
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
		if(lexema.equals("32768")){
			if (tablaSimbolos.getClave(lexema).getCantRef()==1)
				tablaSimbolos.eliminarClave(lexema);
			else
				tablaSimbolos.getClave(lexema).decrementarRef();
		
		if(tablaSimbolos.existeClave("32767")) 
			tablaSimbolos.getClave("32767").incrementarRef();
		else
			tablaSimbolos.agregar("32767", new Token("int",1));
	}
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.existeClave(lexema)) {
		if (tablaSimbolos.getClave(lexema).getCantRef()==1)
			tablaSimbolos.eliminarClave(lexema);
		else
			tablaSimbolos.getClave(lexema).decrementarRef();
	}
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else
		tablaSimbolos.agregar(lexemaNuevo, new Token("int",1));
}


public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if (tablaSimbolos.existeClave(lexema))
		if(tablaSimbolos.getClave(lexema).getCantRef() == 1) 
			tablaSimbolos.eliminarClave(lexema);
		else
			tablaSimbolos.getClave(lexema).decrementarRef();
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else
		tablaSimbolos.agregar(lexemaNuevo, new Token("float",1));

}

public void actualizarTablaVariables(Token tipo,String uso,Token cteE) {
	String lexema = "";
	Token t = null;
	
	while(!listaVariables.isEmpty()) {
		t = listaVariables.get(0);
		listaVariables.remove(0);
		lexema = t.getLexema();
		if(!tablaSimbolos.getClave(lexema).getDeclarada()) {
			tablaSimbolos.getClave(lexema).setTipo(tipo.getLexema());
			tablaSimbolos.getClave(lexema).setDeclarada(true);
			tablaSimbolos.getClave(lexema).setUso(uso);
			if(tablaSimbolos.getClave(lexema).getUso().equals("Nombre de Coleccion")) {
				tablaSimbolos.getClave(lexema).setTamanioColeccion(Integer.parseInt(cteE.getLexema()));
			}
		}
		else
			this.addError("Error en declaracion, variable o coleccion '" + lexema + "' redeclarada",t.getNroLinea());
	}
}

public boolean estaDeclarada(Token t){
	if (tablaSimbolos.getClave(t.getLexema()).getDeclarada())
		return true;
	else
		return false;
}

public boolean esCompatible(Token t1, Token t2){
	if (t1.getTipo().equals(t2.getTipo()))
		return true;
	return false;
}



