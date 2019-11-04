package Lexico;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import AccionesSemanticas.IAccionSemantica.ASFinalOperAsigComp;
import AccionesSemanticas.IAccionSemantica.ASAgregar;
import AccionesSemanticas.IAccionSemantica.ASAumentarNumLinea;
import AccionesSemanticas.IAccionSemantica.ASCerrarComentario;
import AccionesSemanticas.IAccionSemantica.ASFinalCadena;
import AccionesSemanticas.IAccionSemantica.ASFinalID;
import AccionesSemanticas.IAccionSemantica.ASFinalEntero;
import AccionesSemanticas.IAccionSemantica.ASFinalFloat;
import AccionesSemanticas.IAccionSemantica.ASConsumirComentario;
import AccionesSemanticas.IAccionSemantica.ASErrorCadenaMultilinea;
import AccionesSemanticas.IAccionSemantica.ASErrorCaracterFaltante;
import AccionesSemanticas.IAccionSemantica.ASFinalSimple;
import AccionesSemanticas.IAccionSemantica.ASError;
import AccionesSemanticas.IAccionSemantica.ASActivarPorcentaje;
import AccionesSemanticas.IAccionSemantica;


public class AnalizadorLexico {
	
	private final int F = 400;
	private final int E = -1;
	public static boolean comentarioAbierto= false;

