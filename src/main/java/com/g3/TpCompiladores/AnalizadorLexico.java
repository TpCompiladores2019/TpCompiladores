package com.g3.TpCompiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class AnalizadorLexico {

	private int [][] transicionEstados = {{ 1, 2, 0, 3, 1, 1,-1,-1,12,-1, 8, 9,11,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,15}, //0
			  { 1, 1, 1,-1, 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //1
			  {-1, 2,-1, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //2
			  { 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //3
			  {-1, 4,-1,-1, 5, 5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //4
			  { 0, 7, 0, 0, 0, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //5
			  { 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //6
			  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //7
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //8
			  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //9
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //10
			  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //11
			  {-1,-1,-1,-1,-1,-1,-1,13,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //12
			  {13,13,13,13,13,13,13,14,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13}, //13
			  {13,13,13,13,13,13,13,14, 0,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13}, //14
			  {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15, 0,-1}  //15
	};
//			L  D  _  .  E  e  -  +  /  *  :  > <  =  ' ' tb [  ] (   )  ,  ; st  % 
	private int [][] accionesSemanticas ={{ 1, 1,-1, 1, 1, 1, 8, 8, 1, 8, 1, 1, 1, 1,-1,-1, 8, 8, 8, 8, 8, 8,-1, 1}, //0
			  { 2, 2, 2, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, //1
			  { 4, 2, 4, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, //2
			  {-1, 2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //3
			  { 5, 2, 5, 5, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //4
			  {-1, 2,-1,-1,-1,-1, 2, 2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //5
			  {-1, 2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //6
			  { 5, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //7
			  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //8
			  { 2, 2, 2, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, //9
			  {-1, 2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //10
			  { 3, 3, 3, 3, 2, 3, 3, 3, 3, 3, 3, 6, 3, 6, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, //11
			  { 3, 3, 3, 3, 3, 3, 3,-1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, //12
			  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //13
			  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //14
			  { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,-1, 7} //15
	};


	private InterfazAccionSemantica [][] matriz = { 
			
	};
	
	private int indiceLectura =0;
	private int nroLinea = 1;
	
	private Hashtable<Character,Integer> columnas=new Hashtable<Character,Integer>();
	public void rellenar() {
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
		columnas.put('%',23);
	}

	private TablaSimbolos tablaSimbolos = new TablaSimbolos();

	private TablaTokens tablaTokens = new TablaTokens();
	
	private BufferedReader bf;
	
	private StringBuilder codigoLeido = new StringBuilder();
	
	public void leerArchivo() throws IOException {
		FileReader fr = new FileReader("C:\\Users\\Juan\\Desktop\\datos.txt");
		bf = new BufferedReader(fr);
		String nuevaLinea = bf.readLine();
		while (nuevaLinea != null) {
			codigoLeido.append(nuevaLinea + "\n");
			nuevaLinea = bf.readLine();
			
		}
		codigoLeido.append("$");
	}
	
	public AnalizadorLexico() throws IOException {
		tablaTokens.CompletarTabla();
		rellenar();
		leerArchivo();
		System.out.println(codigoLeido);
	}
	
	
	public int getToken() throws IOException {
		
		int nroToken = -1;
		int estadoActual = 0;
		while (nroToken == -1) { //quiere decir que no encuentro token	
			Character caracterleido = codigoLeido.charAt(indiceLectura);
			indiceLectura++;		
			int columna = (int)columnas.get(caracterleido);
			System.out.println("Accion a ejecutar "+accionesSemanticas[estadoActual][columna]);
			//nroToken = accionesSemanticas[estadoActual][columna].ejecutar(caracterleido,cadena,tablaTokens);
			System.out.println("Estado actual " + estadoActual);
			estadoActual = transicionEstados[estadoActual][columna];
			System.out.println("Estado nuevo " + estadoActual);
		}
		return nroToken;	
	}
}
