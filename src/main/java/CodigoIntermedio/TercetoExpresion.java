package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresion extends  TercetoAbstracto {
    public TercetoExpresion(Token t1, Token t2, Token t3, int numTerceto) {
        super(t1, t2, t3, numTerceto);

    }

    public String getCodigoAssembler() {
        String assembler = "";

    	String operador = obtenerOperador(listTerceto.get(0).getLexema());

        Token tercetoIzq =  listTerceto.get(1);
        Token tercetoDer =  listTerceto.get(2);
        String lexemaIzq =  tercetoIzq.getLexema();
        String lexemaDer =  tercetoDer.getLexema();

        if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) //(operador,id,id)
            if (tercetoIzq.getTipo().equals("int")) {
            	assembler = assembler + "MOV EAX, " + "_" +lexemaIzq.replace('-', '@') + '\n';
                assembler = assembler + operador + " EAX, " + "_"+  lexemaDer.replace('-', '@') + '\n';
                assembler = assembler + "MOV "  + "auxiliar@" + getNumTerceto() + " ,EAX" + '\n';
                assembler = assembler + getComparacion(operador,"int") + '\n'; 
            }
            else
            	{ assembler = assembler + "FINIT" + '\n';
            	  assembler = assembler + "FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n';
            	  assembler = assembler + "FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n';
                  assembler = assembler + "F"+operador+ " " + '\n';
                  assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
                  assembler = assembler + getComparacion(operador,"float") + '\n'; 
            	}
        else
        	if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,@)
        		if (tercetoIzq.getTipo().equals("int")) {
        			assembler = assembler + "MOV EAX," + "_"+ lexemaIzq.replace('-', '@') + '\n';
        			assembler = assembler + operador +" EAX," + "auxiliar" + lexemaDer  +'\n';
        			assembler = assembler + "MOV " + "auxiliar@" + getNumTerceto() + ", EAX" + '\n';
        			assembler = assembler + getComparacion(operador,"int") + '\n';		                 
        		}
		              
    			 else
					{assembler = assembler + "FINIT" + '\n';
    				 assembler = assembler + "FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
					 assembler = assembler + "FLD " +  "auxiliar" + lexemaDer + '\n';
		             assembler = assembler + "F"+operador+ " " + '\n';
		             assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
		             assembler = assembler + getComparacion(operador,"float") + '\n'; 					
					}
        	else
    			if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,@,id)
    				if (tercetoIzq.getTipo().equals("int")) {
    					assembler = assembler + "MOV EAX," + "auxiliar" + lexemaIzq +'\n';
        				assembler = assembler + operador +" EAX, " + "_"+lexemaDer.replace('-', '@') + '\n';
        				assembler = assembler + "MOV " + "auxiliar@" + getNumTerceto() +", EAX" + '\n';
        				assembler = assembler + getComparacion(operador,"int") + '\n';
    				}	
			           
    				else
    					{assembler = assembler + "FINIT" + '\n';
    					 assembler = assembler + "FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
    					 assembler = assembler + "FLD " +  "auxiliar" + lexemaIzq + '\n';
    		             assembler = assembler + "F"+operador+ " " + '\n';
    		             assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
    		             assembler = assembler + getComparacion(operador,"float") + '\n';    					
    					}
        		else
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))// (OP,@,@)
    					if (tercetoIzq.getTipo().equals("int")) {
    						assembler = assembler + "MOV EAX," + "auxiliar" + lexemaIzq + '\n' ;
    						assembler = assembler  + operador +" EAX, " + "auxiliar" + lexemaDer + '\n' ;
    						assembler = assembler + "MOV " + "auxiliar@" + getNumTerceto() + ",EAX" + '\n';
    						assembler = assembler + getComparacion(operador,"int") + '\n'; 
    					}

    					else {assembler = assembler + "FINIT" + '\n';
	    					assembler = assembler + "FLD " + "auxiliar" + lexemaIzq + '\n';
	   					 	assembler = assembler + "FLD " +  "auxiliar" + lexemaDer + '\n';
	   					 	assembler = assembler + "F"+operador+ " " + '\n';
	   					 	assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
	   					 	assembler = assembler + getComparacion(operador,"float") + '\n';     					
	    					}
        return assembler;
    }

    private String obtenerOperador(String lexema) {
        if (lexema.equals("+"))
            return "ADD";
        return "SUB";
    }
    
    private String getComparacion(String operador,String tipo) {
    	String assCom="";
    	if (tipo.equals("int")) {
	    	if (operador.equals("ADD")) {
			  assCom = assCom + "CMP auxiliar@" + getNumTerceto() + ", 32767" + '\n';
		      assCom = assCom + "JG LabelOverflowSuma" + '\n';
	    	}
	    	else {
	    		assCom = assCom + "CMP auxiliar@" + getNumTerceto() + ", -32768" + '\n';
	   	      	assCom = assCom + "JL LabelOverflowSuma" + '\n';
	    	}
    	}
    	else {
    		if (operador.equals("ADD")) {
  			  assCom = assCom + "FLD auxiliar@" + getNumTerceto() + '\n';
  		      assCom = assCom + "FLD MayorNumFloatPos" + '\n';
  		      assCom = assCom + "FCOM" + '\n';
  		      assCom = assCom + "FSTSW AX" + '\n';
  		      assCom = assCom + "SAHF" + '\n';
  		      assCom = assCom + "JG LabelOverflowSuma" + '\n';
  	    	}
  	    	else {
  	    	  assCom = assCom + "FLD auxiliar@" + getNumTerceto() + '\n';
		      assCom = assCom + "FLD MenorNumFloatNeg" + '\n';
		      assCom = assCom + "FCOM" + '\n';
		      assCom = assCom + "FSTSW AX" + '\n';
		      assCom = assCom + "SAHF" + '\n';
		      assCom = assCom + "JG LabelOverflowSuma" + '\n'; //TODO mirar porque jg y no jl
  	    	}
    	}
    	
    	
    	return assCom;
    }

}


