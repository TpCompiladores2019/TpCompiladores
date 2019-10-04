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
	DISTINTO
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
	
%start programa
%left '+' '-' 
%left '*' '/'

%%

	programa : conjunto_sentencias
			 ;

	conjunto_sentencias : sentencias_declarativas
						| sentencias_declarativas sentencias_ejecutables
						| sentencias_ejecutables
						;
	
	sentencias_declarativas : sentencias_declarativas sentencia_declarativa
							| sentencia_declarativa 
							;
							
	sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable
							| sentencia_ejecutable
							;
							
	sentencia_declarativa : tipo lista_variables
						  | tipo ID '[' CTE_E ']' ';'
						  ;
							
	tipo : INT 
	 	 | FLOAT
		 ;
		 
	lista_variables : lista_variables ',' ID 
					| ID ';'
					;
					
	sentencia_ejecutable : selecion_ejecutable
						  | control_ejecutable
						  | salida_ejecutable
						  | asign
						  ;
	
	selecion_ejecutable : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF ';'
						| IF '(' condicion ')' bloque_sentencias END_IF ';'
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
			 | DISTINTO
			 | IGUAL
			 ;
			
	bloque_sentencias : sentencia_declarativa
					  | sentencia_ejecutable
					  | BEGIN conjunto_sentencias END
					  ;

	
	control_ejecutable : DO bloque_sentencias UNTIL '(' condicion ')' ';'
						;
	
	salida_ejecutable : PRINT '(' CADENA ')' ';'
					  ;
	asign : variable ASIGNACION expresion_aritmetica ';'
		  ;
		 
	invocacion_metodo : ID '.' ID '(' ')' 
					  ;















