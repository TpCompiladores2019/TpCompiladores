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
			assembler.append("MOV EAX, _" + lexemaDer + '\n');
			assembler.append("CMP " + "EAX"+ ", " + 0 + '\n');
			assembler.append("JL LabelSubIndices" + '\n');
			assembler.append("CMP EAX," + tamanio + '\n');
			assembler.append("JGE LabelSubIndices" + '\n');
			assembler.append("MOV ECX, " + offset + " " + "_" + lexemaIzq +  '\n');
			assembler.append("MOV EAX, [ECX + EAX*4]" + '\n');
			assembler.append("MOV auxiliar@" + getNumTerceto()+", EAX" + '\n'); 
		}
		else {
			assembler.append("MOV EAX, _" + lexemaDer + '\n');
			assembler.append("CMP " + "EAX"+ ", " + 0 + '\n');
			assembler.append("JL LabelSubIndices" + '\n');
			assembler.append("CMP EAX," + tamanio + '\n');
			assembler.append("JGE LabelSubIndices" + '\n');
			assembler.append("MOV ECX, " + offset + " " + "_" + lexemaIzq +  '\n');
			assembler.append("FLD DWORD PTR [ECX + EAX*8] " + '\n'); 
			assembler.append("FST auxiliar@" + getNumTerceto()+ '\n'); 
		}
		return assembler.toString();
	}

}
