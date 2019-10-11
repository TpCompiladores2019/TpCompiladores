package Lexico;

public class Token {

	private String lexema;
	private int nroLinea;
	private int idToken;
	
	public Token(int idToken, String lexema, int nroLinea) {
		super();
		this.lexema = lexema;
		this.nroLinea = nroLinea;
		this.idToken = idToken;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public int getNroLinea() {
		return nroLinea;
	}

	public void setNroLinea(int nroLinea) {
		this.nroLinea = nroLinea;
	}

	public int getIdToken() {
		return idToken;
	}

	public void setIdToken(int idToken) {
		this.idToken = idToken;
	}
	
	
}
