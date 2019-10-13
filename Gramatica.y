%{
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
	import java.util.List;
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
	if
	else
	end_if
	print
	int
	begin
	end
	do
	until
	float
	
%start programa
%left '+' '-' 
%left '*' '/'

%%

	programa : conjunto_sentencias
			 ;

	conjunto_sentencias : sentencias_declarativas
						| sentencias_declarativas begin sentencias_ejecutables end
						| begin sentencias_ejecutables end
						;
	
	sentencias_declarativas : sentencias_declarativas sentencia_declarativa
							| sentencia_declarativa 
							;
							
	sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable
							| sentencia_ejecutable
							;
							
	sentencia_declarativa : tipo lista_variables ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia declarativa");}
						  | tipo ID '[' CTE_E ']' ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia declarativa");}
						  | ID '[' CTE_E ']' ';' {this.addError("Error en declaracion, falta definir el tipo.",((Token)$2.obj).getNroLinea());}
						  | lista_variables ';' {this.addError("Error en declaracion, falta definir el tipo.",((Token)$1.obj).getNroLinea());}
						  | tipo ID '[' CTE_E ']'  {this.addError("Error en declaracion, falta ';'.",((Token)$2.obj).getNroLinea());}
						  | tipo lista_variables  {this.addError("Error en declaracion, falta ';'.",((Token)$2.obj).getNroLinea());}
						  | tipo ID '[' CTE_E ';' {this.addError("Error en declaracion, falta ']'.",((Token)$2.obj).getNroLinea());}
						  | tipo ID CTE_E ']' ';' {this.addError("Error en declaracion, falta '['.",((Token)$2.obj).getNroLinea());}
						  | tipo ';' {this.addError("Error en declaracion, falta variables.",((Token)$2.obj).getNroLinea());}
						  | tipo '[' CTE_E ']' ';' {this.addError("Error en declaracion, falta identificador.",((Token)$2.obj).getNroLinea());}
						  | tipo ID '[' ']' ';' {this.addError("Error en declaracion, falta constante.",((Token)$2.obj).getNroLinea());}
						  ;
							
	tipo : int 
	 	 | float
		 ;
		 
	lista_variables : lista_variables ',' ID 
					| ID
					;
					
	sentencia_ejecutable : selecion_ejecutable
						  | control_ejecutable
						  | salida_ejecutable
						  | asign
						  ;
	
	selecion_ejecutable : if '(' condicion ')' bloque_sentencias else bloque_sentencias end_if ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia if-else");}
						| if '(' condicion ')' bloque_sentencias end_if ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia if");}
						| if '(' condicion ')' bloque_sentencias else bloque_sentencias end_if {this.addError("Falta ';'.",((Token)$8.obj).getNroLinea());}
						| if '(' condicion ')' bloque_sentencias else bloque_sentencias ';' {this.addError("Falta 'end_if'.",((Token) $7.obj).getNroLinea());}
						| if '(' condicion ')' bloque_sentencias bloque_sentencias end_if ';' {this.addError("Falta 'else'.",((Token)$6.obj).getNroLinea());}
						| if '(' condicion ')' bloque_sentencias else end_if ';' {this.addError("Falta bloque de sentencias.",((Token)$6.obj).getNroLinea());}
						| if '(' condicion ')' else bloque_sentencias end_if ';' {this.addError("Falta bloque de sentencias.",((Token)$4.obj).getNroLinea());}
						| if '(' condicion bloque_sentencias else bloque_sentencias end_if ';' {this.addError( "Falta ')'.",((Token)$3.obj).getNroLinea());}
						| if '(' ')' bloque_sentencias else bloque_sentencias end_if ';' {this.addError("Falta condicion.",((Token)$2.obj).getNroLinea());}
						| if condicion ')' bloque_sentencias else bloque_sentencias end_if ';' {this.addError("Falta ')'.",((Token)$1.obj).getNroLinea());}
						| '(' condicion ')' bloque_sentencias else bloque_sentencias end_if ';' {this.addError("Falta 'if'.",((Token)$1.obj).getNroLinea());}
						| '(' condicion ')' bloque_sentencias end_if ';' {this.addError("Falta 'if'.",((Token)$1.obj).getNroLinea());}
						| if condicion ')' bloque_sentencias end_if ';' {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
						| if '(' ')' bloque_sentencias end_if ';' {this.addError("Falta condicion.",((Token)$2.obj).getNroLinea());}
						| if '(' condicion bloque_sentencias end_if ';' {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
						| if '(' condicion ')' end_if ';' {this.addError("Falta bloque de sentencias.",((Token)$4.obj).getNroLinea());}
						| if '(' condicion ')' bloque_sentencias ';' {this.addError("Falta 'end_if'.",((Token)$5.obj).getNroLinea());}
						| if '(' condicion ')' bloque_sentencias end_if {this.addError("Falta ';'.",((Token)$6.obj).getNroLinea());}
						;
						
	condicion : expresion_aritmetica operador expresion_aritmetica 
			  | operador expresion_aritmetica {this.addError("Falta expresion del lado izquierdo.",((Token)$1.obj).getNroLinea());}
			  | expresion_aritmetica expresion_aritmetica {this.addError("Falta operador.",((Token)$1.obj).getNroLinea());}
			  | expresion_aritmetica operador {this.addError("Falta expresion del lado derecho.",((Token)$2.obj).getNroLinea());}
			  ;
			 
	expresion_aritmetica : expresion_aritmetica '+' termino
						 | expresion_aritmetica '-' termino
						 | termino
						 ;
						 
	termino : termino '*' factor
			| termino '/' factor
			| factor
			;
			
	factor : variable
	       | CTE_E {this.agregarConstante(((Token)$1.obj).getLexema(),"int",false);}
		   | CTE_F {this.agregarConstante(((Token)$1.obj).getLexema(),"float",false);}
		   | '-' CTE_E {this.agregarConstante(((Token)$1.obj).getLexema(),"int",true);}
		   | '-' CTE_F {this.agregarConstante(((Token)$1.obj).getLexema(),"float",true);}
		   | invocacion_metodo
		   ;
	
	variable : ID '[' ID ']'
			 | ID '[' CTE_E ']'
			 | ID
			 ;
	
	operador : '<'
			 | MENORIGUAL
			 | '>'
			 | MAYORIGUAL
			 | DESTINTO
			 | IGUAL
			 ;
			
	bloque_sentencias : sentencia_declarativa 
					  | sentencia_ejecutable 
					  | begin sentencias sentencia_ejecutable end 
					  | begin sentencias sentencia_declarativa end 
					  | sentencias sentencia_ejecutable end {this.addError("Falta 'begin'.",((Token)$1.obj).getNroLinea());} 
					  | begin sentencia_ejecutable end {/*Error sentencias*/}
					  | begin sentencias end {/*Error sencia_ejecutable*/}
					  | begin sentencias sentencia_ejecutable {this.addError("Falta 'end'.",((Token)$3.obj).getNroLinea());}
					  | sentencias sentencia_declarativa end {this.addError("Falta 'begin'.",((Token)$1.obj).getNroLinea());}
					  | begin sentencia_declarativa end {/*Error sentencias*/}
					  | begin sentencias end {/*Errorsentencia_declarativa*/}
					  | begin sentencias sentencia_declarativa {this.addError("Falta 'end'.",((Token)$3.obj).getNroLinea());}
					  ;

	
	control_ejecutable : do bloque_sentencias until '(' condicion ')' ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia until");}
					   | bloque_sentencias until '(' condicion ')' ';' {this.addError("Falta 'do'.",((Token)$1.obj).getNroLinea());}
					   | do until '(' condicion ')' ';' {this.addError("Falta bloque de sentencias.",((Token)$1.obj).getNroLinea());}
					   | do bloque_sentencias '(' condicion ')' ';' {this.addError("Falta 'until'.",((Token)$2.obj).getNroLinea());}
					   | do bloque_sentencias until condicion ')' ';' {this.addError("Falta '('.",((Token)$3.obj).getNroLinea());}
					   | do bloque_sentencias until '(' ')' ';' {this.addError("Falta condicion.",((Token)$4.obj).getNroLinea());}
					   | do bloque_sentencias until '(' condicion ';' {this.addError("Falta ')'.",((Token)$5.obj).getNroLinea());}
					   | do bloque_sentencias until '(' condicion ')' {this.addError("Falta ';'.",((Token)$6.obj).getNroLinea());}
					   ;
	
	salida_ejecutable : print '(' CADENA ')' ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Sentencia print");}
					  | '(' CADENA ')' ';' {this.addError("Falta 'print'.",((Token)$1.obj).getNroLinea());}
					  | print CADENA ')' ';' {this.addError("Falta '('.",((Token)$1.obj).getNroLinea());}
					  | print '(' error ')' ';' {this.addError("Solo se puede definir una cadena.",((Token)$2.obj).getNroLinea());}
					  | print '(' CADENA ';' {this.addError("Falta ')'.",((Token)$3.obj).getNroLinea());}
					  | print '(' CADENA ')' {this.addError("Falta ';'.",((Token)$4.obj).getNroLinea());}
					  ;

	asign : variable ASIGNACION expresion_aritmetica ';' {listaCorrectas.add("Linea " + ((Token)$1.obj).getNroLinea() + ": Asignacion");}
		  | ASIGNACION expresion_aritmetica ';' {this.addError("Falta variable.",((Token)$1.obj).getNroLinea());}
		  | variable expresion_aritmetica ';' {this.addError("Falta ':='.",((Token)$1.obj).getNroLinea());}
		  | variable ASIGNACION ';' {this.addError("Falta expresion.",((Token)$2.obj).getNroLinea());}
		  | variable ASIGNACION expresion_aritmetica {this.addError("Falta ';'.",((Token)$3.obj).getNroLinea());}
		  ;
		 
	invocacion_metodo : ID '.' ID '(' ')' //Falta
					  ;

	sentencias : sentencias sentencia_declarativa
			   | sentencias sentencia_ejecutable
			   | sentencia_declarativa
			   | sentencia_ejecutable
			   ;

%%



private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

public Parser(AnalizadorLexico lexico, TablaSimbolos tablaSimbolos) {
	this.lexico = lexico;
	this.tablaSimbolos = tablaSimbolos;
}

public int yylex() {
	int idToken = lexico.yylex();
	yylval = new ParserVal(lexico.token);
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

public void imprimirInformacion() {
	if (!listaCorrectas.isEmpty()){
		System.out.println("Estructuras Sintacticas: ");
		for (String info : listaCorrectas) {
			System.out.println(info);
		}
	}
}

public void imprimirError() {
	if (!listaErrores.isEmpty()){
		System.out.println("Errores Sintacticos: ");
		for (Error e : listaErrores) {
			System.out.println(e);
		}
	}
}
/*
public void agregarATablaSimbolos(Token[] tokensSentencia) {
	//en 0 esta el tipo , en 1 el primer id, en 2 la coma, en 3 id, etc
	ParserVal aux;
	int indice =0;
	String tipoVariables;
	if (valstk[indice] != null) {
		tipoVariables = ((Token) valstk[indice].obj).getLexema();
		indice++;
		}
	else
		return;
	int nroLinea = ((Token) valstk[indice].obj).getNroLinea();
	while (indice < YYSTACKSIZE) {
		String id = ((Token) valstk[indice].obj).getLexema();
		if (!tablaSimbolos.existeClave(id))
			tablaSimbolos.agregar(id, tipoVariables);
		else {
			Lexico.Error e = new Lexico.Error("Variable ya declarada",nroLinea," ","Error");		
			errores.add(e);
		}
			
		indice = indice +2;
		}
	}
}
*/
public void agregarConstante (String constante, String tipo, boolean esNegativa) {
	if (esNegativa) 
		constante = "-" + constante;
	if (!tablaSimbolos.existeClave(constante))		
		tablaSimbolos.agregar(constante, tipo);
	System.out.println("Entro a agregarConstante");	
}

private List<Error> erroresSemanticos = new ArrayList<Error>();
public void agregarVariable (String variable, String tipo) {
	
	if (!tablaSimbolos.existeClave(variable)) {
		tablaSimbolos.agregar(variable, tipo);
	}
	else{
		Error e = new Error ("La variable ya fue declarada.",AnalizadorLexico.nroLinea,"","Error");
		erroresSemanticos.add(e);
	}		
}		
//private List<Error> erroresSemanticos = new ArrayList<Error>()	;	
public void actualizarTabla(String id,String tipo, String valor) {

	if (tipo.equals("int")) {	
		if (Integer.parseInt(valor) < -32768) {
			Error nuevoError = new Error("El numero excede el menor valor posible",AnalizadorLexico.nroLinea," ","Error");
			erroresSemanticos.add(nuevoError);		
		}
		else if (Integer.parseInt(valor) > 32767) {
			Error nuevoError = new Error("El numero excede el mayor valor posible",AnalizadorLexico.nroLinea," ","Error");
			erroresSemanticos.add(nuevoError);	
		}
		else tablaSimbolos.agregar(id, tipo);
	}
	else {
		if ((Float.parseFloat(valor)> Float.MIN_NORMAL && Float.parseFloat(valor) < Float.MAX_VALUE) || (Float.parseFloat(valor)< -Float.MIN_NORMAL && Float.parseFloat(valor) > -Float.MAX_VALUE)
				|| (Float.parseFloat(valor) == 0.0)) {
			tablaSimbolos.agregar(id, tipo);
		}else {Error nuevoError = new Error("El numero excede los rangos",AnalizadorLexico.nroLinea," ","Error");
		erroresSemanticos.add(nuevoError);}
	}
	
}


