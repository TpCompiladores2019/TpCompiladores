package CodigoIntermedio;

import Lexico.Token;

public class TercetoAsignacion extends TercetoAbstracto{

	public TercetoAsignacion(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCodigoAssembler() {

        StringBuilder assembler = new StringBuilder();
        String lexemaIzq = listTerceto.get(1).getLexema();
        String lexemaDer=listTerceto.get(2).getLexema();
            //(ASIG, variable, variable)
            if (!lexemaIzq.contains("@") && !lexemaDer.contains("@")){
                if (listTerceto.get(2).getTipo().equals("float")){
                    assembler.append("FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
                    assembler.append("FST _"+ lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') +'\n');
                }
                else{
                    assembler.append("MOV " + "AX" + ", _" + lexemaDer.replace('-', '@') + '\n');
                    assembler.append("MOV _" + lexemaIzq.replace('-', '@') + ", " + "AX" + '\n');
                }
            }
            else
                if (!lexemaIzq.contains("@") && lexemaDer.contains("@"))//(ASIG, variable, terceto)
	                if (listTerceto.get(2).getTipo().equals("float")){
	                    assembler.append("FLD auxiliar" + lexemaDer + '\n');
	                    assembler.append("FST _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
	                }
	                else {
	                    assembler.append("MOV " + "AX" + ", auxiliar" + lexemaDer + '\n');
	                    assembler.append("MOV _" + lexemaIzq.replace('-', '@') + ", " + "AX" + '\n');
	                }
                else//(ASIGN,TERCETO,TERCETO)
                	if (lexemaIzq.contains("@") && lexemaDer.contains("@")) {
                		if (listTerceto.get(1).getTipo().equals("int")){
	                		assembler.append("MOV AX, auxiliar"+lexemaDer + '\n');
	                		assembler.append("MOV [esi],AX" +'\n');
                		}
                		else {
                			assembler.append("FLD auxiliar" + lexemaDer + '\n');
                			assembler.append("FST DWORD PTR [esi]"+ '\n');
                		}
                	}
                	else ////(ASIGN,TERCETO,variable)
                		if(lexemaIzq.contains("@") && !lexemaDer.contains("@")) {
                			if (listTerceto.get(1).getTipo().equals("int")){
	                			assembler.append("MOV AX, _"+lexemaDer.replace("-", "@") + '\n');
	                    		assembler.append("MOV [esi],AX" +'\n');
                			}
                			else {
                				assembler.append("FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
                                assembler.append("FST DWORD PTR [esi]"+ '\n');
                			}
                		}
            
        return assembler.toString();
    }

}
