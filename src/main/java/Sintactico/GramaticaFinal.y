%{
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
	import CodigoIntermedio.AnalizadorTercetos;
	import CodigoIntermedio.TercetoAbstracto;
	import CodigoIntermedio.TercetoAsignacion;
	import CodigoIntermedio.TercetoComparacion;
	import CodigoIntermedio.TercetoDO;
	import CodigoIntermedio.TercetoExpresionMult;
	import CodigoIntermedio.TercetoIF;
	import CodigoIntermedio.TercetoPrint;
	import CodigoIntermedio.TercetoExpresionDivision;
	import CodigoIntermedio.TercetoExpresion;
	import CodigoIntermedio.TercetoEtiqueta;
	import CodigoIntermedio.TercetoAsignacionRowing;
	import CodigoIntermedio.TercetoMetodos;
	import CodigoIntermedio.TercetoColeccionDer;
	import CodigoIntermedio.TercetoColeccionIzq;
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
	LENGTH
	
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
					;
					
	sentencia_ejecutable : seleccion_ejecutable ';'
						  | control_ejecutable ';'
						  | salida_ejecutable ';'
						  | asign 
						  | seleccion_ejecutable {this.addError("Error, falta ';'",lexico.getNroLinea());}
						  | control_ejecutable {this.addError("Error, falta ';'",lexico.getNroLinea());}
						  | salida_ejecutable {this.addError("Error, falta ';'",lexico.getNroLinea());}
						  ;
	
	
	condicion_if : condicion { if (noHayErrores()){
								if (((Token)$1.obj).getOperador()!=null){
								TercetoIF tercetoIF = new TercetoIF ( new Token( "BF"), new Token("@"+analizadorTerceto.getNumeroTerceto()), null, analizadorTerceto.getProximoTerceto());
								tercetoIF.setTipoSalto(((Token)$1.obj).getOperador(),((Token)$1.obj).getTipo());
								analizadorTerceto.addTerceto(tercetoIF);
								analizadorTerceto.apilar();
								}
											}
											}
					;
					

	
	seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias ELSE {
																if (noHayErrores()){
																TercetoAbstracto tercetoIF = new TercetoIF (new Token("BI"), null, null, analizadorTerceto.getProximoTerceto() );
                                                             	analizadorTerceto.addTerceto (tercetoIF);
                                                             	analizadorTerceto.desapilar();
                                                             	analizadorTerceto.apilar();}}

						bloque_sentencias END_IF {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia IF-ELSE");
												
												if (noHayErrores()){
												analizadorTerceto.desapilar();}}
												
						
						| IF '(' condicion_if ')' bloque_sentencias END_IF {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia IF");
																		if (noHayErrores()){
																			analizadorTerceto.desapilar();}}
																			
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
						| IF '(' condicion_if ')' bloque_sentencias error{this.addError("Falta 'end_if'",lexico.getNroLinea());} 
						;
						
	condicion : expresion_aritmetica operador expresion_aritmetica {
															Token t1 = ((Token)$1.obj);
															Token t3 = ((Token)$3.obj);
															if (estaDeclarada(t1))
																if (estaDeclarada(t3)) 
																	if( esCompatible(t1,t3)){
																	   if (noHayErrores()){
																			TercetoAbstracto tercetoComparacion = new TercetoComparacion(((Token)$2.obj), t1, t3,analizadorTerceto.getProximoTerceto());
																			analizadorTerceto.addTerceto(tercetoComparacion);
																			
																			Token nuevo = new Token ( "@" + analizadorTerceto.getNumeroTerceto());
																			nuevo.setTipo(((Token)$1.obj).getTipo());
																			nuevo.setOperador(((Token)$2.obj).getLexema());
																			$$ = new ParserVal(nuevo);
																		}
																	}
																	else
																		analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
																else{
																	analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
																	if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																		tablaSimbolos.eliminarClave(t3.getLexema());
																	else
																		tablaSimbolos.getClave(t3.getLexema()).decrementarRef();}
															else{
																analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
																if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																	tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
														}
														
															
			  | operador expresion_aritmetica {this.addError("Falta expresion del lado izquierdo.",((Token)$1.obj).getNroLinea());}
			  | expresion_aritmetica operador error{this.addError("Falta expresion del lado derecho.",((Token)$2.obj).getNroLinea());}
			  ;
			 
	expresion_aritmetica : expresion_aritmetica '+' termino {
															
															Token t1 = ((Token)$1.obj);
															Token t3 = ((Token)$3.obj);
															if (estaDeclarada(t1))
																if (estaDeclarada(t3)) 
																	if( esCompatible(t1,t3)){
																		if (noHayErrores()){
																			TercetoAbstracto tercetoExpresion = new TercetoExpresion(((Token)$2.obj), t1,t3,analizadorTerceto.getProximoTerceto());
																			analizadorTerceto.addTerceto(tercetoExpresion);
																			Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																			nuevo.setTipo(((Token)$1.obj).getTipo());
																			nuevo.setUso("Variable Auxiliar");
																			tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
																			$$ = new ParserVal(nuevo);
																			}
																	}
																	else
																		analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
																else{
															analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
															if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t3.getLexema()).decrementarRef();
															}
															
														else{
															analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
															if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();
															}	
										
														}
														
															
															
						 | expresion_aritmetica '-' termino {
															Token t1 = ((Token)$1.obj);
															Token t3 = ((Token)$3.obj);
															
															if (estaDeclarada(t1))
																if (estaDeclarada(t3))
																	if( esCompatible(t1,t3)){
																		if (noHayErrores()){
																			TercetoAbstracto tercetoExpresion = new TercetoExpresion(((Token)$2.obj), t1, t3,analizadorTerceto.getProximoTerceto());
																			analizadorTerceto.addTerceto(tercetoExpresion);
								
																			Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																			nuevo.setTipo(((Token)$1.obj).getTipo());
																			nuevo.setUso("Variable Auxiliar");
																			tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
																			$$ = new ParserVal(nuevo);
																		}
																	}
																	else
																		analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
																else{
																	analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
																	if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}
															else{
																analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
																if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
									
														}
															
						 | termino 
						 ;
						 
			termino : termino '*' factor {	
											Token t1 = ((Token)$1.obj);
											Token t3 = ((Token)$3.obj);
											if (estaDeclarada(t1))
												if (estaDeclarada(t3))
													if( esCompatible(t1,t3)){
														if (noHayErrores()){
															TercetoAbstracto tercetoExpresion = new TercetoExpresionMult(((Token)$2.obj),t1, t3,analizadorTerceto.getProximoTerceto());
															analizadorTerceto.addTerceto(tercetoExpresion);
															Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
															nuevo.setTipo(((Token)$1.obj).getTipo());
															nuevo.setUso("Variable Auxiliar");
															tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
															$$ = new ParserVal(nuevo);
														}
													}
													else
														analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
												else{
											analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t3.getLexema()).decrementarRef();}
									else{
										analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
									
								}
																	
												
															
			| termino '/' factor {  
									Token t1 = ((Token)$1.obj);
								    Token t3 = ((Token)$3.obj);
									if (estaDeclarada(t1))
										if (estaDeclarada(t3))
											if( esCompatible(t1,t3)){
												if (noHayErrores()){
													TercetoAbstracto tercetoExpresion = new TercetoExpresionDivision(((Token)$2.obj), t1, t3,analizadorTerceto.getProximoTerceto());
													analizadorTerceto.addTerceto(tercetoExpresion);
													Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
													nuevo.setTipo(((Token)$1.obj).getTipo());
													nuevo.setUso("Variable Auxiliar");
													tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
													$$ = new ParserVal(nuevo);
													}
											}
											else
												analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
										else{
											analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t3.getLexema()).decrementarRef();}
									else{
										analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
									}
									
																	
															

			| factor {}
		 
			;
			
	factor : variable {}
	       | CTE_E {actualizarTablaPositivo(((Token)$1.obj).getLexema());
					((Token)$1.obj).setTipo("int");
					if(((Token)$1.obj).getLexema().equals("32768"))
						((Token)$1.obj).setLexema("32767");
					$$ = $1;
					}
		   | CTE_F {((Token)$1.obj).setTipo("float");
					tablaSimbolos.getClave(((Token)$1.obj).getLexema()).setUso("CTF");
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
	
	variable : ID '[' ID ']' {	
								if (!estaDeclarada(((Token)$1.obj))){
										analizadorTerceto.agregarError("Error coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)$1.obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)$1.obj).getLexema()).decrementarRef();}
										$$ = new ParserVal(new Token("@" + ((Token)$1.obj).getLexema()));
								}
								else
									if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
										analizadorTerceto.agregarError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",lexico.nroLinea);
								if (!estaDeclarada(((Token)$3.obj))){
										analizadorTerceto.agregarError("Error variable '"+((Token)$3.obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)$3.obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)$3.obj).getLexema()).decrementarRef();}
									
								}
								else
									if (!tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getTipo().equals("int"))
										analizadorTerceto.agregarError("El tipo del subindice no es entero",lexico.nroLinea);
									else
										if (tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getUso().equals("Nombre de Coleccion"))
											analizadorTerceto.agregarError("Error '"+((Token)$3.obj).getLexema() + "' es una coleccion.",lexico.nroLinea);
										else{
										//	ACAif (noHayErrores()){
												((Token)$1.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												((Token)$3.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												TercetoColeccionDer terceto = new TercetoColeccionDer((new Token("OFFSET")), ((Token)$1.obj), ((Token)$3.obj),analizadorTerceto.getProximoTerceto());
												
												
												terceto.setTamanio(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												$$ = new ParserVal(nuevo);
										//ACA	}
										}
								}
								
									
			 | ID '[' CTE_E ']' {	
									if (!estaDeclarada(((Token)$1.obj))){
											analizadorTerceto.agregarError("Coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)$1.obj).getLexema());
											}
											else {
												tablaSimbolos.getClave(((Token)$1.obj).getLexema()).decrementarRef();}
											$$ = new ParserVal(new Token("@" + ((Token)$1.obj).getLexema()));
										
									}
									else{
										if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
											analizadorTerceto.agregarError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",lexico.nroLinea);
										else{
											//ACAif (noHayErrores()){
												((Token)$1.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												((Token)$3.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												tablaSimbolos.getClave(((Token)$3.obj).getLexema()).setUso("CTE");
												TercetoColeccionDer terceto = new TercetoColeccionDer(new Token("OFFSET"), ((Token)$1.obj), ((Token)$3.obj),analizadorTerceto.getProximoTerceto());
												terceto.setTamanio(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												
												$$ = new ParserVal(nuevo);
										//	ACA	}
										}
									}
								
								}
			 | ID {	
					Token t = ((Token)$1.obj);
					t.setTipo(tablaSimbolos.getClave(t.getLexema()).getTipo());
					$$ = new ParserVal(t);
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
	
			;
			
	
	
	inicio_do : DO { if (noHayErrores()){
					analizadorTerceto.apilar();
					TercetoAbstracto tercetoEtiqueta = new TercetoEtiqueta(new Token("Lbl" + analizadorTerceto.getProximoTerceto()),null,null,analizadorTerceto.getProximoTerceto());
					tercetoEtiqueta.setNombre("Etiqueta");
					analizadorTerceto.addTerceto(tercetoEtiqueta);}}
	
	control_ejecutable : inicio_do bloque_sentencias UNTIL '(' condicion ')'  {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia until");
													if (noHayErrores()){
																TercetoDO tercetoDO = new TercetoDO(new Token("BF"), null,null,analizadorTerceto.getProximoTerceto());
																tercetoDO.setTipoSalto(((Token)$5.obj).getOperador(),((Token)$5.obj).getTipo());

															analizadorTerceto.addTerceto(tercetoDO);
															analizadorTerceto.desapilarControl();
	}
	}
					   | inicio_do UNTIL '(' condicion ')'  {this.addError("Falta bloque de sentencias.",((Token)$1.obj).getNroLinea());}
					   | inicio_do bloque_sentencias '(' condicion ')'  {this.addError("Falta 'until'.",((Token)$2.obj).getNroLinea());}
					   | inicio_do bloque_sentencias UNTIL condicion ')' {this.addError("Falta '('.",((Token)$3.obj).getNroLinea());}
					   | inicio_do bloque_sentencias UNTIL '(' ')'  {this.addError("Falta condicion.",((Token)$4.obj).getNroLinea());}
					   | inicio_do bloque_sentencias UNTIL '(' condicion {this.addError("Falta ')'.",((Token)$5.obj).getNroLinea());}
					   ;
	
	imprimir: factor
			| CADENA {$$ = $1;}
	
	salida_ejecutable : PRINT '(' imprimir ')'  {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia PRINT");
												if (noHayErrores()){
												TercetoPrint tercetoPrint = new TercetoPrint ( ((Token)$1.obj), ((Token)$3.obj ), null, analizadorTerceto.getProximoTerceto() );
												tercetoPrint.setNombre("Print");
												analizadorTerceto.addTerceto(tercetoPrint);
												}
											}
					  | '(' imprimir ')'  {this.addError("Falta 'PRINT'.",((Token)$1.obj).getNroLinea());}
					  | PRINT imprimir ')'  {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
					  | PRINT '(' ')'  {this.addError("Falta variable o cadena .",((Token)$2.obj).getNroLinea());}
					  | PRINT '(' imprimir  {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
					  ;

	asignacion_izq :  ID '[' ID ']' {	
								if (!estaDeclarada(((Token)$1.obj))){
										analizadorTerceto.agregarError("Error coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)$1.obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)$1.obj).getLexema()).decrementarRef();}
										$$ = new ParserVal(new Token("@" + ((Token)$1.obj).getLexema()));
								}
								else
									if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
										analizadorTerceto.agregarError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",lexico.nroLinea);
									
								if (!estaDeclarada(((Token)$3.obj))){
										analizadorTerceto.agregarError("Error variable '"+((Token)$3.obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)$3.obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)$3.obj).getLexema()).decrementarRef();}
								}
								else
									if (!tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getTipo().equals("int"))
										analizadorTerceto.agregarError("El tipo del subindice no es entero",lexico.nroLinea);
									else
										if (tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getUso().equals("Nombre de Coleccion"))
											analizadorTerceto.agregarError("Error '"+((Token)$3.obj).getLexema() + "' es una coleccion.",lexico.nroLinea);										
										else{
											//	ACAif (noHayErrores()){
												((Token)$1.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												((Token)$3.obj).setTipo(tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getTipo());
												TercetoColeccionIzq terceto = new TercetoColeccionIzq((new Token("OFFSET")), ((Token)$1.obj), ((Token)$3.obj),analizadorTerceto.getProximoTerceto());
												terceto.setTamanio(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												$$ = new ParserVal(nuevo);
											//	ACA}
									}
								}
								
									
			 | ID '[' CTE_E ']' {	
									if (!estaDeclarada(((Token)$1.obj))){
											analizadorTerceto.agregarError("Coleccion '"+((Token)$1.obj).getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)$1.obj).getLexema());
											}
											else {
												tablaSimbolos.getClave(((Token)$1.obj).getLexema()).decrementarRef();}
											$$ = new ParserVal(new Token("@" + ((Token)$1.obj).getLexema()));
									}
									else{
										if (tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getUso().equals("Variable"))
											analizadorTerceto.agregarError("Error '"+((Token)$1.obj).getLexema() + "' es una variable.",lexico.nroLinea);
										else{
										//	ACA	if (noHayErrores()){
												((Token)$1.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												((Token)$3.obj).setTipo(tablaSimbolos.getClave(((Token)$3.obj).getLexema()).getTipo());
												tablaSimbolos.getClave(((Token)$3.obj).getLexema()).setUso("CTE");
												TercetoColeccionIzq terceto = new TercetoColeccionIzq(new Token("OFFSET"), ((Token)$1.obj), ((Token)$3.obj),analizadorTerceto.getProximoTerceto());
												terceto.setTamanio(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												
												$$ = new ParserVal(nuevo);
										//	ACA	}
										}
									}
								}
								
			 | ID {	
					Token t = ((Token)$1.obj);
					t.setTipo(tablaSimbolos.getClave(t.getLexema()).getTipo());
					$$ = new ParserVal(t);
				}
			 ;
					
	asign : asignacion_izq ASIGNACION expresion_aritmetica ';' {listaCorrectas.add("Linea " + lexico.getNroLinea() + ": Asignacion");
														
														Token t1 = ((Token)$1.obj);
														Token t3 = ((Token)$3.obj);
														if (estaDeclarada(t1))
															if (estaDeclarada(t3))
																if( esCompatible(t1,t3)){
																	if (esColeccion(t1.getLexema())){
																		if (noHayErrores()){
																			TercetoAsignacionRowing tercetoAsignacionRowing = new TercetoAsignacionRowing(((Token)$2.obj), t1, t3,analizadorTerceto.getProximoTerceto());
																			 tercetoAsignacionRowing.setTamanio(tablaSimbolos.getClave(t1.getLexema()).getTamanioColeccion());

																			analizadorTerceto.addTerceto(tercetoAsignacionRowing);
																		}
																	}
																	else{
																		if (noHayErrores()){
																			TercetoAbstracto tercetoAsignacion = new TercetoAsignacion(((Token)$2.obj), t1, t3,analizadorTerceto.getProximoTerceto());			
																			analizadorTerceto.addTerceto(tercetoAsignacion);
																		}
																	}
																}
																else
																	analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															else{
																analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
																tablaSimbolos.eliminarClave(t3.getLexema());}
														else{
															analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
															tablaSimbolos.eliminarClave(t1.getLexema());}	
														}
														

		  | ASIGNACION expresion_aritmetica ';' {this.addError("Falta variable.",lexico.getNroLinea());}
		  | asignacion_izq expresion_aritmetica ';' {this.addError("Falta ':='.",lexico.getNroLinea());}
		  | asignacion_izq ASIGNACION ';' {this.addError("Falta expresion.",lexico.getNroLinea());}
		  | asignacion_izq ASIGNACION expresion_aritmetica {this.addError("Falta ';'.",lexico.getNroLinea());}
		  ;
		  
	metodo : FIRST 
			| LAST
			| LENGTH
			;
			
	invocacion_metodo : ID '.' metodo '(' ')' { 
										
											if (esColeccion(((Token)$1.obj).getLexema())){
											//	ACA		if (noHayErrores()){
													((Token)$1.obj).setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
													TercetoMetodos tercetoMetodo = new TercetoMetodos(new Token("METODO"), ((Token)$1.obj) , 					((Token)$3.obj),analizadorTerceto.getProximoTerceto());
													tercetoMetodo.setTamanio(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTamanioColeccion()); 
													analizadorTerceto.addTerceto(tercetoMetodo);
											
													Token nuevo = new Token("@" + analizadorTerceto.getNumeroTerceto());
													if (((Token)$3.obj).getLexema().equals("length"))
														nuevo.setTipo("int");
													else
														nuevo.setTipo(tablaSimbolos.getClave(((Token)$1.obj).getLexema()).getTipo());
													nuevo.setUso("Variable Auxiliar");
													tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
													$$ = new ParserVal(nuevo);
											//	ACA		}
											}
											else
												analizadorTerceto.agregarError("La variable '" + ((Token)$1.obj).getLexema() +"' no puede invocar metodos, debe ser una coleccion." ,lexico.nroLinea);
										}
										
					  | ID '.' metodo ')' {this.addError("Falta '(.'",((Token)$3.obj).getNroLinea());}
					  | ID '.' '(' ')' {this.addError("Falta metodo.",((Token)$2.obj).getNroLinea());}
					  | ID '.' ID '(' ')' {this.addError("No es un metodo predefinido.",((Token)$2.obj).getNroLinea());}
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
			else{
				tablaSimbolos.agregar("32767", new Token("int",1));
				tablaSimbolos.getClave("32767").setUso("CTE");
			}
		}
		else
			tablaSimbolos.getClave(lexema).setUso("CTE");
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.existeClave(lexema)) {
		if (tablaSimbolos.getClave(lexema).getCantRef()==1)
			tablaSimbolos.eliminarClave(lexema);
		else{
			tablaSimbolos.getClave(lexema).decrementarRef();
			tablaSimbolos.getClave(lexema).setUso("CTE");
		}
	}
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else{
		tablaSimbolos.agregar(lexemaNuevo, new Token("int",1));
		tablaSimbolos.getClave(lexemaNuevo).setUso("CTE");
	}
}


public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if (tablaSimbolos.existeClave(lexema))
		if(tablaSimbolos.getClave(lexema).getCantRef() == 1) 
			tablaSimbolos.eliminarClave(lexema);
		else{
			tablaSimbolos.getClave(lexema).decrementarRef();
			tablaSimbolos.getClave(lexema).setUso("CTF");
		}
			
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else{
		tablaSimbolos.agregar(lexemaNuevo, new Token("float",1));
		tablaSimbolos.getClave(lexemaNuevo).setUso("CTF");
	}

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
	if (t.getLexema().contains("@")) // es una variable aux. No puede escribir un id comun con @
		return true;
	Token token = tablaSimbolos.getClave(t.getLexema());
	if (token==null) {
		return false;
	}
	return (!token.getTipo().isEmpty());
}

public boolean esCompatible(Token t1, Token t2){
	return (t1.getTipo().equals(t2.getTipo()));
}

public boolean esColeccion(String lexema){
	Token t = tablaSimbolos.getClave(lexema);
	if (t!=null)
		if(t.getUso().equals("Nombre de Coleccion"))
			return true;
	return false;
}

public boolean estaVacia() {
	return listaErrores.isEmpty();

}

public boolean noHayErrores(){
	return (listaErrores.isEmpty() && analizadorTerceto.estaVacia() && lexico.estaVacia() );
}

