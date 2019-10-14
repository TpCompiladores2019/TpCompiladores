package com.g3.TpCompiladores;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class MainSintactico {

	public static void main(String[] args) throws IOException {
		JFileChooser file = new JFileChooser();
		file.showOpenDialog(file);
		File ruta = file.getSelectedFile();
		if (ruta != null) {
			Compilador compilador = new Compilador(ruta);
			compilador.mostrarInfoSintactico();
		}
	}
}
