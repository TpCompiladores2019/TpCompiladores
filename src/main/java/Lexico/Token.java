package Lexico;

public class Token {

	private String lexema;
	private int nroLinea;
	private int idToken;
	private String tipo;
	private int cantRef;
	private String uso;
	private boolean declarada;
	private int tamanioColeccion;
	private String operador;
	private String idPrintCadena;
	
	public Token(int idToken, String lexema, int nroLinea) {
		super();
		this.lexema = lexema;
		this.nroLinea = nroLinea;
		this.idToken = idToken;
	}
	
	public Token(String tipo, int cantRef) {
		this.tipo = tipo;
		this.cantRef=cantRef;
	}
	
	public Token (String tipo, String uso) {
		this.tipo = tipo;
		this.uso = uso;
	}

	public Token(String lexema) {
		this.lexema = lexema;
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


	public String getUso() {
		return uso;
	}


	public void setUso(String uso) {
		this.uso = uso;
	}


	public boolean getDeclarada() {
		return declarada;
	}


	public void setDeclarada(boolean declarada) {
		this.declarada = declarada;
	}


	public int getTamanioColeccion() {
		return tamanioColeccion;
	}


	public void setTamanioColeccion(int tamanioColeccion) {
		this.tamanioColeccion = tamanioColeccion;
	}

	public void incrementarRef() {
		cantRef++;
	}

	public void decrementarRef() {
		cantRef--;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getIdPrintCadena() {
		return idPrintCadena;
	}

	public void setIdPrintCadena(String idPrintCadena) {
		this.idPrintCadena = idPrintCadena;
	}

	
}
