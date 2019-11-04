package com.g3.TpCompiladores;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class MainLexico {

	public static void main(String[] args) throws IOException {
			Compilador compilador = new Compilador(args[0]);
			compilador.mostrarInfoLexico();
	}
	
}
