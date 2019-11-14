package CodigoIntermedio;

import Lexico.Token;

public class TercetoComparacion extends TercetoAbstracto{

	public TercetoComparacion(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
	}

	@Override
	public String getCodigoAssembler() {
    	String operador = listTerceto.get(0).getLexema();
    	String lexemaIzq = listTerceto.get(1).getLexema();
    	String lexemaDer = listTerceto.get(2).getLexema();
    	
    	
    	if (!lexemaIzq.contains("@") && !lexemaDer.contains("@")) { // (operador, variable, variable )
    		return  "MOV " + "AX" + ", _" + lexemaIzq + '\n'+
              		"CMP " + "AX "+ ", _"  + lexemaDer + '\n';
    	}
    	else
    		if(lexemaIzq.contains("@") && !lexemaDer.contains("@")) { // (operador, terceto, variable)
    			return  "MOV " + "AX" +", _"+ lexemaDer+'\n' +
    					"CMP" + " " + "auxiliar"+lexemaIzq+", "+ "AX"+ '\n';
    		}
    		else
    			if(!lexemaIzq.contains("@") && lexemaDer.contains("@")) { // (operador, variable, terceto)
    				return  "MOV " + "AX" +", _"+lexemaIzq+'\n' +
    						"CMP" + " " + "AX" +", "+ "auxiliar"+lexemaDer+ '\n';
                }
    			else// (operador, terceto, terceto)
    					return null;
    				
    	}

}
