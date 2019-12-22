package CodigoIntermedio;

import Lexico.Token;

public class TercetoExpresionDivision extends TercetoExpresion {

	public TercetoExpresionDivision(Token t1, Token t2, Token t3, int numTerceto) {
		super(t1, t2, t3, numTerceto);
		// TODO Auto-generated constructor stub
	}

	public String getCodigoAssembler() {
		StringBuilder assembler = new StringBuilder();

		String operador = "IDIV";
		String lexemaIzq = listTerceto.get(1).getLexema();
		String lexemaDer = listTerceto.get(2).getLexema();

		if (!lexemaIzq.contains("@") && (!lexemaDer.contains("@")))
			if (listTerceto.get(1).getTipo().equals("int"))// (operador,id,id)
				return "MOV AX, _" + lexemaIzq.replace('-', '@') + System.lineSeparator() 
						+ "CWD" + System.lineSeparator() + "MOV BX, _"
						+ lexemaDer.replace('-', '@') + System.lineSeparator() 
						+ "CMP BX,0" + System.lineSeparator() 
						+ "JE DividirCero" + System.lineSeparator() 
						+ "IDIV BX"
						+ System.lineSeparator() + "MOV auxiliar@" + getNumTerceto() + " , AX" + System.lineSeparator();
			else {
				assembler.append("FINIT" + System.lineSeparator());
				assembler.append("FLD _" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("FLDZ " + System.lineSeparator());
				assembler.append("FCOMP" + System.lineSeparator());
				assembler.append("FSTSW AX" + System.lineSeparator());
				assembler.append("SAHF" + System.lineSeparator());
				assembler.append("JE DividirCero" + System.lineSeparator());
				assembler.append("FDIV " + System.lineSeparator());
				assembler.append("FSTP auxiliar@" + getNumTerceto() + System.lineSeparator());

			}
		else if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,terceto,id)
			if (listTerceto.get(1).getTipo().equals("int"))
				return "MOV AX, auxiliar" + lexemaIzq + System.lineSeparator() 
						+ "CWD" + System.lineSeparator() 
						+ "MOV BX, _"
						+ lexemaDer.replace('-', '@') + System.lineSeparator() 
						+ "CMP BX,0" + System.lineSeparator() 
						+ "JE DividirCero" + System.lineSeparator() 
						+ "IDIV BX" + System.lineSeparator() 
						+ "MOV auxiliar@" + getNumTerceto() + " , AX" + System.lineSeparator();
			else {
				assembler.append("FINIT" + System.lineSeparator());
				assembler.append("FLD auxiliar" + lexemaIzq + System.lineSeparator());
				assembler.append("FLD " + "_" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("FLDZ" + System.lineSeparator());
				assembler.append("FCOMP" + System.lineSeparator());
				assembler.append("FSTSW AX" + System.lineSeparator());
				assembler.append("SAHF" + System.lineSeparator());
				assembler.append("JE DividirCero" + System.lineSeparator());
				assembler.append("FDIV " + System.lineSeparator());
				assembler.append("FSTP auxiliar@" + getNumTerceto() + System.lineSeparator());
			}
		else if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,terceto)
			if (listTerceto.get(1).getTipo().equals("int")) {
				return "MOV AX, _" + lexemaIzq.replace('-', '@') + System.lineSeparator() 
						+ "CWD" + System.lineSeparator() 
						+ "MOV BX, auxiliar" + lexemaDer + System.lineSeparator() 
						+ "CMP BX,0" + System.lineSeparator() 
						+ "JE DividirCero" + System.lineSeparator() 
						+ "IDIV BX" + System.lineSeparator()
						+ "MOV auxiliar@" + getNumTerceto() + " , AX" + System.lineSeparator();
			} else {
				assembler.append("FINIT" + System.lineSeparator());
				assembler.append("FLD auxiliar" + lexemaIzq + System.lineSeparator());
				assembler.append("FLD _" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + System.lineSeparator());
				assembler.append("FCOMP" + System.lineSeparator());
				assembler.append("FSTSW AX" + System.lineSeparator());
				assembler.append("SAHF" + System.lineSeparator());
				assembler.append("JE DividirCero" + System.lineSeparator());
				assembler.append("FDIV " + System.lineSeparator());
				assembler.append("FSTP auxiliar@" + getNumTerceto() + System.lineSeparator());
			}
		else // (operador,terceto,terceto)
		if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))
			if (listTerceto.get(1).getTipo().equals("int"))
				return "MOV AX, auxiliar" + lexemaIzq + System.lineSeparator() 
						+ "CWD" + System.lineSeparator() 
						+ "MOV BX, auxiliar" + lexemaDer + System.lineSeparator()
						+ "CMP BX,0" + System.lineSeparator() 
						+ "JE DividirCero" + System.lineSeparator() 
						+ "IDIV EBX" + System.lineSeparator() 
						+ "MOV auxiliar@" + getNumTerceto() + " , AX" + System.lineSeparator();

			else {
				assembler.append("FINIT" + System.lineSeparator());
				assembler.append("FLD auxiliar" + lexemaIzq + System.lineSeparator());
				assembler.append("FLD auxiliar" + lexemaDer + System.lineSeparator());
				assembler.append("FCOMP" + System.lineSeparator());
				assembler.append("FSTSW AX" + System.lineSeparator());
				assembler.append("SAHF" + System.lineSeparator());
				assembler.append("JE DividirCero" + System.lineSeparator());
				assembler.append("FDIV " + System.lineSeparator());
				assembler.append("FSTP auxiliar@" + getNumTerceto() + System.lineSeparator());
			}
		return assembler.toString();

	}

}
