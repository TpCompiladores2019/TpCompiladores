package CodigoIntermedio;

import java.io.FileWriter;
import java.io.IOException;

import Lexico.TablaSimbolos;

public class GeneradorAssembler {
	private AnalizadorTercetos analizadorTercetos;
	private TablaSimbolos tablaSimbolos;
	private FileWriter fw;	
	
	public static final String labelDivCero = "_LabelDividirCero";
	public static final String labelOverflowSuma = "_LabelOverflowSuma";
	public static final String labelSubIndices = "_LabelSubIndices";
	
	public GeneradorAssembler(AnalizadorTercetos analizadorTercetos, TablaSimbolos tablaSimbolos) {
		this.analizadorTercetos=analizadorTercetos;
		this.tablaSimbolos=tablaSimbolos;
	}
	
    public void generarAssembler() throws IOException {
    	fw = new FileWriter("salida.asm");
    	escribirCodigo();
    }

	private void escribirCodigo() throws IOException {
		StringBuilder codigo = new StringBuilder();

		codigo.append(".386" + '\n');
		codigo.append(".model flat, stdcall" + '\n');
		codigo.append(".stack 200h" + '\n');
		codigo.append("option casemap :none" + '\n');
		codigo.append("include \\masm32\\include\\masm32rt.inc" + '\n');
		codigo.append("dll_dllcrt0 PROTO C" + '\n');
		codigo.append("printf PROTO C : VARARG" + '\n');
	    codigo.append('\n' +".data" + '\n');
		codigo.append(tablaSimbolos.getDataAssembler());
		codigo.append("auxiliarFloat DQ ?" + '\n');
		codigo.append("auxiliarInt DW ?" + '\n');
		codigo.append("MayorNumFloatPos DQ 3.4028235E38" + '\n');
		codigo.append("MenorNumFloatNeg DQ -3.4028235E38" + '\n');
		codigo.append(labelDivCero + " DB \"Error al dividir por cero!\", 0" + '\n');
		codigo.append(labelOverflowSuma + " DB \"La suma ha generado un Overflow!\", 0" + '\n');
		codigo.append(labelSubIndices + " DB \"Subindice fuera de rango!\", 0" + '\n');
        
		codigo.append('\n' + ".code"+ "\n");
		codigo.append("FUNCION_LENGTH:" + '\n');
		codigo.append("    MOV AX, [ECX] \n"); 
		codigo.append("    MOV auxiliarInt, AX \n");
		codigo.append("    RET" + '\n');
		codigo.append("FUNCION_FIRSTINT: \n");
		codigo.append("    MOV AX, [ECX + 2] \n");
		codigo.append("    MOV auxiliarInt, AX \n");
		codigo.append("    RET " + '\n');
		codigo.append("FUNCION_LASTINT: \n");
		codigo.append("    MOV AX, [ECX]  \n");
		codigo.append("    IMUL AX, 2 \n");
		codigo.append("    MOVZX EAX, AX \n");
		codigo.append("    MOV AX, [ECX + EAX] \n");
		codigo.append("    MOV auxiliarInt, AX \n");
		codigo.append("    RET " + '\n');
		codigo.append("FUNCION_FIRSTFLOAT: \n");
		codigo.append("    FLD DWORD PTR [ECX + 8] \n");
		codigo.append("    STP auxiliarFloat \n");
		codigo.append("    RET " + '\n');
		codigo.append("FUNCION_LASTFLOAT: \n");
		codigo.append("    MOVE AX, [ECX] \n");
		codigo.append("    IMUL AX, 8 \n");
		codigo.append("    MOVZX EAX, AX \n");
		codigo.append("    FLD DWORD PTR [ECX + EAX] \n");
		codigo.append("    FST auxiliarFloat \n");
		codigo.append("    RET " + '\n');
		codigo.append("start:" + '\n');
		codigo.append(analizadorTercetos.getCodeString() + '\n');
		codigo.append("invoke ExitProcess, 0" + '\n');
        
		codigo.append("DividirCero" + ":" + '\n');
		codigo.append("invoke MessageBox, NULL, addr "+labelDivCero+", addr "+labelDivCero+", MB_OK" + '\n');
		codigo.append("invoke ExitProcess, 0" + '\n');
		codigo.append("LabelOverflowSuma" + ":" + '\n');
		codigo.append("invoke MessageBox, NULL, addr "+labelOverflowSuma+", addr "+labelOverflowSuma+", MB_OK" + '\n');
		codigo.append("invoke ExitProcess, 0" + '\n');
		codigo.append("LabelSubIndices" + ":" + '\n');
		codigo.append("invoke MessageBox, NULL, addr "+labelSubIndices+", addr "+labelSubIndices+", MB_OK" + '\n');
		codigo.append("invoke ExitProcess, 0" + '\n');
		codigo.append("end start");
		fw.write(codigo.toString());
		fw.close();
			   
	}
}
