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
		String lexemaIzq = listTerceto.get(1).getLexema();
		String lexemaDer = listTerceto.get(2).getLexema();
		String offset = listTerceto.get(0).getLexema();
		String assembler = "";
		if (tercetoIzq.getTipo().equals("int")) {
			assembler = assembler + "MOV ECX, _" + lexemaDer + '\n';
			assembler = assembler + "CMP " + "ECX"+ ", " + 0 + '\n';
			assembler = assembler + "JL LabelSubIndices" + '\n';
			assembler = assembler + "CMP ECX," + tamanio + '\n';
			assembler = assembler + "JGE LabelSubIndices" + '\n';
			assembler = assembler + "MOV esi, " + offset + " " + "_" + lexemaIzq +  '\n';
			assembler = assembler + "MOV EAX, _" + lexemaDer + '\n';
			assembler = assembler + "IMUL EAX, 4" + '\n';
			assembler = assembler + "ADD esi,EAX" + '\n'; 
		}
		else {
			assembler = assembler + "MOV ECX, _" + lexemaDer + '\n';
			assembler = assembler + "CMP " + "ECX"+ ", " + 0 + '\n';
			assembler = assembler + "JL LabelSubIndices" + '\n';
			assembler = assembler + "CMP ECX," + tamanio + '\n';
			assembler = assembler + "JGE LabelSubIndices" + '\n';
			assembler = assembler + "MOV esi, " + offset + " " + "_" + lexemaIzq +  '\n';
			assembler = assembler + "MOV EAX, _" + lexemaDer + '\n';
			assembler = assembler + "IMUL EAX, 8" + '\n';
			assembler = assembler + "ADD esi,EAX" + '\n';
		}
		return assembler;
		
		
		
		

	}

}
