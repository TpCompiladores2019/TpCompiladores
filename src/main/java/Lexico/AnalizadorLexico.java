package Lexico;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import AccionesSemanticas.ASFinalOAC;
import AccionesSemanticas.ASAgregar;
import AccionesSemanticas.ASAumentarNumLinea;
import AccionesSemanticas.ASFinalCadena;
import AccionesSemanticas.ASFinalID;
import AccionesSemanticas.ASFinalEntero;
import AccionesSemanticas.ASFinalFloat;
import AccionesSemanticas.ASConsumirComentario;
import AccionesSemanticas.ASErrorCadenaMultilinea;
import AccionesSemanticas.ASErrorCaracterFaltante;
import AccionesSemanticas.ASFinal;
import AccionesSemanticas.ASError;
import AccionesSemanticas.IAccionSemantica;


public class AnalizadorLexico {
	
	private final int F = 400;
	private final int E = -1;
	
	private Token token;
										//	L  D  _  .  E  e  -  +  /  *  :  > <  =  ' ' tb [  ] (   )  ,  ; \n  %  otros
	private int [][] transicionEstados = {{ 1, 2, E, 3, 1, 1, F, F,12, F, 8, 9,11,10, 0, 0, F, F, F, F, F, F, 0,15, E}, //0
										  { 1, 1, 1, F, 1, 1, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //1
										  { F, 2, F, 4, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, E}, //2
										  { E, 4, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E}, //3
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
										  {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15, 0, F, E}, //15
								};

	private IAccionSemantica AS1 = new ASAgregar(); 
	private IAccionSemantica AS2 = new ASFinalID();
	private IAccionSemantica AS3 = new ASFinalEntero();
	private IAccionSemantica AS4 = new ASFinalFloat();
	private IAccionSemantica AS5 = null;
	private IAccionSemantica AS6 = new ASFinalCadena();
	private IAccionSemantica AS7 = new ASAumentarNumLinea();
	private IAccionSemantica AS8 = new ASFinalOAC(); //mirar nombre
	private IAccionSemantica AS9 = new ASError();
	private IAccionSemantica AS10 = new ASConsumirComentario();
	private IAccionSemantica AS11 = new ASErrorCaracterFaltante();
	private IAccionSemantica AS12 = new ASErrorCadenaMultilinea();
	private IAccionSemantica AS13 = new ASFinal();
	
	private IAccionSemantica [][] accionesSemanticas={
//			   0   1    2    3    4    5    6    7    8    9   10   11    12   13  14   15   16   17   18   19   20   21    22  23	24  
//			   L   D    _    .    E    e    -    +    /    *    :    >    <    =   ' '  tb    [    ]    (    )    ,    ;    \n   %  otros
			{AS1 ,AS1 ,AS9 ,AS1 ,AS1 ,AS1 ,AS8 ,AS8 ,AS1 ,AS8 ,AS1 ,AS1 ,AS1 ,AS1 ,AS5 ,AS5 ,AS8 ,AS8 ,AS8 ,AS8 ,AS8 ,AS8 ,AS7 ,AS5, AS9}, //0
		    {AS1 ,AS1 ,AS1 ,AS2 ,AS1 ,AS1 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2, AS9}, //1
			{AS3 ,AS1 ,AS3 ,AS1 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3, AS9}, //2
			{AS11,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS9}, //3
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
			{AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS7 ,AS5, AS5}, //14
			{AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS12 ,AS6, AS9},//15 
			};
		
		
		public static int indiceLectura =0;	
		public static int nroLinea = 1;
		private boolean porcentaje=false;


		public static List <Error>listaErrores = new ArrayList<Error>();
		public static List <Error>listaWarning = new ArrayList<Error>();
		public static List <String> listaCorrectas = new ArrayList<String>();
		
		private Hashtable<Character,Integer> columnas=new Hashtable<Character,Integer>();
	
		private TablaSimbolos tablaSimbolos;
		private TablaTokens tablaTokens;
		
		private FileReader fr;
		private ArrayList<Integer> ascii = new ArrayList<Integer>();
	
	
	public AnalizadorLexico(TablaSimbolos tablaSimbolos, TablaTokens tablaTokens,File ruta) throws IOException {
		this.tablaSimbolos = tablaSimbolos;
		this.tablaTokens = tablaTokens;
		tablaTokens.CompletarTabla();
		inicializarColumnas();
		fr = new FileReader(ruta);
		int numAscii;
		while ((numAscii=fr.read())!=-1) {
			ascii.add(numAscii);
		}
		ascii.add(32);
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

	public void checkPorcentaje(char c) {
		if (c=='%') {
			porcentaje=!porcentaje;
		}
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
				checkPorcentaje(caracterleido);
				indiceLectura++;
				int columna = getColumna(caracterleido);
				if (accionesSemanticas[estadoActual][columna] != null) 
					nroToken = accionesSemanticas[estadoActual][columna].ejecutar(caracterleido,cadena,tablaTokens,tablaSimbolos);
				estadoActual = transicionEstados[estadoActual][columna];
			}
			else
				indiceLectura++;
		}
		token = new Token(nroToken,cadena.toString(),nroLinea);
		if(indiceLectura==ascii.size()) {
			if (porcentaje) 
				listaErrores.add(new Error("No cierra cadena", nroLinea, "", "ERROR"));
			return 0;
			}
		return nroToken;	
	}

}
