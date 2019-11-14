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
        String lexemaIzq =  tercetoIzq.getLexema();
        String lexemaDer =  tercetoDer.getLexema();

        if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) //(operador,id,id)
            if (tercetoIzq.getTipo().equals("int"))
            	return  "MOV AX, " + "_" +lexemaIzq + '\n'  
            			+ operador + " AX, " + "_"+  lexemaDer + '\n'
            			+ "MOV "  + "auxiliar@" + getNumTerceto() + " ,AX" + '\n'
            			+ "JO LabelOverflowSuma" + '\n';
            else
            	{System.out.println("entra");}
        else
        	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,@)
        		if (tercetoIzq.getTipo().equals("int"))
		            return "MOV AX," + "_"+ lexemaIzq + '\n' 
		                 + operador +" AX," + "auxiliar" + lexemaDer  +'\n'
		                 + "MOV " + "auxiliar@" + getNumTerceto() + ", AX" + '\n'
		                 + "JO LabelOverflowSuma" + '\n';
    			 else
    			 	{System.out.println("entra");}
        	else
    			if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
    				if (tercetoIzq.getTipo().equals("int"))
			            return "MOV AX," + "auxiliar" + lexemaIzq +'\n'
			                + operador +" AX, " + "_"+lexemaDer + '\n'
			                + "MOV " + "auxiliar@" + getNumTerceto() +", AX" + '\n'
    						+ "JO LabelOverflowSuma" + '\n';
    				else
    					{System.out.println("entra");}
        		else
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))// (OP,@,@)
    					if (tercetoIzq.getTipo().equals("int"))
					        return "MOV AX," + "auxiliar" + lexemaIzq + '\n' 
					             + operador +" AX, " + "auxiliar" + lexemaDer + '\n' 
					             + "MOV " + "auxiliar@" + getNumTerceto() + ",AX" + '\n'
					             + "JO LabelOverflowSuma" + '\n';
    					else
    						{System.out.println("entra");}
        return "";
    }

    private String obtenerOperador(String lexema) {
        if (lexema.equals("+"))
            return "ADD";
        return "SUB";
    }

}


