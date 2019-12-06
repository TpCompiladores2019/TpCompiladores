package CodigoIntermedio;

import Lexico.Token;

public class TercetoAsignacionRowing extends TercetoAbstracto {
	private int tamanio;

	public TercetoAsignacionRowing(Token t1, Token t2, Token t3, int numTerceto) {
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
		
		StringBuilder assembler = new StringBuilder();
		if (!lexemaDer.contains("@")){
			if(tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV esi, OFFSET _" + lexemaIzq + System.lineSeparator());
				assembler.append("MOV AX, _" + lexemaDer.replace('-', '@') + System.lineSeparator());
				assembler.append("XOR ECX,ECX" + System.lineSeparator());
				assembler.append("L" + getNumTerceto() + ": " + System.lineSeparator());
				assembler.append("ADD ECX,1" + System.lineSeparator());
				assembler.append("MOV [esi + ECX * 2], AX " + System.lineSeparator());
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + System.lineSeparator());
				assembler.append("jne L" + getNumTerceto() + System.lineSeparator());
			}
			else {
				assembler.append("XOR ECX,ECX" + System.lineSeparator());
				assembler.append("FLD _" + lexemaDer.replace("-", "@").replace("+", "@").replace(".", "@") + System.lineSeparator());
				assembler.append("L" + getNumTerceto() + ": " + System.lineSeparator());
				assembler.append("ADD ECX,1" + System.lineSeparator());
				assembler.append("FST DWORD PTR _" + lexemaIzq + "[ECX*8]"  + System.lineSeparator());
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + System.lineSeparator());
				assembler.append("jne L" + getNumTerceto() + System.lineSeparator());
		}
		}
		else {
			if(tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV esi, OFFSET _" + lexemaIzq + System.lineSeparator());
				assembler.append("MOV AX, auxiliar" + lexemaDer + System.lineSeparator());
				assembler.append("XOR ECX,ECX" + System.lineSeparator());
				assembler.append("L" + getNumTerceto() + ": " + System.lineSeparator());
				assembler.append("MOV [esi + ECX * 4], AX " + System.lineSeparator());
				assembler.append("ADD ECX,1" + System.lineSeparator());
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + System.lineSeparator());
				assembler.append("jne L" + getNumTerceto() + System.lineSeparator());
			}			
			else {
				assembler.append("XOR ECX,ECX" + System.lineSeparator());
				assembler.append("FLD auxiliar" + lexemaDer  + System.lineSeparator());
				assembler.append("L" + getNumTerceto() + ": " + System.lineSeparator());
				assembler.append("FST DWORD PTR _" + lexemaIzq + "[ECX*8]"  + System.lineSeparator());
				assembler.append("ADD ECX,1" + System.lineSeparator());
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + System.lineSeparator());
				assembler.append("jne L" + getNumTerceto() + System.lineSeparator());
			}
		}
		return assembler.toString();
	}

}
