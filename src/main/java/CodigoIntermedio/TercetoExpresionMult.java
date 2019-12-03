package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresionMult extends TercetoExpresion {

	public TercetoExpresionMult(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}
	
	
    public String getCodigoAssembler() {
    	String assembler= "" ;
    	
    	String operador = "IMUL";

         Token tercetoIzq =  listTerceto.get(1);
         Token tercetoDer =  listTerceto.get(2);
         String lexemaIzq =  tercetoIzq.getLexema();
         String lexemaDer =  tercetoDer.getLexema();

         if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))){ //(operador,id,id)
             if (tercetoIzq.getTipo().equals("int"))
             	return  "MOV EAX, " + "_" +lexemaIzq.replace('-', '@') + '\n'  
             			+ operador + " EAX, " + "_"+  lexemaDer.replace('-', '@') + '\n'
             			+ "MOV "  + "auxiliar@" + getNumTerceto() + " ,EAX" + '\n';
             else{
            	 assembler = assembler + "FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n';
            	 assembler = assembler + "FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n';
            	 assembler = assembler + "FMUL " + '\n';
            	 assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
             }
             }
         	
         else
         	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) { // (operador,id,@)
         		if (tercetoIzq.getTipo().equals("int"))
         			return "MOV EAX," + "_"+ lexemaIzq.replace('-', '@') + '\n' 
 			                 + operador +" EAX," + "auxiliar" + lexemaDer  +'\n'
 			                 + "MOV " + "auxiliar@" + getNumTerceto() + ", EAX" + '\n';
         		else{
         			assembler = assembler + "FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
         			assembler = assembler + "FLD " +  "auxiliar" + lexemaDer + '\n';
   		            assembler = assembler + "FMUL" + " " + '\n';
   		            assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';  					
   				}
         	}
         	else
         		if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
         			if (tercetoIzq.getTipo().equals("int"))
         				return "MOV EAX," + "auxiliar" + lexemaIzq +'\n'
 				                 + operador +" EAX, " + "_"+lexemaDer.replace('-', '@') + '\n'
 				                 + "MOV " + "auxiliar@" + getNumTerceto() +", EAX" + '\n';
         			else {
         				assembler = assembler + "FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
						assembler = assembler + "FLD " +  "auxiliar" + lexemaIzq + '\n';
			            assembler = assembler + "FMUL " + '\n';
			            assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n'; 					
					}
         					
         		else	// (OP,@,@)
         			if (tercetoIzq.getTipo().equals("int"))
         				return "MOV EAX," + "auxiliar" + lexemaIzq + '\n' 
 					             + operador +" EAX, " + "auxiliar" + lexemaDer + '\n' 
 					             + "MOV " + "auxiliar@" + getNumTerceto() + ",EAX" + '\n';
         			else{
    					assembler = assembler + "FLD " + "auxiliar" + lexemaIzq + '\n';
   					 	assembler = assembler + "FLD " +  "auxiliar" + lexemaDer + '\n';
   					 	assembler = assembler + "FMUL " + '\n';
   					 	assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';					
    				}
         
         return assembler;
     }
    

}
