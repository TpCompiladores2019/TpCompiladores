package CodigoIntermedio;

import java.util.ArrayList;
import java.util.Stack;

import Lexico.Token;
import Lexico.Error;

public class AnalizadorTercetos {

	private ArrayList<TercetoS> listTercetos; 
	private Stack<Integer> pila;
	
	private ArrayList<Error> listErroresSemanticos;	

	public AnalizadorTercetos() {
		this.listTercetos = new ArrayList<TercetoS>();
		pila = new Stack<Integer>();
		listErroresSemanticos = new ArrayList<Error>();
	}
	
	
	
	public void addTerceto(TercetoS t) {
		this.listTercetos.add(t);
	}
	
	public int getSizeTerceto() {
		return this.listTercetos.size();
	}
	
	public int getProximoTerceto() {
		return this.listTercetos.size()+1;
	}
	
    public void imprimirTerceto() {
        String cadena="Tercetos: \n";
        for (TercetoS t: listTercetos ){
            cadena= cadena + t.imprimirTercetoI() + '\n';
        }
        System.out.println(cadena);

    }
	
	public String getNumeroTerceto() {
		return String.valueOf(listTercetos.size());
	}
	
	
	public void apilar() {
		pila.push(getSizeTerceto());
		System.out.println(getSizeTerceto());
	}
	
	public void desapilar() {
		int numTerceto = pila.pop();
		System.out.println(numTerceto);
		numTerceto--;
		TercetoS nuevo = listTercetos.get(numTerceto);
		Token t = new Token("@"+String.valueOf(listTercetos.size()+1));
		TercetoIndividual add = new TercetoIndividual(t);
        if (nuevo.getTerceto(1) == null) 
        	nuevo.setTerceto(add,1);  
        else
            nuevo.setTerceto(add,2);
        listTercetos.set(numTerceto, nuevo);
	}
	
	public void desapilarControl() {
		int numTerceto = pila.pop();
		TercetoS nuevo = listTercetos.get(getSizeTerceto()-1);
		Token t = new Token("@" + String.valueOf(numTerceto+1));
		System.out.println(t.getLexema());
		TercetoIndividual add  = new TercetoIndividual(t);
		nuevo.setTerceto(add, 1);		
	}
	
	public void agregarError(String mensaje,int nroLinea) {
		listErroresSemanticos.add(new Error(mensaje, nroLinea, "", "ERROR"));
	}



	public void imprimirErroresSemanticos() {
		for (Error error : listErroresSemanticos) {
			System.out.println(error);
		}
		
	}
	
	
	
/*	public void crearTerceto(String s1, String s2, String s3) {
		if(s2.contains("@")) {

			s2=listTercetos.get(Integer.valueOf( s2.substring(1, s2.length())));
			
		}
		TercetoS t = new TercetoS(s1, s2, s3);
		
	}*/
}
