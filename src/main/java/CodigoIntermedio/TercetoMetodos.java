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
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("CALL FUNCION_LENGTH"+ '\n');
				assembler.append("MOV auxiliar@" +getNumTerceto() + ", auxiliarInt" + '\n');
		}
		if (lexemaDer.contains("first")) {
			if (tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("CALL FUNCION_FIRSTINT" + '\n');
				assembler.append("MOV auxiliar@" + getNumTerceto() + ", auxiliarInt"  + '\n');
				}
			else {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("CALL FUNCION_FIRSTFLOAT" + '\n');
				assembler.append("MOV auxiliar@" + getNumTerceto() + ", auxiliarFloat"  + '\n');
			}
				
		}
		if (lexemaDer.contains("last")) 
			if (tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("CALL FUNCION_LASTINT" + '\n');
				assembler.append("MOV auxiliar@" + getNumTerceto() + ", auxiliarInt"  + '\n');
		}
		else {
			assembler.append("MOV ECX, OFFSET _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
			assembler.append("CALL FUNCION_LASTFLOAT" + '\n');
			assembler.append("MOV auxiliar@" + getNumTerceto() + ", auxiliarFloat"  + '\n');
			
		}
	return assembler.toString();
	}

}
