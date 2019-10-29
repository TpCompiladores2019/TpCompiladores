package Lexico;

public class Registro {
	private String tipo;
	private int cantRef;
	private String uso;
	private boolean declarada;
	private int tamanioColeccion;
	
	public Registro (String tipo) {
		this.tipo = tipo;
		this.cantRef=1;
	}
	
	public Registro (String tipo, String uso) {
		this.tipo = tipo;
		this.uso = uso;
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
	
	public void setUso(String uso) {
		this.uso = uso;
	}
	
	public String getUso() {
		return uso;
	}
	
	public boolean getDeclarada() {
		return declarada;
	}
	
	public void setDeclarada(boolean declarada) {
		this.declarada=declarada;
	}

	public int getTamanioColeccion() {
		return tamanioColeccion;
	}

	public void setTamanioColeccion(int tamanioColeccion) {
		this.tamanioColeccion = tamanioColeccion;
	}
}
