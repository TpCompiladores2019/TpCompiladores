package CodigoIntermedio;

import Lexico.Token;

public class TercetoPrint extends TercetoAbstracto{
	String print = "";
	
	public TercetoPrint(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCodigoAssembler() {
        String assembler = "invoke MessageBox, NULL, addr "+ print +", addr "+print+", MB_OK \n";
        return assembler;
	}

}

