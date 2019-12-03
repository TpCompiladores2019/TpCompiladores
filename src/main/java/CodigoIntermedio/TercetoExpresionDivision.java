package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresionDivision extends TercetoExpresion {

	public TercetoExpresionDivision(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

    public String getCodigoAssembler() {
    	String assembler= "";
    	
    	String operador = "IDIV";
    	String lexemaIzq = listTerceto.get(1).getLexema();
    	String lexemaDer = listTerceto.get(2).getLexema();
    
    	
    	
    	if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) 
			if (listTerceto.get(1).getTipo().equals("int"))// (operador,id,id)
    			return "MOV EAX, _" + lexemaIzq.replace('-', '@') + '\n' +
    					"CWD" + '\n' +
    					"MOV EBX, _" + lexemaDer.replace('-', '@') + '\n' +
    					"CMP EBX,0" + '\n' + 
    					"JE DividirCero" + '\n' +
    					"IDIV EBX" + '\n' +
    					"MOV auxiliar@" + getNumTerceto() + " , EAX" + '\n';
    		else {assembler = assembler + "FINIT" + '\n';
	    		assembler = assembler + "FLD " + "_" +  lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n';
	          	assembler = assembler + "FLD " + "_" +  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@')+ '\n';
	            assembler = assembler + "FLDZ"+'\n';
	            assembler = assembler + "FCOM" + '\n';
	            assembler = assembler + "FSTSW AX" + '\n';
	            assembler = assembler + "SAHF" + '\n';
	            assembler = assembler + "JE DividirCero" + '\n';
	          	assembler = assembler + "FDIV "  + '\n';
	            assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
	           
    		}
    	else
    		if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) //(operador,terceto,id)
    			if (listTerceto.get(1).getTipo().equals("int"))
    				return "MOV EAX, auxiliar" + lexemaIzq + '\n' +
        					"CWD" + '\n' +
        					"MOV EBX, _" + lexemaDer.replace('-', '@') + '\n' +
        					"CMP EBX,0" + '\n' + 
        					"JE DividirCero" + '\n' +
        					"IDIV EBX" + '\n' +
        					"MOV auxiliar@" + getNumTerceto() + " , EAX" + '\n';
    			else{assembler = assembler + "FINIT" + '\n';
    				assembler = assembler + "FLD " +  "auxiliar" + lexemaIzq + '\n';
				 	assembler = assembler + "FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
				 	assembler = assembler + "FLDZ"+'\n';
		            assembler = assembler + "FCOM" + '\n';
		            assembler = assembler + "FSTSW AX" + '\n';
		            assembler = assembler + "SAHF" + '\n';
		            assembler = assembler + "JE DividirCero" + '\n';
		          	assembler = assembler + "FDIV "  + '\n';
		            assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
    			}
    		else
    			if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) //(operador,id,terceto)
        			if (listTerceto.get(1).getTipo().equals("int")) {
        				return "MOV EAX, _" + lexemaIzq.replace('-', '@') + '\n' +
            					"CWD" + '\n' +
            					"MOV EBX, auxiliar" + lexemaDer + '\n' +
            					"CMP EBX,0" + '\n' + 
            					"JE DividirCero" + '\n' +
            					"IDIV EBX" + '\n' +
            					"MOV auxiliar@" + getNumTerceto() + " , EAX" + '\n';
        			}
        			else {assembler = assembler + "FINIT" + '\n';
        				assembler = assembler + "FLD " +  "auxiliar" + lexemaIzq + '\n';
        				assembler = assembler + "FLD " + "_"+  lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n';
        				assembler = assembler + "FCOM" + '\n';
     		            assembler = assembler + "FSTSW AX" + '\n';
     		            assembler = assembler + "SAHF" + '\n';
     		            assembler = assembler + "JE DividirCero" + '\n';
     		          	assembler = assembler + "FDIV "  + '\n';
     		            assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
        			}
    			else //(operador,terceto,terceto)
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))
    					if (listTerceto.get(1).getTipo().equals("int"))
    						return "MOV EAX, auxiliar" + lexemaIzq + '\n' +
    	        					"CWD" + '\n' +
    	        					"MOV EBX, auxiliar" + lexemaDer + '\n' +
    	        					"CMP EBX,0" + '\n' + 
    	        					"JE DividirCero" + '\n' +
    	        					"IDIV EBX" + '\n' +
    	        					"MOV auxiliar@" + getNumTerceto() + " , EAX" + '\n';
    	    			
    					else {assembler = assembler + "FINIT" + '\n';
	    					assembler = assembler + "FLD " + "auxiliar" + lexemaIzq + '\n';
	   					 	assembler = assembler + "FLD " +  "auxiliar" + lexemaDer + '\n';
    						assembler = assembler + "FCOM" + '\n';
	     		            assembler = assembler + "FSTSW AX" + '\n';
	     		            assembler = assembler + "SAHF" + '\n';
	     		            assembler = assembler + "JE DividirCero" + '\n';
	     		          	assembler = assembler + "FDIV "  + '\n';
	     		            assembler = assembler + "FST " + "auxiliar@" + getNumTerceto() + '\n';
    					}
    	return assembler;
    
    }
 
}
