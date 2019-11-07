package CodigoIntermedio;

import java.util.ArrayList;
import java.util.Stack;

import Lexico.Token;

public class AnalizadorTercetos {

	
	private ArrayList<TercetoS> listTercetos; 
	private Stack<Integer> pila;
	
	public AnalizadorTercetos() {
		this.listTercetos = new ArrayList<TercetoS>();
		pila = new Stack<Integer>();
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
		for (TercetoS tercetoS : listTercetos) {
			System.out.println(tercetoS.getNumTerceto());
		}
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
		TercetoS nuevo = listTercetos.get(numTerceto);
		Token t = new Token("@"+String.valueOf(listTercetos.size()+1));
		TercetoIndividual add = new TercetoIndividual(t);
        if (nuevo.getTerceto(1) == null)
            nuevo.setTerceto(add,1);
        else
            nuevo.setTerceto(add,1);
        listTercetos.set(numTerceto, nuevo);
	}
	
	
	
/*	public void crearTerceto(String s1, String s2, String s3) {
		if(s2.contains("@")) {

			s2=listTercetos.get(Integer.valueOf( s2.substring(1, s2.length())));
			
		}
		TercetoS t = new TercetoS(s1, s2, s3);
		
	}*/
}
