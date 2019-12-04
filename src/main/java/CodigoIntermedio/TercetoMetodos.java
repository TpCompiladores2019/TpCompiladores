package CodigoIntermedio;

import Lexico.Token;

public class TercetoMetodos extends TercetoAbstracto {
	int tamanio;
	public TercetoMetodos(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	public void setTamanio(int tamanio) {
		this.tamanio=tamanio;
	}
	@Override
	public String getCodigoAssembler() {
		StringBuilder assembler=new StringBuilder();
		Token tercetoIzq = listTerceto.get(1);
		Token tercetoDer = listTerceto.get(2);
		
		String lexemaIzq=tercetoIzq.getLexema();
		String lexemaDer=tercetoDer.getLexema();
		if (lexemaDer.contains("length")) {
				assembler.append("MOV auxiliar@" +getNumTerceto() + ","+tamanio + '\n');
		}
		if (lexemaDer.contains("first")) {
			if (tercetoIzq.getTipo().equals("int")) {
				assembler.append("MOV EAX, OFFSET _" + lexemaIzq + '\n');
				assembler.append("CALL FUNCION_FIRSTI" + '\n');
				assembler.append("MOV EAX,auxiliar" + '\n');
				assembler.append("MOV auxiliar@" + getNumTerceto() + ", EAX"  + '\n');
				}
			else {
				 assembler.append("MOV esi, OFFSET _"  + lexemaIzq + '\n');
				 assembler.append("FLD DWORD PTR [esi] " + '\n'); 
                 assembler.append("FST auxiliar@" + getNumTerceto() + '\n');
			}
				
		}
		if (lexemaDer.contains("last")) 
			if (tercetoIzq.getTipo().equals("int")) {
			assembler.append("MOV EAX, OFFSET _" + lexemaIzq + '\n');
			assembler.append("MOV ECX, " + (tamanio-1) + '\n');
			assembler.append("CALL FUNCION_LASTI" + '\n');
			assembler.append("MOV EAX,auxiliar" + '\n');
			assembler.append("MOV auxiliar@" + getNumTerceto() + ", EAX"  + '\n');
		}
		else {
			 assembler.append("MOV esi, OFFSET _"  + lexemaIzq + '\n');
			 assembler.append("ADD esi, "+ (tamanio-1)*8 + '\n');
			 assembler.append("FLD DWORD PTR [esi] " + '\n'); 
             assembler.append("FST auxiliar@" + getNumTerceto() + '\n');
			
		}
	return assembler.toString();
	}

}
