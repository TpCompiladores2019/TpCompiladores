package CodigoIntermedio;

import Lexico.Token;

public class TercetoColeccionIzq extends TercetoAbstracto{
	private int tamanio;
	
	
	public TercetoColeccionIzq(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}
	
	public void setTamanio(int tamanio) {
		this.tamanio=tamanio;
	}

	@Override
	public String getCodigoAssembler() {
		Token tercetoIzq = listTerceto.get(1);
		Token tercetoDer = listTerceto.get(2);
		String lexemaIzq = tercetoIzq.getLexema();
		String lexemaDer = tercetoDer.getLexema();
		String offset = "OFFSET";
		StringBuilder assembler = new StringBuilder();
		if (tercetoIzq.getTipo().equals("int")) {
			assembler.append("MOV CX, _" + lexemaDer + System.lineSeparator());
			assembler.append("CMP " + "CX"+ ", " + 0 + System.lineSeparator());
			assembler.append("JL LabelSubIndices" + System.lineSeparator());
			assembler.append("CMP CX," + tamanio + System.lineSeparator());
			assembler.append("JGE LabelSubIndices" + System.lineSeparator());
			assembler.append("MOV esi, " + offset + " " + "_" + lexemaIzq +  System.lineSeparator());
			assembler.append("MOV AX, _" + lexemaDer + System.lineSeparator());
			assembler.append("ADD AX,1" + System.lineSeparator());
			assembler.append("MOVZX EAX,AX" + System.lineSeparator());
			assembler.append("IMUL EAX, 2" + System.lineSeparator());
			assembler.append("ADD esi,EAX" + System.lineSeparator()); 
		}
		else {
			assembler.append("MOV CX, _" + lexemaDer + System.lineSeparator());
			assembler.append("CMP " + "CX"+ ", " + 0 + System.lineSeparator());
			assembler.append("JL LabelSubIndices" + System.lineSeparator());
			assembler.append("CMP CX," + tamanio + System.lineSeparator());
			assembler.append("JGE LabelSubIndices" + System.lineSeparator());
			assembler.append("MOV esi, " + offset + " " + "_" + lexemaIzq +  System.lineSeparator());
			assembler.append("MOV AX, _" + lexemaDer + System.lineSeparator());
			assembler.append("ADD AX, 1" + System.lineSeparator());
			assembler.append("MOVZX EAX,AX" + System.lineSeparator());
			assembler.append("IMUL EAX, 8" + System.lineSeparator());
			assembler.append("ADD esi,EAX" + System.lineSeparator());
		}
		return assembler.toString();
		
		
		
		

	}

}
