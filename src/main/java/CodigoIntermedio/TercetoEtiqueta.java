package CodigoIntermedio;

import Lexico.Token;

public class TercetoEtiqueta extends TercetoAbstracto{

	public TercetoEtiqueta(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCodigoAssembler() {
		String label = listTerceto.get(0).getLexema();
		return label +":" + '\n';
	}

}
