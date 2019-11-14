package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresionMult extends TercetoExpresion {

	public TercetoExpresionMult(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}
	
	
    public String getCodigoAssembler() {
    	 String operador = "IMUL";

         Token tercetoIzq =  listTerceto.get(1);
         Token tercetoDer =  listTerceto.get(2);
         String lexemaIzq =  tercetoIzq.getLexema();
         String lexemaDer =  tercetoDer.getLexema();

         if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))){ //(operador,id,id)
             if (tercetoIzq.getTipo().equals("int"))
             	return  "MOV AX, " + "_" +lexemaIzq + '\n'  
             			+ operador + " AX, " + "_"+  lexemaDer + '\n'
             			+ "MOV "  + "auxiliar@" + getNumTerceto() + " ,AX" + '\n';
             }
         	else
         		if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,@)
         			 if (tercetoIzq.getTipo().equals("int"))
 			            return "MOV AX," + "_"+ lexemaIzq + '\n' 
 			                 + operador +" AX," + "auxiliar" + lexemaDer  +'\n'
 			                 + "MOV " + "auxiliar@" + getNumTerceto() + ", AX" + '\n';
         			 else
         			 {System.out.println("entra");}
         		else
         			if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
         				if (tercetoIzq.getTipo().equals("int"))
 				            return "MOV AX," + "auxiliar" + lexemaIzq +'\n'
 				                 + operador +" AX, " + "_"+lexemaDer + '\n'
 				                 + "MOV " + "auxiliar@" + getNumTerceto() +", AX" + '\n';
         			else	// (OP,@,@)
         				if (tercetoIzq.getTipo().equals("int"))
 					        return "MOV AX," + "auxiliar" + lexemaIzq + '\n' 
 					             + operador +" AX, " + "auxiliar" + lexemaDer + '\n' 
 					             + "MOV " + "auxiliar@" + getNumTerceto() + ",AX" + '\n'  ;
         return "";
     }
    

}
