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
             	return  "MOV AX, " + "_" +lexemaIzq.replace('-', '@') + System.lineSeparator()  
             			+ operador + " AX, " + "_"+  lexemaDer.replace('-', '@') + System.lineSeparator()
             			+ "MOV "  + "auxiliar@" + getNumTerceto() + " ,AX" + System.lineSeparator();
             else{
            	 assembler.append("FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ System.lineSeparator());
            	 assembler.append("FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ System.lineSeparator());
            	 assembler.append("FMUL " + System.lineSeparator());
            	 assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());
             }
             }
         	
         else
         	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) { // (operador,id,@)
         		if (tercetoIzq.getTipo().equals("int"))
         			return "MOV AX," + "_"+ lexemaIzq.replace('-', '@') + System.lineSeparator() 
 			                 + operador +" AX," + "auxiliar" + lexemaDer  +System.lineSeparator()
 			                 + "MOV " + "auxiliar@" + getNumTerceto() + ", AX" + System.lineSeparator();
         		else{
         			assembler.append("FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
         			assembler.append("FLD " +  "auxiliar" + lexemaDer + System.lineSeparator());
   		            assembler.append("FMUL" + " " + System.lineSeparator());
   		            assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());  					
   				}
         	}
         	else
         		if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
         			if (tercetoIzq.getTipo().equals("int"))
         				return "MOV AX," + "auxiliar" + lexemaIzq +System.lineSeparator()
 				                 + operador +" AX, " + "_"+lexemaDer.replace('-', '@') + System.lineSeparator()
 				                 + "MOV " + "auxiliar@" + getNumTerceto() +", AX" + System.lineSeparator();
         			else {
         				assembler.append("FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
						assembler.append("FLD " +  "auxiliar" + lexemaIzq + System.lineSeparator());
			            assembler.append("FMUL " + System.lineSeparator());
			            assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator()); 					
					}
         					
         		else	// (OP,@,@)
         			if (tercetoIzq.getTipo().equals("int"))
         				return "MOV AX," + "auxiliar" + lexemaIzq + System.lineSeparator() 
 					             + operador +" AX, " + "auxiliar" + lexemaDer + System.lineSeparator() 
 					             + "MOV " + "auxiliar@" + getNumTerceto() + ",AX" + System.lineSeparator();
         			else{
    					assembler.append("FLD " + "auxiliar" + lexemaIzq + System.lineSeparator());
   					 	assembler.append("FLD " +  "auxiliar" + lexemaDer + System.lineSeparator());
   					 	assembler.append("FMUL " + System.lineSeparator());
   					 	assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());					
    				}
         
         return assembler.toString();
     }
    

}
