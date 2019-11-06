package CodigoIntermedio;

import java.util.ArrayList;

public class TercetoS {
	private ArrayList<TercetoIndividual> listTerceto;
	private int numTerceto;
	
	
	public TercetoS(TercetoIndividual t1, TercetoIndividual t2, TercetoIndividual t3,int numTerceto) {
		listTerceto = new ArrayList<TercetoIndividual>();
		System.out.println("Terceto " + numTerceto + ": " + t1.getToken().getLexema() + ", " + t2.getToken().getLexema() + ", " + t3.getToken().getLexema());
		listTerceto.add(t1);
		listTerceto.add(t2);
		listTerceto.add(t3);
		this.numTerceto=numTerceto;
	}

//IDEA LARDA	
	public TercetoS(String s1, String s2, String s3) {
		
	}
	
	public int getNumTerceto() {
		return numTerceto;
	}
 }
