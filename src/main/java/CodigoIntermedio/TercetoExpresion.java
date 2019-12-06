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
            	assembler.append("MOV AX, " + "_" +lexemaIzq.replace('-', '@') + System.lineSeparator());
                assembler.append(operador + " AX, " + "_"+  lexemaDer.replace('-', '@') + System.lineSeparator());
                assembler.append("MOV "  + "auxiliar@" + getNumTerceto() + " ,AX" + System.lineSeparator());
                assembler.append("JO LabelOverflowSuma" + System.lineSeparator()); 
            }
            else
            	{ assembler.append("FINIT" + System.lineSeparator());
            	  assembler.append("FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ System.lineSeparator());
            	  assembler.append("FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ System.lineSeparator());
                  assembler.append("F"+operador+ " " + System.lineSeparator());
                  assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());
                  assembler.append(getComparacion() + System.lineSeparator()); 
            	}
        else
        	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,@)
        		if (tercetoIzq.getTipo().equals("int")) {
        			assembler.append("MOV AX," + "_"+ lexemaIzq.replace('-', '@') + System.lineSeparator());
        			assembler.append(operador +" AX," + "auxiliar" + lexemaDer  +System.lineSeparator());
        			assembler.append("MOV " + "auxiliar@" + getNumTerceto() + ", AX" + System.lineSeparator());
        			assembler.append("JO LabelOverflowSuma" + System.lineSeparator()); 		                 
        		}
		              
    			 else
					{assembler.append("FINIT" + System.lineSeparator());
    				 assembler.append("FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
					 assembler.append("FLD " +  "auxiliar" + lexemaDer + System.lineSeparator());
		             assembler.append("F"+operador+ " " + System.lineSeparator());
		             assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());
		             assembler.append(getComparacion() + System.lineSeparator()); 					
					}
        	else
    			if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
    				if (tercetoIzq.getTipo().equals("int")) {
    					assembler.append("MOV AX," + "auxiliar" + lexemaIzq +System.lineSeparator());
        				assembler.append(operador +" AX, " + "_"+lexemaDer.replace('-', '@') + System.lineSeparator());
        				assembler.append("MOV " + "auxiliar@" + getNumTerceto() +", AX" + System.lineSeparator());
        				assembler.append("JO LabelOverflowSuma" + System.lineSeparator()); 
    				}	
			           
    				else
    					{assembler.append("FINIT" + System.lineSeparator());
    					 assembler.append("FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
    					 assembler.append("FLD " +  "auxiliar" + lexemaIzq + System.lineSeparator());
    		             assembler.append("F"+operador+ " " + System.lineSeparator());
    		             assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());
    		             assembler.append(getComparacion() + System.lineSeparator());    					
    					}
        		else
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))// (OP,@,@)
    					if (tercetoIzq.getTipo().equals("int")) {
    						assembler.append("MOV AX," + "auxiliar" + lexemaIzq + System.lineSeparator());
    						assembler.append(operador +" AX, " + "auxiliar" + lexemaDer + System.lineSeparator());
    						assembler.append("MOV " + "auxiliar@" + getNumTerceto() + ",AX" + System.lineSeparator());
    						assembler.append("JO LabelOverflowSuma" + System.lineSeparator()); 
    					}

    					else {
    						assembler.append("FINIT" + System.lineSeparator());
	    					assembler.append("FLD " + "auxiliar" + lexemaIzq + System.lineSeparator());
	   					 	assembler.append("FLD " +  "auxiliar" + lexemaDer + System.lineSeparator());
	   					 	assembler.append("F"+operador+ " " + System.lineSeparator());
	   					 	assembler.append("FSTP " + "auxiliar@" + getNumTerceto() + System.lineSeparator());
	   					 	assembler.append(getComparacion() + System.lineSeparator());     					
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
		assCom.append("FLD MayorNumFloatPos" + System.lineSeparator());  
		assCom.append("FLD auxiliar@" + getNumTerceto() + System.lineSeparator());      
		assCom.append("FCOM" + System.lineSeparator());
		assCom.append("FSTSW AX" + System.lineSeparator());
		assCom.append("SAHF" + System.lineSeparator());
		assCom.append("JA LabelOverflowSuma" + System.lineSeparator());
		assCom.append("FLD MenorNumFloatNeg" + System.lineSeparator());
		assCom.append("FLD auxiliar@" + getNumTerceto() + System.lineSeparator());
		assCom.append("FCOM" + System.lineSeparator());
		assCom.append("FSTSW AX" + System.lineSeparator());
		assCom.append("SAHF" + System.lineSeparator());
		assCom.append("JB LabelOverflowSuma" + System.lineSeparator()); 

    	return assCom.toString();
    }

}


