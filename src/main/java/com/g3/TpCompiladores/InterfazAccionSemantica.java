package com.g3.TpCompiladores;

public interface InterfazAccionSemantica {

	public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens);
	
	public class ASAgregar implements InterfazAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens) {
			cadena.append(caracter);
			return tablaTokens.getToken(cadena.toString());
		}
		
	}
	
	public class ASChequearCadena implements InterfazAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens) {
			
			
			return 0;
		}
		
	}
	
	// HAY QUE HACER UNA ACCION SEMANTICA PARA LA \N QUE AUMENTE EL NROD E LINEA 
	
	
}
