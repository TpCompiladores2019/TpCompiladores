package CodigoIntermedio;

import Lexico.Token;

public class TercetoComparacion extends TercetoAbstracto{

	public TercetoComparacion(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}

	@Override
	public String getCodigoAssembler() {
		StringBuilder assembler = new StringBuilder();
    	String lexemaIzq = listTerceto.get(1).getLexema();
    	String lexemaDer = listTerceto.get(2).getLexema();
    	
    	
    	if (!lexemaIzq.contains("@") && !lexemaDer.contains("@")) { // (operador, variable, variable )
    		if (listTerceto.get(1).getTipo().equals("float")){
    			assembler.append("FINIT" + System.lineSeparator());
    			assembler.append("FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') +  System.lineSeparator());
    			assembler.append("FLD _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') +  System.lineSeparator());
    			assembler.append("FCOM" + System.lineSeparator());
                assembler.append("FSTSW AX" + System.lineSeparator());
                assembler.append("SAHF" + System.lineSeparator());
    		}
    		else 
    			return  "MOV AX, _" + lexemaIzq.replace('-', '@') + System.lineSeparator()+
    					"CMP AX, _"  + lexemaDer.replace('-', '@') + System.lineSeparator();
    	}
    	else
    		if(lexemaIzq.contains("@") && !lexemaDer.contains("@")) { // (operador, terceto, variable)
    			if (listTerceto.get(1).getTipo().equals("float")){
    				assembler.append("FINIT" + System.lineSeparator());
    				assembler.append("FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') +  System.lineSeparator());                   
    				assembler.append("FLD auxiliar" + lexemaIzq + System.lineSeparator());
        			assembler.append("FCOM" + System.lineSeparator());
                    assembler.append("FSTSW AX" + System.lineSeparator());
                    assembler.append("SAHF" + System.lineSeparator());
        		}
    			else
    				return  "MOV AX, _"+ lexemaDer.replace('-', '@')+System.lineSeparator() +
    						"CMP auxiliar"+lexemaIzq+", AX"+ System.lineSeparator();
    		}
    		else
    			if(!lexemaIzq.contains("@") && lexemaDer.contains("@")) { // (operador, variable, terceto)
    				if (listTerceto.get(1).getTipo().equals("float")){
    					assembler.append("FINIT" + System.lineSeparator());
    					assembler.append("FLD auxiliar" + lexemaDer + System.lineSeparator());
    					assembler.append("FLD _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') +  System.lineSeparator());
                        assembler.append("FCOM" + System.lineSeparator());
                        assembler.append("FSTSW AX" + System.lineSeparator());
                        assembler.append("SAHF" + System.lineSeparator());
            		}
        			else
	    				return  "MOV AX, _"+lexemaIzq.replace('-', '@')+System.lineSeparator() +
	    						"CMP AX, auxiliar"+lexemaDer+ System.lineSeparator();
                }
    			else
    				if(lexemaIzq.contains("@") && lexemaDer.contains("@")) { // (operador, terceto, terceto)
        				if (listTerceto.get(1).getTipo().equals("float")){
        					assembler.append("FINIT" + System.lineSeparator());
        					assembler.append("FLD auxiliar" + lexemaDer + System.lineSeparator());
                			assembler.append("FLD auxiliar" + lexemaIzq + System.lineSeparator());       			
                            assembler.append("FCOM" + System.lineSeparator());
                            assembler.append("FSTSW AX" + System.lineSeparator());
                            assembler.append("SAHF" + System.lineSeparator());
                		}
            			else
	        				return  "MOV AX, auxiliar"+lexemaIzq+System.lineSeparator() +
	        						"CMP AX, auxiliar"+lexemaDer+ System.lineSeparator();
    				}
    	return assembler.toString();
    	}
		

}
