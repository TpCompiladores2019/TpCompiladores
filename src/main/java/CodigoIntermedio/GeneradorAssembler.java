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
		String codigo = "";
		
		codigo = ".386" + '\n'
			    + ".model flat, stdcall" + '\n'
			    + ".stack 200h" + '\n'
			    + "option casemap :none" + '\n'
			    + "include \\masm32\\include\\windows.inc" + '\n'
			    + "include \\masm32\\include\\kernel32.inc" + '\n'
			    + "include \\masm32\\include\\user32.inc" + '\n'
			    + "include \\masm32\\include\\masm32.inc" + '\n'
			    + "includelib \\masm32\\lib\\kernel32.lib" + '\n'
			    + "includelib \\masm32\\lib\\user32.lib" + '\n'
			    + "includelib \\masm32\\lib\\masm32.inc" + '\n'
			    + '\n' +".data" + '\n';
		codigo = codigo + tablaSimbolos.getDataAssembler();
        codigo = codigo + labelDivCero + " DB \"Error al dividir por cero!\", 0" + '\n';
        codigo = codigo + labelOverflowSuma + " DB \"La suma ha generado un Overflow!\", 0" + '\n';
        codigo = codigo + labelSubIndices + " DB \"Subindice fuera de rango!\", 0" + '\n';
        
        codigo = codigo + '\n' + ".code"+ "\n";
        
        codigo = codigo + "start:" + '\n' ;
        codigo = codigo + analizadorTercetos.getCodeString() + '\n';
        codigo = codigo + "invoke ExitProcess, 0" + '\n';
        
        codigo = codigo + "DividirCero" + ":" + '\n';
        codigo = codigo + "invoke MessageBox, NULL, addr "+labelDivCero+", addr "+labelDivCero+", MB_OK" + '\n';
        codigo = codigo + "invoke ExitProcess, 0" + '\n';
        codigo = codigo + "LabelOverflowSuma" + ":" + '\n';
        codigo = codigo + "invoke MessageBox, NULL, addr "+labelOverflowSuma+", addr "+labelOverflowSuma+", MB_OK" + '\n';
        codigo = codigo + "invoke ExitProcess, 0" + '\n';
        codigo = codigo + "LabelSubIndices" + ":" + '\n';
        codigo = codigo + "invoke MessageBox, NULL, addr "+labelSubIndices+", addr "+labelSubIndices+", MB_OK" + '\n';
        codigo = codigo + "invoke ExitProcess, 0" + '\n';
        codigo = codigo + "end start";
		fw.write(codigo);
		fw.close();
			   
	}
}
