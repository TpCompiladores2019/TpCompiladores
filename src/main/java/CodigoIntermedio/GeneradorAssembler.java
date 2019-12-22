package CodigoIntermedio;

import java.io.FileWriter;
import java.io.IOException;

import Lexico.TablaSimbolos;

public class GeneradorAssembler {
	private AnalizadorTercetos analizadorTercetos;
	private TablaSimbolos tablaSimbolos;
	private FileWriter fw;
	private String path;
	
	public static final String labelDivCero = "_LabelDividirCero";
	public static final String labelOverflowSuma = "_LabelOverflowSuma";
	public static final String labelSubIndices = "_LabelSubIndices";
	
	public GeneradorAssembler(AnalizadorTercetos analizadorTercetos, TablaSimbolos tablaSimbolos, String path) {
		this.analizadorTercetos=analizadorTercetos;
		this.tablaSimbolos=tablaSimbolos;
		this.path = path;
	}
	
    public void generarAssembler() throws IOException {
    	int i = path.indexOf('.');
    	path = path.substring(0, i+1);
    	path = path + "asm";
    	fw = new FileWriter(path);
    	escribirCodigo();
    }

	private void escribirCodigo() throws IOException {
		StringBuilder codigo = new StringBuilder();

		codigo.append(".386" + System.lineSeparator());
		codigo.append(".model flat, stdcall" + System.lineSeparator());
		codigo.append(".stack 200h" + System.lineSeparator());
		codigo.append("option casemap :none" + System.lineSeparator());
		codigo.append("include \\masm32\\include\\masm32rt.inc" + System.lineSeparator());
		codigo.append("dll_dllcrt0 PROTO C" + System.lineSeparator());
		codigo.append("printf PROTO C : VARARG" + System.lineSeparator());
	    codigo.append(System.lineSeparator() +".data" + System.lineSeparator());
		codigo.append(tablaSimbolos.getDataAssembler());
		codigo.append("auxiliarFloat DQ ?" + System.lineSeparator());
		codigo.append("auxiliarInt DW ?" + System.lineSeparator());
		codigo.append("MayorNumFloatPos DQ 3.4028235E38" + System.lineSeparator());
		codigo.append("MenorNumFloatNeg DQ -3.4028235E38" + System.lineSeparator());
		codigo.append(labelDivCero + " DB \"Error al dividir por cero!\", 0" + System.lineSeparator());
		codigo.append(labelOverflowSuma + " DB \"La suma ha generado un Overflow!\", 0" + System.lineSeparator());
		codigo.append(labelSubIndices + " DB \"Subindice fuera de rango!\", 0" + System.lineSeparator());
        
		codigo.append(System.lineSeparator() + ".code"+ System.lineSeparator());
		codigo.append("FUNCION_LENGTH:" + System.lineSeparator());
		codigo.append("    MOV AX, [ECX] " + System.lineSeparator()); 
		codigo.append("    MOV auxiliarInt, AX " + System.lineSeparator());
		codigo.append("    RET" + System.lineSeparator());
		codigo.append("FUNCION_FIRSTINT: " + System.lineSeparator());
		codigo.append("    MOV AX, [ECX + 2] " + System.lineSeparator());
		codigo.append("    MOV auxiliarInt, AX " + System.lineSeparator());
		codigo.append("    RET " + System.lineSeparator());
		codigo.append("FUNCION_LASTINT: " + System.lineSeparator());
		codigo.append("    MOV AX, [ECX]  " + System.lineSeparator());
		codigo.append("    IMUL AX, 2 " + System.lineSeparator());
		codigo.append("    MOVZX EAX, AX " + System.lineSeparator());
		codigo.append("    MOV AX, [ECX + EAX] " + System.lineSeparator());
		codigo.append("    MOV auxiliarInt, AX " + System.lineSeparator());
		codigo.append("    RET " + System.lineSeparator());
		codigo.append("FUNCION_FIRSTFLOAT: " + System.lineSeparator());
		codigo.append("    FLD DWORD PTR [ECX + 8] " + System.lineSeparator());
		codigo.append("    FST auxiliarFloat " + System.lineSeparator());
		codigo.append("    RET " + System.lineSeparator());
		codigo.append("FUNCION_LASTFLOAT: " + System.lineSeparator());
		codigo.append("    MOV AX, [ECX] " + System.lineSeparator());
		codigo.append("    IMUL AX, 8 " + System.lineSeparator());
		codigo.append("    MOVZX EAX, AX " + System.lineSeparator());
		codigo.append("    FLD DWORD PTR [ECX + EAX] " + System.lineSeparator());
		codigo.append("    FST auxiliarFloat " + System.lineSeparator());
		codigo.append("    RET " + System.lineSeparator());
		codigo.append("start:" + System.lineSeparator());
		codigo.append(analizadorTercetos.getCodeString() + System.lineSeparator());
		codigo.append("invoke ExitProcess, 0" + System.lineSeparator());
        
		codigo.append("DividirCero" + ":" + System.lineSeparator());
		codigo.append("invoke MessageBox, NULL, addr "+labelDivCero+", addr "+labelDivCero+", MB_OK" + System.lineSeparator());
		codigo.append("invoke ExitProcess, 0" + System.lineSeparator());
		codigo.append("LabelOverflowSuma" + ":" + System.lineSeparator());
		codigo.append("invoke MessageBox, NULL, addr "+labelOverflowSuma+", addr "+labelOverflowSuma+", MB_OK" + System.lineSeparator());
		codigo.append("invoke ExitProcess, 0" + System.lineSeparator());
		codigo.append("LabelSubIndices" + ":" + System.lineSeparator());
		codigo.append("invoke MessageBox, NULL, addr "+labelSubIndices+", addr "+labelSubIndices+", MB_OK" + System.lineSeparator());
		codigo.append("invoke ExitProcess, 0" + System.lineSeparator());
		codigo.append("end start");
		fw.write(codigo.toString());
		fw.close();
			   
	}
}
