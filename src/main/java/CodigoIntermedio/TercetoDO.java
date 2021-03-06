package CodigoIntermedio;

import Lexico.Token;

public class TercetoDO extends TercetoAbstracto{
	
	public String tipoSalto;
	public TercetoDO(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	
    public void setTipoSalto(String operacion,String tipo){
    	if (tipo.equals("float")) {
			if(operacion.equals("<="))
	            this.tipoSalto = "JA";
	        else
	        if(operacion.equals("=="))
	            this.tipoSalto = "JNE";
	        else
	        if(operacion.equals(">="))
	            this.tipoSalto = "JB";
	        else
	        if(operacion.equals(">"))
	            this.tipoSalto = "JBE";
	        else
	        if(operacion.equals("<"))
	            this.tipoSalto = "JAE";
	        else
	        if(operacion.equals("<>"))
	            this.tipoSalto = "JE";
    	}
    	else {
	        if(operacion.equals("<="))
	            this.tipoSalto = "JA";
	        else
	        if(operacion.equals("=="))
	            this.tipoSalto = "JNE";
	        else
	        if(operacion.equals(">="))
	            this.tipoSalto = "JB";
	        else
	        if(operacion.equals(">"))
	            this.tipoSalto = "JBE";
	        else
	        if(operacion.equals("<"))
	            this.tipoSalto = "JAE";
	        else
	        if(operacion.equals("<>"))
	            this.tipoSalto = "JE";
    	}
    };
	@Override
	public String getCodigoAssembler() {
			int numLabel = Integer.parseInt(listTerceto.get(1).getLexema().substring(1));
			AnalizadorTercetos.listLabel.add(numLabel);
			return(tipoSalto +" Lbl" + numLabel + System.lineSeparator());
	}

}
