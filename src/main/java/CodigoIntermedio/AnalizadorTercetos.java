package CodigoIntermedio;

import java.util.ArrayList;
import java.util.Stack;

import Lexico.Token;
import Lexico.Error;

public class AnalizadorTercetos {

	private ArrayList<TercetoAbstracto> listTercetos; 
	private Stack<Integer> pila;
	
	private ArrayList<Error> listErroresSemanticos;	
	static ArrayList<Integer> listLabel;
	
	public AnalizadorTercetos() {
		this.listTercetos = new ArrayList<TercetoAbstracto>();
		pila = new Stack<Integer>();
		listErroresSemanticos = new ArrayList<Error>();
		listLabel = new ArrayList<Integer>();
	}
	
	
	public boolean estaVacia() {
		return listErroresSemanticos.isEmpty();
	}
	
	public void addTerceto(TercetoAbstracto t) {
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
        for (TercetoAbstracto t: listTercetos ){
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
		TercetoAbstracto nuevo = listTercetos.get(numTerceto);
		Token t = new Token("@"+String.valueOf(listTercetos.size()+1));
        if (nuevo.getTerceto(1) == null) 
        	nuevo.setTerceto(t,1);  
        else
            nuevo.setTerceto(t,2);
        listTercetos.set(numTerceto, nuevo);
	}
	
	public void desapilarControl() {
		int numTerceto = pila.pop();
		TercetoAbstracto nuevo = listTercetos.get(getSizeTerceto()-1);
		Token t = new Token("@" + String.valueOf(numTerceto+1));
		System.out.println(t.getLexema());
		nuevo.setTerceto(t, 1);		
	}
	
	public void agregarError(String mensaje,int nroLinea) {
		listErroresSemanticos.add(new Error(mensaje, nroLinea, "", "ERROR"));
	}

	public int borrarLabelPendientes() {
		int label = listLabel.get(listLabel.size()-1);
		listLabel.remove(listLabel.size()-1);
		
		return label;
	}
	
	public void imprimirErroresSemanticos() {
		for (Error error : listErroresSemanticos) {
			System.out.println(error);
		}
		
	}

	
	public String getCodeString() {
		String code = "";
		int num_terceto_actual =1;
		for (TercetoAbstracto t : listTercetos) {
			code = code + t.getCodigoAssembler() + '\n';
			//System.out.println(code);
			
			 num_terceto_actual++;
	            if ( (!listLabel.isEmpty()) && ( num_terceto_actual == listLabel.get(listLabel.size()-1) ) ){
	                code = code + "Label" + String.valueOf(listLabel.get(listLabel.size()-1))+ ":" + '\n';
	                borrarLabelPendientes();
	            }
		}
		
		return code;
	}
	
	
	
}
