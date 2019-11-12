package CodigoIntermedio;

import java.util.ArrayList;

import Lexico.Token;

public abstract class TercetoAbstracto  {
	protected ArrayList<Token> listTerceto;
	private int numTerceto;
	
	
	public TercetoAbstracto(Token t1, Token t2, Token t3,int numTerceto) {
		listTerceto = new ArrayList<Token>();
		listTerceto.add(t1);
		listTerceto.add(t2);
		listTerceto.add(t3);
		this.numTerceto=numTerceto;
	}

	public Token getTerceto(int i) {
		return listTerceto.get(i);
	}
	
	public void setTerceto(Token t, int i) {
		listTerceto.set(i, t);
		
	}
	

	public String imprimirTercetoI() {
        String terceto = numTerceto + "  (";
        for (int i = 0 ; i< listTerceto.size(); i++){
            if (listTerceto.get(i) != null)
                terceto = terceto + listTerceto.get(i).getLexema();
            else
                terceto = terceto + " - ";
            if (i != listTerceto.size()-1)
                terceto = terceto + ", ";
            else
                terceto = terceto + ")";
        }
        return terceto;
	}
	
	public int getNumTerceto() {
		return numTerceto;
	}
	
	public abstract String getCodigoAssembler();
 }
