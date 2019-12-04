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
		String lexemaPrint = listTerceto.get(1).getLexema();
		Token tPrint = listTerceto.get(1);
		print = listTerceto.get(1).getLexema();
		print = print.replace(' ', '@');
		String assembler = ""; 
		if (lexemaPrint.contains("@")){
			if (tPrint.getTipo().equals("int"))
				assembler = "invoke printf , cfm$(\"%d\\n\"), auxiliar" + lexemaPrint;
			else
				assembler = "invoke printf , cfm$(\"%.20f\\n\"), auxiliar" + lexemaPrint;
		}
		else
			if (tPrint.getTipo()!= null)
				if (tPrint.getTipo().equals("int")) 
					assembler = "invoke printf , cfm$(\"%d\\n\"), _" + lexemaPrint.replace('-', '@');
				else
					assembler = "invoke printf , cfm$(\"%.20f\\n\"), _" + lexemaPrint.replace('-', '@').replace('.', '@');
			else
				assembler = "invoke MessageBox, NULL, addr "+ "_" +print +", addr "+ "_" +print+", MB_OK \n";
        return assembler;
	}

}
