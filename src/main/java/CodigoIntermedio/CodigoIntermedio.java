package CodigoIntermedio;

import java.util.ArrayList;
import java.util.List;

import Lexico.Token;

public class CodigoIntermedio {

	private List<Terceto> listTercetos = new ArrayList<Terceto>();
	
	private Terceto asignacion;
	private Terceto expresion;
	private Terceto termino;
	private Terceto factor;
	
	public void imprimir() {
		for (Terceto terceto : listTercetos) {
			System.out.println(terceto.getToken());
			System.out.println(terceto.getIzq());
			System.out.println(terceto.getDer());
		}
	}
	
	public void asignarFactor(Token token) {
		factor.setToken(token);
	}
	
	public void asignarTermino() {
		termino = factor;
	}
	
	public void crearTercetoTermino(Token token) {
		termino = new Terceto(token,termino,factor);
		listTercetos.add(termino);
	}
	
	public void asignarExpresion() {
		expresion = termino;
	}
	
	public void crearTercetoExpresion(Token token) {
		expresion = new Terceto (token,expresion,termino);
		listTercetos.add(expresion);
	}
	public void crearTercetoAsignacion(String operador, Token id) {
		Token token = new Token(0,operador,0);
		asignacion = new Terceto (token, new Terceto(id,null,null),expresion);
		listTercetos.add(asignacion);
	}
	
	
	
}
