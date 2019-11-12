package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresion extends  TercetoAbstracto {
    public TercetoExpresion(Token t1, Token t2, Token t3, int numTerceto) {
        super(t1, t2, t3, numTerceto);

    }

    public String getCodigoAssembler() {
        String operador = obtenerOperador(listTerceto.get(0).getLexema());

        Token tercetoIzq =  listTerceto.get(1);
        Token tercetoDer =  listTerceto.get(2);
        String lexemaIzq = "_"+ tercetoIzq.getLexema();
        String lexemaDer = "_"+ tercetoDer.getLexema();

        if (!lexemaIzq.contains("@")){
            if (!lexemaDer.contains("@")){
                return  "MOV AX," + lexemaIzq + '\n' // (operador,id,id)
                      + operador + " AX," + lexemaDer + '\n'
                      +  "MOV "  + ",AX";
            }
            return "MOV AX," + lexemaIzq + '\n' // (operador,id,@)
                 + operador +" AX,"  +'\n'
                 + "MOV " + ",AX";
        }
        if (!lexemaDer.contains("@")) // (operador,@,id)
            return "MOV AX," +'\n'
                 + operador +" AX," + lexemaDer + '\n'
                 + "MOV " +",AX";

        return "MOV AX," // (OP,@,@)
             + operador +" AX," 
             + "MOV " +",AX" ;

    }

    private String obtenerOperador(String lexema) {
        if (lexema.equals("+"))
            return "ADD";
        return "SUB";
    }

}


