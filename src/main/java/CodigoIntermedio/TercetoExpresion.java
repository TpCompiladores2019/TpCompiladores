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
            	assembler.append("MOV EAX, " + "_" +lexemaIzq.replace('-', '@') + '\n');
                assembler.append(operador + " EAX, " + "_"+  lexemaDer.replace('-', '@') + '\n');
                assembler.append("MOV "  + "auxiliar@" + getNumTerceto() + " ,EAX" + '\n');
                assembler.append(getComparacion(operador,"int") + '\n'); 
            }
            else
            	{ assembler.append("FINIT" + '\n');
            	  assembler.append("FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n');
            	  assembler.append("FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n');
                  assembler.append("F"+operador+ " " + '\n');
                  assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
                  assembler.append(getComparacion(operador,"float") + '\n'); 
            	}
        else
        	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,@)
        		if (tercetoIzq.getTipo().equals("int")) {
        			assembler.append("MOV EAX," + "_"+ lexemaIzq.replace('-', '@') + '\n');
        			assembler.append(operador +" EAX," + "auxiliar" + lexemaDer  +'\n');
        			assembler.append("MOV " + "auxiliar@" + getNumTerceto() + ", EAX" + '\n');
        			assembler.append(getComparacion(operador,"int") + '\n');		                 
        		}
		              
    			 else
					{assembler.append("FINIT" + '\n');
    				 assembler.append("FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
					 assembler.append("FLD " +  "auxiliar" + lexemaDer + '\n');
		             assembler.append("F"+operador+ " " + '\n');
		             assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
		             assembler.append(getComparacion(operador,"float") + '\n'); 					
					}
        	else
    			if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
    				if (tercetoIzq.getTipo().equals("int")) {
    					assembler.append("MOV EAX," + "auxiliar" + lexemaIzq +'\n');
        				assembler.append(operador +" EAX, " + "_"+lexemaDer.replace('-', '@') + '\n');
        				assembler.append("MOV " + "auxiliar@" + getNumTerceto() +", EAX" + '\n');
        				assembler.append(getComparacion(operador,"int") + '\n');
    				}	
			           
    				else
    					{assembler.append("FINIT" + '\n');
    					 assembler.append("FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
    					 assembler.append("FLD " +  "auxiliar" + lexemaIzq + '\n');
    		             assembler.append("F"+operador+ " " + '\n');
    		             assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
    		             assembler.append(getComparacion(operador,"float") + '\n');    					
    					}
        		else
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))// (OP,@,@)
    					if (tercetoIzq.getTipo().equals("int")) {
    						assembler.append("MOV EAX," + "auxiliar" + lexemaIzq + '\n');
    						assembler.append(operador +" EAX, " + "auxiliar" + lexemaDer + '\n');
    						assembler.append("MOV " + "auxiliar@" + getNumTerceto() + ",EAX" + '\n');
    						assembler.append(getComparacion(operador,"int") + '\n'); 
    					}

    					else {
    						assembler.append("FINIT" + '\n');
	    					assembler.append("FLD " + "auxiliar" + lexemaIzq + '\n');
	   					 	assembler.append("FLD " +  "auxiliar" + lexemaDer + '\n');
	   					 	assembler.append("F"+operador+ " " + '\n');
	   					 	assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
	   					 	assembler.append(getComparacion(operador,"float") + '\n');     					
	    					}
        return assembler.toString();
    }

    private String obtenerOperador(String lexema) {
        if (lexema.equals("+"))
            return "ADD";
        return "SUB";
    }
    
    private String getComparacion(String operador,String tipo) {
    	StringBuilder assCom=new StringBuilder();
    	if (tipo.equals("int")) {
	    	if (operador.equals("ADD")) {
			  assCom.append("CMP auxiliar@" + getNumTerceto() + ", 32767" + '\n');
		      assCom.append("JG LabelOverflowSuma" + '\n');
	    	}
	    	else {
	    		assCom.append("CMP auxiliar@" + getNumTerceto() + ", -32768" + '\n');
	   	      	assCom.append("JL LabelOverflowSuma" + '\n');
	    	}
    	}
    	else {
    		if (operador.equals("ADD")) {
  			  assCom.append("FLD auxiliar@" + getNumTerceto() + '\n');
  		      assCom.append("FLD MayorNumFloatPos" + '\n');
  		      assCom.append("FCOM" + '\n');
  		      assCom.append("FSTSW AX" + '\n');
  		      assCom.append("SAHF" + '\n');
  		      assCom.append("JG LabelOverflowSuma" + '\n');
  	    	}
  	    	else {
  	    	  assCom.append("FLD auxiliar@" + getNumTerceto() + '\n');
		      assCom.append("FLD MenorNumFloatNeg" + '\n');
		      assCom.append("FCOM" + '\n');
		      assCom.append("FSTSW AX" + '\n');
		      assCom.append("SAHF" + '\n');
		      assCom.append("JG LabelOverflowSuma" + '\n'); //TODO mirar porque jg y no jl
  	    	}
    	}
    	
    	
    	return assCom.toString();
    }

}


