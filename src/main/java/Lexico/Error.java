package Lexico;

public class Error {

	private String descripcion;
	private int nroLinea;
	private String caracterInvadido;
	private String tipoError;
	
	public Error(String descripcion, int nroLinea, String caracterInvadido, String tipoError) {
		this.descripcion = descripcion;
		this.nroLinea = nroLinea;
		this.caracterInvadido = caracterInvadido;
		this.tipoError = tipoError;
	}
	
	  
	public String toString() {
		return tipoError +"--> Linea "+ nroLinea +": "+ descripcion + caracterInvadido ;
	}
	
}
