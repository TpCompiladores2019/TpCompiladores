package CodigoIntermedio;

import java.util.ArrayList;

public class TercetoS {
	private ArrayList<TercetoIndividual> listTerceto;
	private int numTerceto;
	
	
	public TercetoS(TercetoIndividual t1, TercetoIndividual t2, TercetoIndividual t3,int numTerceto) {
		listTerceto = new ArrayList<TercetoIndividual>();
	/*	if (t2!= null && t3 !=null)
			System.out.println("Terceto " + numTerceto + ": " + t1.getToken().getLexema() + ", " + t2.getToken().getLexema() + ", " + t3.getToken().getLexema());
		else
			if (t2!=null)
				System.out.println("Terceto " + numTerceto + ": " + t1.getToken().getLexema() + ", " + t2.getToken().getLexema() + ", " ); 
			else
				System.out.println("Terceto " + numTerceto + ": " + t1.getToken().getLexema() + ", "); */
		listTerceto.add(t1);
		listTerceto.add(t2);
		listTerceto.add(t3);
		this.numTerceto=numTerceto;
	}

	public TercetoIndividual getTerceto(int i) {
		return listTerceto.get(i);
	}
	
	public void setTerceto(TercetoIndividual t, int i) {
		listTerceto.set(i, t);
		
	}
	

	public String imprimirTercetoI() {
        String terceto = numTerceto + "  (";
        for (int i = 0 ; i< listTerceto.size(); i++){
            if (listTerceto.get(i) != null)
                terceto = terceto + listTerceto.get(i).getToken().getLexema();
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
 }
