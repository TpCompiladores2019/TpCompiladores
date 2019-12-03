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
		String assembler="";
		Token tercetoIzq = listTerceto.get(1);
		Token tercetoDer = listTerceto.get(2);
		
		String lexemaIzq=listTerceto.get(1).getLexema();
		String lexemaDer=listTerceto.get(2).getLexema();
		if (lexemaDer.contains("length")) {
				assembler=assembler+ "MOV auxiliar@" +getNumTerceto() + ","+tamanio + '\n';
		}
		if (lexemaDer.contains("first")) {
			if (tercetoIzq.getTipo().equals("int")) {
				assembler = assembler + "MOV EAX, OFFSET _" + lexemaIzq + '\n';
				assembler = assembler + "CALL FUNCION_FIRSTI" + '\n';
				assembler = assembler + "MOV EAX,auxiliar" + '\n';
				assembler = assembler + "MOV auxiliar@" + getNumTerceto() + ", EAX"  + '\n';
				}
			else {
				 assembler = assembler + "MOV esi, OFFSET _"  + lexemaIzq + '\n';
				 assembler = assembler + "FLD DWORD PTR [esi] " + '\n'; 
                 assembler = assembler + "FST auxiliar@" + getNumTerceto() + '\n';
			}
				
		}
		if (lexemaDer.contains("last")) 
			if (tercetoIzq.getTipo().equals("int")) {
			assembler = assembler + "MOV EAX, OFFSET _" + lexemaIzq + '\n';
			assembler = assembler + "MOV ECX, " + (tamanio-1) + '\n';
			assembler = assembler + "CALL FUNCION_LASTI" + '\n';
			assembler = assembler + "MOV EAX,auxiliar" + '\n';
			assembler = assembler + "MOV auxiliar@" + getNumTerceto() + ", EAX"  + '\n';
		}
		else {
			 assembler = assembler + "MOV esi, OFFSET _"  + lexemaIzq + '\n';
			 assembler = assembler + "ADD esi, "+ (tamanio-1)*8 + '\n';
			 assembler = assembler + "FLD DWORD PTR [esi] " + '\n'; 
             assembler = assembler + "FST auxiliar@" + getNumTerceto() + '\n';
			
		}
	return assembler;
	}

}
