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
		String offset = listTerceto.get(0).getLexema();
		StringBuilder assembler = new StringBuilder();
		if (tercetoIzq.getTipo().equals("int")) {
			assembler.append("MOV ECX, _" + lexemaDer + '\n');
			assembler.append("CMP " + "ECX"+ ", " + 0 + '\n');
			assembler.append("JL LabelSubIndices" + '\n');
			assembler.append("CMP ECX," + tamanio + '\n');
			assembler.append("JGE LabelSubIndices" + '\n');
			assembler.append("MOV esi, " + offset + " " + "_" + lexemaIzq +  '\n');
			assembler.append("MOV EAX, _" + lexemaDer + '\n');
			assembler.append("IMUL EAX, 4" + '\n');
			assembler.append("ADD esi,EAX" + '\n'); 
		}
		else {
			assembler.append("MOV ECX, _" + lexemaDer + '\n');
			assembler.append("CMP " + "ECX"+ ", " + 0 + '\n');
			assembler.append("JL LabelSubIndices" + '\n');
			assembler.append("CMP ECX," + tamanio + '\n');
			assembler.append("JGE LabelSubIndices" + '\n');
			assembler.append("MOV esi, " + offset + " " + "_" + lexemaIzq +  '\n');
			assembler.append("MOV EAX, _" + lexemaDer + '\n');
			assembler.append("IMUL EAX, 8" + '\n');
			assembler.append("ADD esi,EAX" + '\n');
		}
		return assembler.toString();
		
		
		
		

	}

}
