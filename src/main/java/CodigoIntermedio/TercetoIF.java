package CodigoIntermedio;

import Lexico.Token;

public class TercetoIF extends TercetoAbstracto{
	public static final String etiquetaSaltoIncondicional = "JMP ";
	private String tipoSalto = "";
	public TercetoIF(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}
	
	public void setTipoSalto(String operacion) {

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
		System.out.println(tipoSalto);
	}

	@Override
	public String getCodigoAssembler() {
		String operador = listTerceto.get(0).getLexema();

		if (operador.equals("BF")) {
			int numLabel = Integer.parseInt(listTerceto.get(2).getLexema().substring(1));
			AnalizadorTercetos.listLabel.add(numLabel);
			return(tipoSalto +" Label" + numLabel + '\n');
		}
		String assembler ="";
		int numLabel = Integer.parseInt(listTerceto.get(1).getLexema().substring(1));
		assembler = assembler + etiquetaSaltoIncondicional + "Label" + numLabel + '\n';
		assembler =	assembler + "Label" + String.valueOf(AnalizadorTercetos.listLabel.remove(AnalizadorTercetos.listLabel.size()-1)) + ":" + '\n';
		AnalizadorTercetos.listLabel.add(numLabel);
		return assembler;
			
	}

}
