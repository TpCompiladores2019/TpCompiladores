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
	
    public String imprimirTerceto() {
        String cadena="\nTercetos: \n";
        for (TercetoAbstracto t: listTercetos ){
            cadena= cadena + t.imprimirTercetoI() + System.lineSeparator();
        }
        return cadena;


    }
	
	public String getNumeroTerceto() {
		return String.valueOf(listTercetos.size());
	}
	
	
	public void apilar() {
		pila.push(getSizeTerceto());
	}
	
	public void desapilar() {
		int numTerceto = pila.pop();
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
		nuevo.setTerceto(t, 1);		
	}
	
	public void agregarError(String mensaje,int nroLinea) {
		listErroresSemanticos.add(new Error(mensaje, nroLinea, "", "ERROR"));
	}

	public void borrarLabelPendientes() {
		int label = listLabel.get(listLabel.size()-1);
		listLabel.remove(listLabel.size()-1);
	}
	
	public String imprimirErroresSemanticos() {
		String errores = System.lineSeparator() + "Errores Semanticos Detectados" + System.lineSeparator();
		for (Error error : listErroresSemanticos) {
			errores = errores + error.toString() + System.lineSeparator();
		}
		return errores;
	}

	
	public String getCodeString() {
		String code = "";
		int tercetoActual =1;

		for (int i = 0; i< listTercetos.size();i++) {
			code = code + listTercetos.get(i).getCodigoAssembler() + System.lineSeparator();
			tercetoActual++;
			if ( (!listLabel.isEmpty()) && ( tercetoActual == listLabel.get(listLabel.size()-1))){
					code = code + "Label" + listLabel.get(listLabel.size()-1)+ ":" + System.lineSeparator();
	                borrarLabelPendientes();
	            }
			}
            
	
		return code;
	}
	
	
	
}
