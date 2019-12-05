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
				return "MOV AX, _" + lexemaIzq.replace('-', '@') + '\n' 
						+ "CWD" + '\n' + "MOV BX, _"
						+ lexemaDer.replace('-', '@') + '\n' 
						+ "CMP BX,0" + '\n' 
						+ "JE DividirCero" + '\n' 
						+ "IDIV BX"
						+ '\n' + "MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
			else {
				assembler.append("FINIT" + '\n');
				assembler.append("FLD " + "_" + lexemaIzq.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("FLD " + "_" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("FLDZ" + '\n');
				assembler.append("FCOMP" + '\n');
				assembler.append("FSTSW AX" + '\n');
				assembler.append("SAHF" + '\n');
				assembler.append("JE DividirCero" + '\n');
				assembler.append("FDIV " + '\n');
				assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');

			}
		else if (lexemaIzq.contains("@") && (!lexemaDer.contains("@"))) // (operador,terceto,id)
			if (listTerceto.get(1).getTipo().equals("int"))
				return "MOV AX, auxiliar" + lexemaIzq + '\n' 
						+ "CWD" + '\n' 
						+ "MOV BX, _"
						+ lexemaDer.replace('-', '@') + '\n' 
						+ "CMP BX,0" + '\n' 
						+ "JE DividirCero" + '\n' 
						+ "IDIV BX" + '\n' 
						+ "MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
			else {
				assembler.append("FINIT" + '\n');
				assembler.append("FLD " + "auxiliar" + lexemaIzq + '\n');
				assembler.append("FLD " + "_" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("FLDZ" + '\n');
				assembler.append("FCOMP" + '\n');
				assembler.append("FSTSW AX" + '\n');
				assembler.append("SAHF" + '\n');
				assembler.append("JE DividirCero" + '\n');
				assembler.append("FDIV " + '\n');
				assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
			}
		else if (!lexemaIzq.contains("@") && (lexemaDer.contains("@"))) // (operador,id,terceto)
			if (listTerceto.get(1).getTipo().equals("int")) {
				return "MOV AX, _" + lexemaIzq.replace('-', '@') + '\n' 
						+ "CWD" + '\n' 
						+ "MOV BX, auxiliar" + lexemaDer + '\n' 
						+ "CMP BX,0" + '\n' 
						+ "JE DividirCero" + '\n' 
						+ "IDIV BX" + '\n'
						+ "MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';
			} else {
				assembler.append("FINIT" + '\n');
				assembler.append("FLD " + "auxiliar" + lexemaIzq + '\n');
				assembler.append("FLD " + "_" + lexemaDer.replace('.', '@').replace('-', '@').replace('+', '@') + '\n');
				assembler.append("FCOMP" + '\n');
				assembler.append("FSTSW AX" + '\n');
				assembler.append("SAHF" + '\n');
				assembler.append("JE DividirCero" + '\n');
				assembler.append("FDIV " + '\n');
				assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
			}
		else // (operador,terceto,terceto)
		if (lexemaIzq.contains("@") && (lexemaDer.contains("@")))
			if (listTerceto.get(1).getTipo().equals("int"))
				return "MOV AX, auxiliar" + lexemaIzq + '\n' 
						+ "CWD" + '\n' 
						+ "MOV BX, auxiliar" + lexemaDer + '\n'
						+ "CMP BX,0" + '\n' 
						+ "JE DividirCero" + '\n' 
						+ "IDIV EBX" + '\n' 
						+ "MOV auxiliar@" + getNumTerceto() + " , AX" + '\n';

			else {
				assembler.append("FINIT" + '\n');
				assembler.append("FLD " + "auxiliar" + lexemaIzq + '\n');
				assembler.append("FLD " + "auxiliar" + lexemaDer + '\n');
				assembler.append("FCOMP" + '\n');
				assembler.append("FSTSW AX" + '\n');
				assembler.append("SAHF" + '\n');
				assembler.append("JE DividirCero" + '\n');
				assembler.append("FDIV " + '\n');
				assembler.append("FST " + "auxiliar@" + getNumTerceto() + '\n');
			}
		return assembler.toString();

	}

}
