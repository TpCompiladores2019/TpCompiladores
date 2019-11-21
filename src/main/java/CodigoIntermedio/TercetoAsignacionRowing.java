package CodigoIntermedio;

import Lexico.Token;

public class TercetoAsignacionRowing extends TercetoAbstracto {
	int tamanio;
	public TercetoAsignacionRowing(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}
	
	public void setTamanio(int tamanio) {
		this.tamanio=tamanio;
	}
	
	@Override
	public String getCodigoAssembler() {
		String lexemaIzq = listTerceto.get(1).getLexema();
		String lexemaDer = listTerceto.get(2).getLexema();
		String assembler = "";
		if (!lexemaIzq.contains("@")) { //rowing
			assembler = assembler + "MOV esi, OFFSET _" + lexemaIzq + '\n';
			assembler = assembler + "MOV AX, _" + lexemaDer + '\n';
			for (int i = 0; i < tamanio; i++) {
				assembler = assembler + "ADD esi, " + i*2 + '\n'; 
				assembler = assembler + "MOV [esi], AX" + '\n';
			}
		}
		
	
		
		return assembler;
	}

}
