package Lexico;

public class Registro {
	private String tipo;
	private int cantRef;
	
	
	public Registro (String tipo) {
		this.tipo = tipo;
		this.cantRef=1;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public int getCantRef() {
		return cantRef;
	}


	public void setCantRef(int cantRef) {
		this.cantRef = cantRef;
	}
	
	public void incrementarRef() {
		this.cantRef++;
	}
	
	public void decrementarRef() {
		this.cantRef--;
	}
}
