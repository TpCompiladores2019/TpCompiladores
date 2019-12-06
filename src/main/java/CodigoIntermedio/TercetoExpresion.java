package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresion extends  TercetoAbstracto {
    public TercetoExpresion(Token t1, Token t2, Token t3, int numTerceto) {
        super(t1, t2, t3, numTerceto);

    }

    public String getCodigoAssembler() {
        StringBuilder assembler = new StringBuilder();

    	String operador = obtenerOperador(listTerceto.get(0).getLexema());

        Token tercetoIzq =  listTerceto.get(1);
        Token tercetoDer =  listTerceto.get(2);
        String lexemaIzq =  tercetoIzq.getLexema();
        String lexemaDer =  tercetoDer.getLexema();

        if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) //(operador,id,id)
            if (tercetoIzq.getTipo().equals("int")) {
            	assembler.append("MOV AX, " + "_" +lexemaIzq.replace('-', '@') + '\n');
                assembler.append(operador + " AX, " + "_"+  lexemaDer.replace('-', '@') + '\n');
                assembler.append("MOV "  + "auxiliar@" + getNumTerceto() + " ,AX" + '\n');
                assembler.append("JO LabelOverflowSuma" + '\n'); 
            }
            else
            	{ assembler.append("FINIT" + '\n');
            	  assembler.append("FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n');
            	  assembler.append("FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n');
                  assembler.append("F"+operador+ " " + '\n');
                  assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');
                  assembler.append(getComparacion() + '\n'); 
            	}
        else
        	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,@)
        		if (tercetoIzq.getTipo().equals("int")) {
        			assembler.append("MOV AX," + "_"+ lexemaIzq.replace('-', '@') + '\n');
        			assembler.append(operador +" AX," + "auxiliar" + lexemaDer  +'\n');
        			assembler.append("MOV " + "auxiliar@" + getNumTerceto() + ", AX" + '\n');
        			assembler.append("JO LabelOverflowSuma" + '\n'); 		                 
        		}
		              
    			 else
					{assembler.append("FINIT" + '\n');
    				 assembler.append("FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
					 assembler.append("FLD " +  "auxiliar" + lexemaDer + '\n');
		             assembler.append("F"+operador+ " " + '\n');
		             assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');
		             assembler.append(getComparacion() + '\n'); 					
					}
        	else
    			if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
    				if (tercetoIzq.getTipo().equals("int")) {
    					assembler.append("MOV AX," + "auxiliar" + lexemaIzq +'\n');
        				assembler.append(operador +" AX, " + "_"+lexemaDer.replace('-', '@') + '\n');
        				assembler.append("MOV " + "auxiliar@" + getNumTerceto() +", AX" + '\n');
        				assembler.append("JO LabelOverflowSuma" + '\n'); 
    				}	
			           
    				else
    					{assembler.append("FINIT" + '\n');
    					 assembler.append("FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
    					 assembler.append("FLD " +  "auxiliar" + lexemaIzq + '\n');
    		             assembler.append("F"+operador+ " " + '\n');
    		             assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');
    		             assembler.append(getComparacion() + '\n');    					
    					}
        		else
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))// (OP,@,@)
    					if (tercetoIzq.getTipo().equals("int")) {
    						assembler.append("MOV AX," + "auxiliar" + lexemaIzq + '\n');
    						assembler.append(operador +" AX, " + "auxiliar" + lexemaDer + '\n');
    						assembler.append("MOV " + "auxiliar@" + getNumTerceto() + ",AX" + '\n');
    						assembler.append("JO LabelOverflowSuma" + '\n'); 
    					}

    					else {
    						assembler.append("FINIT" + '\n');
	    					assembler.append("FLD " + "auxiliar" + lexemaIzq + '\n');
	   					 	assembler.append("FLD " +  "auxiliar" + lexemaDer + '\n');
	   					 	assembler.append("F"+operador+ " " + '\n');
	   					 	assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + '\n');
	   					 	assembler.append(getComparacion() + '\n');     					
	    					}
        return assembler.toString();
    }

    private String obtenerOperador(String lexema) {
        if (lexema.equals("+"))
            return "ADD";
        return "SUB";
    }
    
    private String getComparacion() {
    	StringBuilder assCom=new StringBuilder();
		assCom.append("FLD MayorNumFloatPos" + '\n');  
		assCom.append("FLD auxiliar@" + getNumTerceto() + '\n');      
		assCom.append("FCOM" + '\n');
		assCom.append("FSTSW AX" + '\n');
		assCom.append("SAHF" + '\n');
		assCom.append("JA LabelOverflowSuma" + '\n');
		assCom.append("FLD MenorNumFloatNeg" + '\n');
		assCom.append("FLD auxiliar@" + getNumTerceto() + '\n');
		assCom.append("FCOM" + '\n');
		assCom.append("FSTSW AX" + '\n');
		assCom.append("SAHF" + '\n');
		assCom.append("JB LabelOverflowSuma" + '\n'); 

    	return assCom.toString();
    }

}


