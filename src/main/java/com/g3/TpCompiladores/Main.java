package com.g3.TpCompiladores;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
			if (args.length != 0) {
				File f = new File(args[0]);
				if (f.exists()) {
					
					Compilador compilador = new Compilador(args[0]);
					compilador.ejecutar();
				}
				else
				System.out.println("Path No Encontrado");
			}
			else
				System.out.println("Debe ingresar un path");

	}
}
