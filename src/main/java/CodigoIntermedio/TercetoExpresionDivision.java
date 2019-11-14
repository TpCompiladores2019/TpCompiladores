package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresionDivision extends TercetoExpresion {

	public TercetoExpresionDivision(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

    public String getCodigoAssembler() {
    	String operador = "IDIV";
    	String lexemaIzq = listTerceto.get(1).getLexema();
    	String lexemaDer = listTerceto.get(2).getLexema();
    
    	
    	
    	if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) 
			if (listTerceto.get(1).getTipo().equals("int"))// (operador,id,id)
    			return "MOV AX, _" + lexemaIzq + '\n' +
    					"CWD" + '\n' +
    					"MOV BX, _" + lexemaDer + '\n' +
    					"CMP BX,0" + '\n' + 
    					"JE LabelDividirCero" + '\n' +
    					"IDIV BX" + '\n' +
    					"MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
    		else {
    			System.out.println("sadas");
    		}
    	else
    		if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) //(operador,terceto,id)
    			if (listTerceto.get(1).getTipo().equals("int"))
    				return "MOV AX, auxiliar" + lexemaIzq + '\n' +
        					"CWD" + '\n' +
        					"MOV BX, _" + lexemaDer + '\n' +
        					"CMP BX,0" + '\n' + 
        					"JE LabelDividirCero" + '\n' +
        					"IDIV BX" + '\n' +
        					"MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
    			else
    				System.out.println("entro");
    		else
    			if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) //(operador,id,terceto)
        			if (listTerceto.get(1).getTipo().equals("int")) {
        				return "MOV AX, _" + lexemaIzq + '\n' +
            					"CWD" + '\n' +
            					"MOV BX, auxiliar" + lexemaDer + '\n' +
            					"CMP BX,0" + '\n' + 
            					"JE LabelDividirCero" + '\n' +
            					"IDIV BX" + '\n' +
            					"MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
        			
        			}
        			else
        				System.out.println("entro");
    			else
    				if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))
    					if (listTerceto.get(1).getTipo().equals("int"))
    						return "MOV AX, auxiliar" + lexemaIzq + '\n' +
    	        					"CWD" + '\n' +
    	        					"MOV BX, auxiliar" + lexemaDer + '\n' +
    	        					"CMP BX,0" + '\n' + 
    	        					"JE LabelDividirCero" + '\n' +
    	        					"IDIV BX" + '\n' +
    	        					"MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
    	    			
    					else
    						System.out.println("entro");	
    	return null;
    
    }
 
}
