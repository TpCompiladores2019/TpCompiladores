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
					code = code + "Label" + listLabel.remove(listLabel.size()-1)+ ":" + System.lineSeparator();
	            }
			}
		return code;
	}
		
	
	public void getTercetoOptimos() {
		int idTercetoActual;
		int idTercetoCambio = -1;
		boolean llegoHastaAsig ;
		for (int i = 0; i< listTercetos.size();i++) {
			llegoHastaAsig = false;
			//if (!listTercetos.get(i).listTerceto.get(1).getLexema().contains("@") && !listTercetos.get(i).listTerceto.get(2).getLexema().contains("@")) {
			if (!listTercetos.get(i).listTerceto.get(0).getLexema().equals(":=") ) {
				
				idTercetoActual = listTercetos.get(i).getNumTerceto();
				//System.out.println(listTercetos.get(i).listTerceto.get(0).getLexema() + " " + idTercetoActual  + " " + idTercetoCambio);
				for (int j = i+1; j< listTercetos.size()&& !llegoHastaAsig;j++ ) {
					if (!listTercetos.get(j).listTerceto.get(0).getLexema().equals(":=") ) {
					//if (!listTercetos.get(j).listTerceto.get(1).getLexema().contains("@") && !listTercetos.get(j).listTerceto.get(2).getLexema().contains("@")) 
						if(listTercetos.get(j).listTerceto.get(0).getLexema().equals(listTercetos.get(i).listTerceto.get(0).getLexema()))
							if(listTercetos.get(j).listTerceto.get(1).getLexema().equals(listTercetos.get(i).listTerceto.get(1).getLexema()))
								if(listTercetos.get(j).listTerceto.get(2).getLexema().equals(listTercetos.get(i).listTerceto.get(2).getLexema())) {
									idTercetoCambio = listTercetos.get(j).getNumTerceto();
									//System.out.println(listTercetos.get(j).listTerceto.get(1).getLexema() + " " + idTercetoActual + " " +idTercetoCambio) ;
								}
					if(listTercetos.get(j).listTerceto.get(1).getLexema().equals("@"+idTercetoCambio)) {
						listTercetos.get(j).setTerceto(new Token("@"+idTercetoActual),1);
					}
					else {
						System.out.println("lexema" + listTercetos.get(j).listTerceto.get(2).getLexema());
						System.out.println("idTercetoCambio" + idTercetoCambio);
						if(listTercetos.get(j).listTerceto.get(2).getLexema().equals("@"+idTercetoCambio)) {
							
							System.out.println("tercetoooooooactual" + idTercetoActual);
							listTercetos.get(j).setTerceto(new Token("@"+idTercetoActual),2);
						}
					}
				}
					else
						llegoHastaAsig=true;
				}
				
				
			}
			else {
				idTercetoActual = -1;
			 	idTercetoCambio = -1;
			}
		}
	}
	
	
	
	
}
