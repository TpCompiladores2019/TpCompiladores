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
				assembler.append("MOV esi, OFFSET _" + lexemaIzq + '\n');
				assembler.append("MOV AX, _" + lexemaDer.replace('-', '@') + '\n');
				assembler.append("XOR ECX,ECX" + '\n');
				assembler.append("L" + getNumTerceto() + ": " + '\n');
				assembler.append("MOV [esi + ECX * 2], AX " + '\n');
				assembler.append("ADD ECX,1" + '\n');
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + '\n');
				assembler.append("jne L" + getNumTerceto() + '\n');
			}
			else {
				assembler.append("XOR ECX,ECX" + '\n');
				assembler.append("FLD _" + lexemaDer.replace("-", "@").replace("+", "@").replace(".", "@") + '\n');
				assembler.append("L" + getNumTerceto() + ": " + '\n');
				assembler.append("FST DWORD PTR _" + lexemaIzq + "[ECX*8]"  + '\n');
				assembler.append("ADD ECX,1" + '\n');
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + '\n');
				assembler.append("jne L" + getNumTerceto() + '\n');
		}
		}
		else {
			if(tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV esi, OFFSET _" + lexemaIzq + '\n');
				assembler.append("MOV AX, auxiliar" + lexemaDer + '\n');
				assembler.append("XOR ECX,ECX" + '\n');
				assembler.append("L" + getNumTerceto() + ": " + '\n');
				assembler.append("MOV [esi + ECX * 4], AX " + '\n');
				assembler.append("ADD ECX,1" + '\n');
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + '\n');
				assembler.append("jne L" + getNumTerceto() + '\n');
			}			
			else {
				assembler.append("XOR ECX,ECX" + '\n');
				assembler.append("FLD auxiliar" + lexemaDer  + '\n');
				assembler.append("L" + getNumTerceto() + ": " + '\n');
				assembler.append("FST DWORD PTR _" + lexemaIzq + "[ECX*8]"  + '\n');
				assembler.append("ADD ECX,1" + '\n');
				assembler.append("cmp ECX, LENGTHOF _" + lexemaIzq + '\n');
				assembler.append("jne L" + getNumTerceto() + '\n');
			}
		}
		return assembler.toString();
	}

}
