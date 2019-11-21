package CodigoIntermedio;

import Lexico.Token;

public class TercetoColeccion extends TercetoAbstracto{
	private int tamanio;
	
	public TercetoColeccion(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}
	
	public void setTamanio(int tamanio) {
		this.tamanio=tamanio;
	}

	@Override
	public String getCodigoAssembler() {
		String lexemaIzq = listTerceto.get(1).getLexema();
		String lexemaDer = listTerceto.get(2).getLexema();
		String offset = listTerceto.get(0).getLexema();
		String assembler = "";
		assembler = assembler + "MOV CX, _" + lexemaDer + '\n';
		assembler = assembler + "CMP " + "CX"+ ", " + 0 + '\n';
		assembler = assembler + "JL LabelSubIndices" + '\n';
		assembler = assembler + "CMP CX," + tamanio + '\n';
		assembler = assembler + "JGE LabelSubIndices" + '\n';
		assembler = assembler + "MOV esi, " + offset + " " + "_" + lexemaIzq +  '\n';
		assembler = assembler + "MOV AX, _" + lexemaDer + '\n';
		assembler = assembler + "IMUL AX, 2" + '\n';
		assembler = assembler + "MOVZX EAX,AX" + '\n' ;
		assembler = assembler + "ADD esi,EAX" + '\n';
		assembler = assembler + "MOV AX, [esi]" + '\n';
		assembler = assembler + "MOV auxiliar@" + getNumTerceto()+", AX" + '\n'; 
		return assembler;
	}

}
