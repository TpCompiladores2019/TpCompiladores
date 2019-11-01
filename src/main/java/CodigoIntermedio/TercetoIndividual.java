package CodigoIntermedio;

import Lexico.Token;

public class TercetoIndividual {
	
	private Token token;
	private boolean esTerceto;

	public TercetoIndividual(Token t) {
		setToken(t);
	}

	public boolean isEsTerceto() {
		return esTerceto;
	}

	public void setEsTerceto(boolean esTerceto) {
		this.esTerceto = esTerceto;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	
}
