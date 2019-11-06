package CodigoIntermedio;

import java.util.ArrayList;

public class AnalizadorTercetos {

	
	private ArrayList<TercetoS> listTercetos; 

	public AnalizadorTercetos() {
		this.listTercetos = new ArrayList<TercetoS>();
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
	
/*	public void crearTerceto(String s1, String s2, String s3) {
		if(s2.contains("@")) {

			s2=listTercetos.get(Integer.valueOf( s2.substring(1, s2.length())));
			
		}
		TercetoS t = new TercetoS(s1, s2, s3);
		
	}*/
}
