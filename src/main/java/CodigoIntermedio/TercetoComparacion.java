package CodigoIntermedio;

import Lexico.Token;

public class TercetoComparacion extends TercetoAbstracto{

	public TercetoComparacion(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}

	@Override
	public String getCodigoAssembler() {
		String assembler = "";
    	String operador = listTerceto.get(0).getLexema();
   
    	String lexemaIzq = listTerceto.get(1).getLexema();
    	String lexemaDer = listTerceto.get(2).getLexema();
    	
    	
    	if (!lexemaIzq.contains("@") && !lexemaDer.contains("@")) { // (operador, variable, variable )
    		if (listTerceto.get(1).getTipo().equals("float")){
    			assembler = assembler + "FINIT" + '\n';
    			assembler = assembler + "FLD _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') +  '\n';
    			assembler = assembler + "FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') +  '\n';
                assembler = assembler + "FCOM" + '\n';
                assembler = assembler + "FSTSW AX" + '\n';
                assembler = assembler + "SAHF" + '\n';
    		}
    		else 
    			return  "MOV " + "AX" + ", _" + lexemaIzq + '\n'+
    					"CMP " + "AX "+ ", _"  + lexemaDer + '\n';
    	}
    	else
    		if(lexemaIzq.contains("@") && !lexemaDer.contains("@")) { // (operador, terceto, variable)
    			if (listTerceto.get(1).getTipo().equals("float")){
    				assembler = assembler + "FINIT" + '\n';
        			assembler = assembler + "FLD auxiliar" + lexemaIzq + '\n';
        			assembler = assembler + "FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') +  '\n';
                    assembler = assembler + "FCOM" + '\n';
                    assembler = assembler + "FSTSW AX" + '\n';
                    assembler = assembler + "SAHF" + '\n';
        		}
    			else
    				return  "MOV " + "AX" +", _"+ lexemaDer+'\n' +
    						"CMP" + " " + "auxiliar"+lexemaIzq+", "+ "AX"+ '\n';
    		}
    		else
    			if(!lexemaIzq.contains("@") && lexemaDer.contains("@")) { // (operador, variable, terceto)
    				if (listTerceto.get(1).getTipo().equals("float")){
    					assembler = assembler + "FINIT" + '\n';
            			assembler = assembler + "FLD _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') +  '\n';
            			assembler = assembler + "FLD auxiliar" + lexemaDer + '\n';
                        assembler = assembler + "FCOM" + '\n';
                        assembler = assembler + "FSTSW AX" + '\n';
                        assembler = assembler + "SAHF" + '\n';
            		}
        			else
	    				return  "MOV " + "AX" +", _"+lexemaIzq+'\n' +
	    						"CMP" + " " + "AX" +", "+ "auxiliar"+lexemaDer+ '\n';
                }
    			else
    				if(lexemaIzq.contains("@") && lexemaDer.contains("@")) { // (operador, terceto, terceto)
        				if (listTerceto.get(1).getTipo().equals("float")){
        					assembler = assembler + "FINIT" + '\n';
                			assembler = assembler + "FLD auxiliar" + lexemaIzq + '\n';
                			assembler = assembler + "FLD auxiliar" + lexemaDer + '\n';
                            assembler = assembler + "FCOM" + '\n';
                            assembler = assembler + "FSTSW AX" + '\n';
                            assembler = assembler + "SAHF" + '\n';
                		}
            			else
	        				return  "MOV " + "AX" +", auxiliar"+lexemaIzq+'\n' +
	        						"CMP" + " " + "AX" +", "+ "auxiliar"+lexemaDer+ '\n';
    				}
    	return assembler;
    	}
		

}
