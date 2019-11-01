package CodigoIntermedio;

import Lexico.Token;

public class Terceto {

	
	private Token token;
	private Terceto izq;
	private Terceto der;
	private int idTerceto;
	
	private static int contador =0;
	
	public Terceto(Token token, Terceto izq, Terceto der) {
		
		this.idTerceto = contador;
		contador++;
		this.token = token;
		this.izq = izq;
		this.der = der;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Terceto getIzq() {
		return izq;
	}

	public void setIzq(Terceto izq) {
		this.izq = izq;
	}

	public Terceto getDer() {
		return der;
	}

	public void setDer(Terceto der) {
		this.der = der;
	}
	
	
}
