package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresionMult extends TercetoExpresion {

	public TercetoExpresionMult(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}
	
	
    public String getCodigoAssembler() {
    	StringBuilder assembler= new StringBuilder();
    	
    	String operador = "IMUL";

         Token tercetoIzq =  listTerceto.get(1);
         Token tercetoDer =  listTerceto.get(2);
         String lexemaIzq =  tercetoIzq.getLexema();
         String lexemaDer =  tercetoDer.getLexema();

         if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))){ //(operador,id,id)
             if (tercetoIzq.getTipo().equals("int"))
             	return  "MOV AX, " + "_" +lexemaIzq.replace('-', '@') + '\n'  
             			+ operador + " AX, " + "_"+  lexemaDer.replace('-', '@') + '\n'
             			+ "MOV "  + "auxiliar@" + getNumTerceto() + " ,AX" + '\n';
             else{
            	 assembler.append("FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n');
            	 assembler.append("FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n');
            	 assembler.append("FMUL " + '\n');
            	 assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');
             }
             }
         	
         else
         	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) { // (operador,id,@)
         		if (tercetoIzq.getTipo().equals("int"))
         			return "MOV AX," + "_"+ lexemaIzq.replace('-', '@') + '\n' 
 			                 + operador +" AX," + "auxiliar" + lexemaDer  +'\n'
 			                 + "MOV " + "auxiliar@" + getNumTerceto() + ", AX" + '\n';
         		else{
         			assembler.append("FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
         			assembler.append("FLD " +  "auxiliar" + lexemaDer + '\n');
   		            assembler.append("FMUL" + " " + '\n');
   		            assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');  					
   				}
         	}
         	else
         		if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
         			if (tercetoIzq.getTipo().equals("int"))
         				return "MOV AX," + "auxiliar" + lexemaIzq +'\n'
 				                 + operador +" AX, " + "_"+lexemaDer.replace('-', '@') + '\n'
 				                 + "MOV " + "auxiliar@" + getNumTerceto() +", AX" + '\n';
         			else {
         				assembler.append("FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
						assembler.append("FLD " +  "auxiliar" + lexemaIzq + '\n');
			            assembler.append("FMUL " + '\n');
			            assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n'); 					
					}
         					
         		else	// (OP,@,@)
         			if (tercetoIzq.getTipo().equals("int"))
         				return "MOV AX," + "auxiliar" + lexemaIzq + '\n' 
 					             + operador +" AX, " + "auxiliar" + lexemaDer + '\n' 
 					             + "MOV " + "auxiliar@" + getNumTerceto() + ",AX" + '\n';
         			else{
    					assembler.append("FLD " + "auxiliar" + lexemaIzq + '\n');
   					 	assembler.append("FLD " +  "auxiliar" + lexemaDer + '\n');
   					 	assembler.append("FMUL " + '\n');
   					 	assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');					
    				}
         
         return assembler.toString();
     }
    

}
