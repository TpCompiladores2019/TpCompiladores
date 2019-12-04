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
		
		String assembler = "";
		if (!lexemaDer.contains("@")){
			if(tercetoIzq.getTipo().equals("int")) {
				assembler = assembler + "MOV esi, OFFSET _" + lexemaIzq + '\n';
				assembler = assembler + "MOV EAX, _" + lexemaDer.replace('-', '@') + '\n';
				assembler = assembler + "XOR ECX,ECX" + '\n';
				assembler = assembler + "L" + getNumTerceto() + ": " + '\n';
				assembler = assembler + "MOV [esi + ECX * 4], EAX " + '\n';
				assembler = assembler + "ADD ECX,1" + '\n';
				assembler = assembler + "cmp ECX, LENGTHOF _" + lexemaIzq + '\n';
				assembler = assembler + "jne L" + getNumTerceto() + '\n';
			}
			else {
				assembler = assembler + "XOR ECX,ECX" + '\n';
				assembler = assembler + "FLD _" + lexemaDer.replace("-", "@").replace("+", "@").replace(".", "@") + '\n';
				assembler = assembler + "L" + getNumTerceto() + ": " + '\n';
				assembler = assembler + "FST DWORD PTR _" + lexemaIzq + "[ECX*8]"  + '\n';
				assembler = assembler + "ADD ECX,1" + '\n';
				assembler = assembler + "cmp ECX, LENGTHOF _" + lexemaIzq + '\n';
				assembler = assembler + "jne L" + getNumTerceto() + '\n';
		}
		}
		else {
			if(tercetoIzq.getTipo().equals("int")) {
				assembler = assembler + "MOV esi, OFFSET _" + lexemaIzq + '\n';
				assembler = assembler + "MOV EAX, auxiliar" + lexemaDer + '\n';
				assembler = assembler + "XOR ECX,ECX" + '\n';
				assembler = assembler + "L" + getNumTerceto() + ": " + '\n';
				assembler = assembler + "MOV [esi + ECX * 4], EAX " + '\n';
				assembler = assembler + "ADD ECX,1" + '\n';
				assembler = assembler + "cmp ECX, LENGTHOF _" + lexemaIzq + '\n';
				assembler = assembler + "jne L" + getNumTerceto() + '\n';
			}			
			else {
				assembler = assembler + "XOR ECX,ECX" + '\n';
				assembler = assembler + "FLD auxiliar" + lexemaDer  + '\n';
				assembler = assembler + "L" + getNumTerceto() + ": " + '\n';
				assembler = assembler + "FST DWORD PTR _" + lexemaIzq + "[ECX*8]"  + '\n';
				assembler = assembler + "ADD ECX,1" + '\n';
				assembler = assembler + "cmp ECX, LENGTHOF _" + lexemaIzq + '\n';
				assembler = assembler + "jne L" + getNumTerceto() + '\n';
			}
		}
		return assembler;
	}

}
