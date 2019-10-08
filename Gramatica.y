%{
	//LIBRERIAS JAVA
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
	PRINNT
	INT
	BEGIN
	END
	DO
	UNTIL
	FLOAT
	
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
							
	sentencia_declarativa : tipo lista_variables ';'
						  | tipo ID '[' CTE_E ']' ';'
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
	
	selecion_ejecutable : if '(' condicion ')' bloque_sentencias else bloque_sentencias end_if ';'
						| if '(' condicion ')' bloque_sentencias end_if ';'
						;
						
	condicion : expresion_aritmetica operador expresion_aritmetica
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
	       | CTE_E
		   | CTE_F
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
					  ;

	
	control_ejecutable : do bloque_sentencias until '(' condicion ')' ';'
						;
	
	salida_ejecutable : print '(' CADENA ')' ';'
					  ;
	asign : variable ASIGNACION expresion_aritmetica ';'
		  ;
		 
	invocacion_metodo : ID '.' ID '(' ')' 
					  ;

	sentencias : sentencias sentencia_declarativa
			   | sentencias sentencia_ejecutable
			   | sentencia_declarativa
			   | sentencia_ejecutable
			   ;














