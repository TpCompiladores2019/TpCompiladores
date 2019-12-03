package CodigoIntermedio;

import Lexico.Token;

public class TercetoAsignacion extends TercetoAbstracto{

	public TercetoAsignacion(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCodigoAssembler() {

        String assembler = "";
        String lexemaIzq = listTerceto.get(1).getLexema();
        String lexemaDer=listTerceto.get(2).getLexema();
            //(ASIG, variable, variable)
            if (!lexemaIzq.contains("@") && !lexemaDer.contains("@")){
                if (listTerceto.get(2).getTipo().equals("float")){
                    assembler = assembler + "FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
                    assembler = assembler + "FST _"+ lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') +'\n';
                }
                else{
                    assembler = assembler + "MOV " + "EAX" + ", _" + lexemaDer.replace('-', '@') + '\n';
                    assembler = assembler + "MOV _" + lexemaIzq.replace('-', '@') + ", " + "EAX" + '\n';
                }
            }
            else
                if (!lexemaIzq.contains("@") && lexemaDer.contains("@"))//(ASIG, variable, terceto)
	                if (listTerceto.get(2).getTipo().equals("float")){
	                    assembler = assembler + "FLD auxiliar" + lexemaDer + '\n';
	                    assembler = assembler + "FST _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
	                }
	                else {
	                    assembler = assembler + "MOV " + "EAX" + ", auxiliar" + lexemaDer + '\n';
	                    assembler = assembler + "MOV _" + lexemaIzq.replace('-', '@') + ", " + "EAX" + '\n';
	                }
                else//(ASIGN,TERCETO,TERCETO)
                	if (lexemaIzq.contains("@") && lexemaDer.contains("@")) {
                		if (listTerceto.get(1).getTipo().equals("int")){
	                		assembler = assembler + "MOV EAX, auxiliar"+lexemaDer + '\n';
	                		assembler = assembler + "MOV [esi],EAX" +'\n';
                		}
                		else {
                			assembler = assembler + "FLD auxiliar" + lexemaDer + '\n';
                			assembler = assembler + "FST DWORD PTR [esi]"+ '\n';
                		}
                	}
                	else ////(ASIGN,TERCETO,variable)
                		if(lexemaIzq.contains("@") && !lexemaDer.contains("@")) {
                			if (listTerceto.get(1).getTipo().equals("int")){
	                			assembler = assembler + "MOV EAX, _"+lexemaDer.replace("-", "@") + '\n';
	                    		assembler = assembler + "MOV [esi],EAX" +'\n';
                			}
                			else {
                				assembler = assembler + "FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
                                assembler = assembler + "FST DWORD PTR [esi]"+ '\n';
                			}
                		}
            
        return assembler;
    }

}
