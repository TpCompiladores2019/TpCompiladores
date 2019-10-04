package Lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import AccionesSemanticas.ASAgregaYPasa;
import AccionesSemanticas.ASAgregar;
import AccionesSemanticas.ASAumentarNumLinea;
import AccionesSemanticas.ASCadenaUnaLinea;
import AccionesSemanticas.ASChequearCadena;
import AccionesSemanticas.ASChequearEntero;
import AccionesSemanticas.ASChequearFloat;
import AccionesSemanticas.ASConsumirComentario;
import AccionesSemanticas.ASErrorCadenaMultilinea;
import AccionesSemanticas.ASErrorCaracterFaltante;
import AccionesSemanticas.ASErrorInicioCadena;
import AccionesSemanticas.IAccionSemantica;

public class AnalizadorLexico {
	private final int F = 400;
	
	
	
										//	L  D  _  .  E  e  -  +  /  *  :  > <  =  ' ' tb [  ] (   )  ,  ; \n  % 
	private int [][] transicionEstados = {{ 1, 2, 0, 3, 1, 1,-1,-1,12,-1, 8, 9,11,10, 0, 0,-1,-1,-1,-1,-1,-1, 0,15}, //0
										  { 1, 1, 1,-1, 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //1
										  {-1, 2,-1, 4,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //2
										  { 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //3
										  {-1, 4,-1,-1, 5, 5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //4
										  { 0, 7, 0, 0, 0, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //5
										  { 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //6
										  {-1, 7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //7
										  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //8
										  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //9
										  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //10
										  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //11
										  {-1,-1,-1,-1,-1,-1,-1,13,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //12
										  {13,13,13,13,13,13,13,14,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13}, //13 
										  {13,13,13,13,13,13,13,14, 0,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13}, //14
										  {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15, 0,-1}, //15
								};

	IAccionSemantica AS1 = new ASAgregar(); 
	IAccionSemantica AS2 = new ASChequearCadena();
	IAccionSemantica AS3 = new ASChequearEntero();
	IAccionSemantica AS4 = new ASChequearFloat();
	IAccionSemantica AS5 = null;
	IAccionSemantica AS6 = new ASCadenaUnaLinea();
	IAccionSemantica AS7 = new ASAumentarNumLinea();
	IAccionSemantica AS8 = new ASAgregaYPasa();
	IAccionSemantica AS9 = new ASErrorInicioCadena();
	IAccionSemantica AS10 = new ASConsumirComentario();
	IAccionSemantica AS11 = new ASErrorCaracterFaltante();
	IAccionSemantica AS12 = new ASErrorCadenaMultilinea();
	
	private IAccionSemantica [][] accionesSemanticas={
//		   0   1    2    3    4    5    6    7    8    9   10   11    12   13  14   15   16   17   18   19   20   21    22  23  
//		   L   D    _    .    E    e    -    +    /    *    :    >    <    =   ' '  tb    [    ]    (    )    ,    ;    \n   %  
		{AS1 ,AS1 ,AS9 ,AS1 ,AS1 ,AS1 ,AS8 ,AS8 ,AS1 ,AS8 ,AS1 ,AS1 ,AS1 ,AS1 ,AS5 ,AS5 ,AS8 ,AS8 ,AS8 ,AS8 ,AS8 ,AS8 ,AS7 ,AS5}, //0
	    {AS1 ,AS1 ,AS1 ,AS2 ,AS1 ,AS1 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2}, //1
		{AS3 ,AS1 ,AS3 ,AS1 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3 ,AS3}, //2
		{AS11,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11}, //3
		{AS4 ,AS1 ,AS4 ,AS4 ,AS1 ,AS1 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4}, //4
		{AS11,AS1 ,AS11,AS11,AS11,AS11,AS1 ,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11}, //5
		{AS11,AS1 ,AS11,AS11,AS11,AS11,AS1 ,AS1 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11}, //6
		{AS4 ,AS1 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4 ,AS4}, //7
		{AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS8 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11}, //8
		{AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS8 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2}, //9
		{AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS8 ,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11,AS11}, //10
		{AS2 ,AS2 ,AS1 ,AS2 ,AS1 ,AS1 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS8 ,AS2 ,AS8 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2}, //11
		{AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS10,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2 ,AS2}, //12
		{AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5}, //13
		{AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5 ,AS5}, //14
		{AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS1 ,AS12 ,AS6},//15 
		};
	

	public static int indiceLectura =0;	
	public static int nroLinea = 1;


	public static List <Error>listaErrores = new ArrayList<Error>();
	public static List <Error>listaWarning = new ArrayList<Error>();
		
	private Hashtable<Character,Integer> columnas=new Hashtable<Character,Integer>();
	
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

	private TablaSimbolos tablaSimbolos;

	private TablaTokens tablaTokens;
	
	private FileReader fr;
	private ArrayList<Integer> ascii = new ArrayList<Integer>();
	
	
	
	public AnalizadorLexico(TablaSimbolos tablaSimbolos, TablaTokens tablaTokens,String path) throws IOException {
		this.tablaSimbolos = tablaSimbolos;
		this.tablaTokens = tablaTokens;
		tablaTokens.CompletarTabla();
		inicializarColumnas();
		fr = new FileReader(path);
		int numAscii;
		while ((numAscii=fr.read())!=-1) {
			ascii.add(numAscii);
		}
		ascii.add(32);
	}
	
	public int yylex() throws IOException {
		
		int nroToken = -1;
		int estadoActual = 0;
		int numAscii = -1;

		StringBuilder cadena = new StringBuilder();
		while (estadoActual != -1 && indiceLectura<ascii.size()){ //llega a estado final
			numAscii = ascii.get(indiceLectura);
			if(numAscii != 13) {
				char caracterleido = (char) numAscii;
				System.out.println(caracterleido);
				indiceLectura++;
				int columna = (int)columnas.get(caracterleido);
				if (accionesSemanticas[estadoActual][columna] != null) 
					nroToken = accionesSemanticas[estadoActual][columna].ejecutar(caracterleido,cadena,tablaTokens,tablaSimbolos);
				estadoActual = transicionEstados[estadoActual][columna];
			}
			else
				indiceLectura++;
		}
		return nroToken;	
	}

}
