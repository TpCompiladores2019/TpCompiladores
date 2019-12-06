package CodigoIntermedio;

import Lexico.Token;

public class TercetoMetodos extends TercetoAbstracto {
	int tamanio;
	public TercetoMetodos(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	public void setTamanio(int tamanio) {
		this.tamanio=tamanio;
	}
	@Override
	public String getCodigoAssembler() {
		StringBuilder assembler=new StringBuilder();
		Token tercetoIzq = listTerceto.get(1);
		Token tercetoDer = listTerceto.get(2);
		
		String lexemaIzq=tercetoIzq.getLexema();
		String lexemaDer=tercetoDer.getLexema();
		if (lexemaDer.contains("length")) {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("CALL FUNCION_LENGTH"+ System.lineSeparator());
				assembler.append("MOV AX , auxiliarInt" + System.lineSeparator());
				assembler.append("MOV auxiliar@" +getNumTerceto() + ", AX" + System.lineSeparator());
		}
		if (lexemaDer.contains("first")) {
			if (tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("CALL FUNCION_FIRSTINT" + System.lineSeparator());
				assembler.append("MOV AX , auxiliarInt" + System.lineSeparator());
				assembler.append("MOV auxiliar@" + getNumTerceto() + ", AX"  + System.lineSeparator());
				}
			else {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("CALL FUNCION_FIRSTFLOAT" + System.lineSeparator());
				assembler.append("FLD auxiliarFloat"  + System.lineSeparator());
				assembler.append("FSTP auxiliar@" + getNumTerceto() + System.lineSeparator());
			}
				
		}
		if (lexemaDer.contains("last")) 
			if (tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("CALL FUNCION_LASTINT" + System.lineSeparator());
				assembler.append("MOV AX , auxiliarInt" + System.lineSeparator());
				assembler.append("MOV auxiliar@" + getNumTerceto() + ", AX"  + System.lineSeparator());
		}
		else {
			assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
			assembler.append("CALL FUNCION_LASTFLOAT" + System.lineSeparator());
			assembler.append("FLD auxiliarFloat"  + System.lineSeparator());
			assembler.append("FSTP auxiliar@" + getNumTerceto() + System.lineSeparator());
			
		}
	return assembler.toString();
	}

}
