package CodigoIntermedio;

import Lexico.Token;

public class TercetoColeccionDer extends TercetoAbstracto{
	private int tamanio;
	
	public TercetoColeccionDer(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
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
		String offset = listTerceto.get(0).getLexema();
		StringBuilder assembler = new StringBuilder();
		if (tercetoIzq.getTipo().equals("int")) {
			assembler.append("MOV AX, _" + lexemaDer + System.lineSeparator());
			assembler.append("CMP " + "AX"+ ", " + 0 + System.lineSeparator());
			assembler.append("JL LabelSubIndices" + System.lineSeparator());
			assembler.append("CMP AX," + tamanio + System.lineSeparator());
			assembler.append("JGE LabelSubIndices" + System.lineSeparator());
			assembler.append("MOV ECX, " + offset + " " + "_" + lexemaIzq +  System.lineSeparator());
			assembler.append("ADD AX,1" + System.lineSeparator());
			assembler.append("MOVZX EAX,AX" + System.lineSeparator());
			assembler.append("MOV AX, [ECX + EAX*2]" + System.lineSeparator());
			assembler.append("MOV auxiliar@" + getNumTerceto()+", AX" + System.lineSeparator()); 
		}
		else {
			assembler.append("MOV AX, _" + lexemaDer + System.lineSeparator());
			assembler.append("CMP " + "AX"+ ", " + 0 + System.lineSeparator());
			assembler.append("JL LabelSubIndices" + System.lineSeparator());
			assembler.append("CMP AX," + tamanio + System.lineSeparator());
			assembler.append("JGE LabelSubIndices" + System.lineSeparator());
			assembler.append("ADD AX,1" + System.lineSeparator());
			assembler.append("MOVZX EAX,AX" + System.lineSeparator());
			assembler.append("MOV ECX, " + offset + " " + "_" + lexemaIzq +  System.lineSeparator());
			assembler.append("FLD DWORD PTR [ECX + EAX*8] " + System.lineSeparator()); 
			assembler.append("FSTP auxiliar@" + getNumTerceto()+ System.lineSeparator()); 
		}
		return assembler.toString();   
	}

}
