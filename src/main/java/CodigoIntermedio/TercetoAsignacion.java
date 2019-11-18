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
                if (listTerceto.get(2).getTipo() == "float"){
                	System.out.println("entraste a la asignacion");
                    assembler = assembler + "FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
                    assembler = assembler + "FST _"+ lexemaIzq +'\n';
                }
                else{
                    assembler = assembler + "MOV " + "AX" + ", _" + lexemaDer + '\n';
                    assembler = assembler + "MOV _" + lexemaIzq + ", " + "AX" + '\n';
                }
            }
            else
                //(ASIG, variable, terceto)
                if (listTerceto.get(2).getTipo() == "float"){
                    assembler = assembler + "FLD auxiliar" + lexemaDer + '\n';
                    assembler = assembler + "FST _" + lexemaIzq + '\n';
                }
                else {
                    assembler = assembler + "MOV " + "AX" + ", auxiliar" + lexemaDer + '\n';
                    assembler = assembler + "MOV _" + lexemaIzq + ", " + "AX" + '\n';
                }
        return assembler;
    }

}
