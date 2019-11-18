package CodigoIntermedio;

import Lexico.Token;

public class TercetoPrint extends TercetoAbstracto{
	String print = "";
	
	public TercetoPrint(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}
	
	
	public void setPrint (String print) {
		this.print = print;
	}
	@Override
	public String getCodigoAssembler() {
		print = listTerceto.get(1).getLexema();
		print = print.replace(' ', '@');
		
        String assembler = "invoke MessageBox, NULL, addr "+ "_" +print +", addr "+ "_" +print+", MB_OK \n";
        return assembler;
	}

}