	private Token token;
										//	L  D  _  .  E  e  -  +  /  *  :  > <  =  ' ' tb [  ] (   )  ,  ; \n  %  otros
	private int [][] transicionEstados = {{ 1, 2, E, 3, 1, 1, F, F,12, F, 8, 9,11,10, 0, 0, F, F, F, F, F, F, 0,15, E}, //0
										  { 1, 1, 1, F, 1, 1, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //1
										  { F, 2, F, 4, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //2
										  { F, 4, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E}, //3
										  { F, 4, F, F, 5, 5, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //4
										  { E, 7, E, E, E, E, 6, 6, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E}, //5
										  { E, 7, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E}, //6
										  { F, 7, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //7
										  { E, E, E, E, E, E, E, E, E, E, E, E, E, F, E, E, E, E, E, E, E, E, E, E, E}, //8
										  { F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //9
										  { E, E, E, E, E, E, E, E, E, E, E, E, E, F, E, E, E, E, E, E, E, E, E, 0, E}, //10
										  { F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //11
										  { F, F, F, F, F, F, F,13, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //12
										  {13,13,13,13,13,13,13,14,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13, 13}, //13 
										  {13,13,13,13,13,13,13,14, 0,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13, 13}, //14
										  {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15, F, F, E}, //15
								};

	private IAccionSemantica AS1; 
	private IAccionSemantica AS2;
	private IAccionSemantica AS3;
	private IAccionSemantica AS4;
	private IAccionSemantica AS5;
	private IAccionSemantica AS6;
	private IAccionSemantica AS7;
	private IAccionSemantica AS8;
	private IAccionSemantica AS9;
	private IAccionSemantica AS10;
	private IAccionSemantica AS11;
	private IAccionSemantica AS12;
	private IAccionSemantica AS13;
	private IAccionSemantica AS14;
	private IAccionSemantica AS15;

		
		
		public static int indiceLectura =0;	
		public static int nroLinea = 1;


		public static List <Error>listaErrores = new ArrayList<Error>();
		public static List <Error>listaWarning = new ArrayList<Error>();
		public static List <String> listaCorrectas = new ArrayList<String>();
		public static boolean porcentajeAbierto;
		
		private HashMap<Character,Integer> columnas=new HashMap<Character,Integer>();
	
		private TablaSimbolos tablaSimbolos;
		private TablaTokens tablaTokens;
		
		private FileReader fr;
		private ArrayList<Integer> ascii = new ArrayList<Integer>();
	
	
	public AnalizadorLexico(TablaSimbolos tablaSimbolos, TablaTokens tablaTokens,String args) throws IOException {
		this.tablaSimbolos = tablaSimbolos;
		this.tablaTokens = tablaTokens;
		tablaTokens.completarTabla();
		inicializarColumnas();
		AS1 = new ASAgregar(tablaTokens,tablaSimbolos); 
		AS2 = new ASFinalID(tablaTokens,tablaSimbolos);
		AS3 = new ASFinalEntero(tablaTokens,tablaSimbolos);
		AS4 = new ASFinalFloat(tablaTokens,tablaSimbolos);
		AS5 = null;
		AS6 = new ASFinalCadena(tablaTokens,tablaSimbolos);
		AS7 = new ASAumentarNumLinea(tablaTokens,tablaSimbolos);
		AS8 = new ASFinalOperAsigComp(tablaTokens,tablaSimbolos);
		AS9 = new ASError(tablaTokens,tablaSimbolos);
		AS10 = new ASConsumirComentario(tablaTokens,tablaSimbolos);
		AS11 = new ASErrorCaracterFaltante(tablaTokens,tablaSimbolos);
		AS12 = new ASErrorCadenaMultilinea(tablaTokens,tablaSimbolos);
		AS13 = new ASFinalSimple(tablaTokens,tablaSimbolos);
		AS14 = new ASCerrarComentario(tablaTokens,tablaSimbolos);
		AS15 = new ASActivarPorcentaje(tablaTokens,tablaSimbolos);
		cargarMatrizAS();
		
		
		fr = new FileReader(args);
		int numAscii;
		while ((numAscii=fr.read())!=-1) {
			ascii.add(numAscii);
		}
		ascii.add(32);
	}
	
	
	private IAccionSemantica [][] accionesSemanticas;
	
	private void cargarMatrizAS() {
		this.accionesSemanticas= new IAccionSemantica[][] {
//				   0   1    2    3    4    5    6    7    8    9   10   11    12   13  14   15   16   17   18   19   20   21    22  23	24  
//				   L   D    _    .    E    e    -    +    /    *    :    >    <    =   ' '  tb    [    ]    (    )    ,    ;    \n   %  otros
				{AS1 ,AS1 ,AS9 ,AS1 ,AS1 ,AS1 ,AS8 ,AS8 ,AS1 ,AS8 ,AS1 ,AS1 ,AS1 ,AS1 ,AS5 ,AS5 ,AS8 ,AS8 ,AS8 ,AS8 ,AS8 ,AS8 ,AS7 ,AS15, AS9}, //0
			    {AS1 ,AS1 ,AS1 ,AS2 ,AS1 ,AS1 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2, AS9}, //1
				{AS3 ,AS1 ,AS3 ,AS1 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3, AS9}, //2
				{AS13,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS9}, //3
				{AS4 ,AS1 ,AS4 ,AS4 ,AS1 ,AS1 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4, AS9}, //4
				{AS11,AS1 ,AS11,AS11,AS11,AS11,AS1 ,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS9}, //5
				{AS11,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS9}, //6
				{AS4 ,AS1 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4, AS9}, //7
				{AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS8 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11, AS9}, //8
				{AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS8,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13, AS9}, //9
				{AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS8 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11, AS9}, //10
				{AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS8,AS13,AS8,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13, AS9}, //11
				{AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS10,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13,AS13, AS9},  //12
				{AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS7 ,AS5, AS5}, //13
				{AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS14 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS7 ,AS5, AS5}, //14
				{AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS12 ,AS6, AS9},//15 
				};
		
	}



	private void inicializarColumnas() {
		columnas.put('a',0);	columnas.put('b',0);	columnas.put('c',0);	columnas.put('d',0);	
		columnas.put('e',5);	columnas.put('f',0);	columnas.put('g',0);	columnas.put('h',0);	
		columnas.put('i',0);	columnas.put('j',0);	columnas.put('k',0);	columnas.put('l',0);	
		columnas.put('m',0);	columnas.put('n',0);	columnas.put('o',0);	columnas.put('p',0);	
		columnas.put('q',0);	columnas.put('r',0);	columnas.put('s',0);	columnas.put('t',0);	
		columnas.put('u',0);	columnas.put('v',0);	columnas.put('w',0);	columnas.put('x',0);	
		columnas.put('y',0);	columnas.put('z',0);	columnas.put('A',0);	columnas.put('B',0);	
		columnas.put('C',0);	columnas.put('D',0);	columnas.put('E',4);	columnas.put('F',0);	
		columnas.put('G',0);	columnas.put('H',0);	columnas.put('I',0);	columnas.put('J',0);	
		columnas.put('K',0);	columnas.put('L',0);	columnas.put('M',0);	columnas.put('N',0);	
		columnas.put('O',0);	columnas.put('P',0);	columnas.put('Q',0);	columnas.put('R',0);	
		columnas.put('S',0);	columnas.put('T',0);	columnas.put('U',0);	columnas.put('V',0);	
		columnas.put('W',0);	columnas.put('X',0);	columnas.put('Y',0);	columnas.put('Z',0);	
		columnas.put('0',1);	columnas.put('1',1);	columnas.put('2',1);	columnas.put('3',1);	
		columnas.put('4',1);	columnas.put('5',1);	columnas.put('6',1);	columnas.put('7',1);	
		columnas.put('8',1);	columnas.put('9',1);	columnas.put('_',2);	columnas.put('.',3);	
		columnas.put('-',6);	columnas.put('+',7);	columnas.put('/',8);	columnas.put('*',9);	
		columnas.put(':',10);	columnas.put('>',11);	columnas.put('<',12);	columnas.put('=',13);	
		columnas.put(' ',14);	columnas.put('	',15);	columnas.put('[',16);	columnas.put(']',17);	
		columnas.put('(',18);	columnas.put(')',19);	columnas.put(',',20);	columnas.put(';',21);	
		columnas.put('\n',22);	columnas.put('%',23);   			
	}
	
	private int getColumna(char caracterLeido) {
		if (columnas.containsKey(caracterLeido))
			return (int) columnas.get(caracterLeido);
		return 24;
	}

	

	public TablaTokens getTablaTokens(){
		return tablaTokens;
	}
	
	public TablaSimbolos getTablaSimbolos() {
		return tablaSimbolos;
	}
	
	public Token getToken() {
		return token;
	}



	
	public int yylex() {
		
		int nroToken = -1;
		int estadoActual = 0;
		int numAscii = -1;

		StringBuilder cadena = new StringBuilder();
		while (estadoActual != F && estadoActual != E && indiceLectura<ascii.size()){ //llega a estado final
			numAscii = ascii.get(indiceLectura);
			if(numAscii != 13) {
				char caracterleido = (char) numAscii;
				indiceLectura++;
				int columna = getColumna(caracterleido);
				if (accionesSemanticas[estadoActual][columna] != null) 
					nroToken = accionesSemanticas[estadoActual][columna].ejecutar(caracterleido,cadena);
				estadoActual = transicionEstados[estadoActual][columna];
			}
			else
				indiceLectura++;
		}
		token = new Token(nroToken,cadena.toString(),nroLinea);
		
		if(indiceLectura==ascii.size()) {
			if (porcentajeAbierto) {
				AnalizadorLexico.listaCorrectas.add("Linea " +nroLinea + " Cadena: " + cadena.toString());
				Error nuevoWarning = new Error("La cadena no fue cerrada correctamente",nroLinea,"","WARNING");//Chequear
				listaWarning.add(nuevoWarning);
				tablaSimbolos.agregar(cadena.toString(), new Registro("Cadena"));
			}
				
			else
				if (comentarioAbierto)
					listaErrores.add(new Error ("El programa termina con un comentario abierto",nroLinea,"","Error"));
			return 0;
			}
		return nroToken;	
	}

}
