package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.Registro;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;

public abstract class IAccionSemantica {
	

	public abstract int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos);
	
	
	public static class ASAgregar extends IAccionSemantica {
		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			cadena.append(caracter);
			//return tablaTokens.getToken(cadena.toString());  NO VA PORQUE SI TENEMOS: palabra y palabras, simepre corta en la mas corta
			return -1;
		}
	}
	
	public static class ASAumentarNumLinea extends IAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.nroLinea++;
			return -1;
		}

	}
	
	public static class ASCerrarComentario extends IAccionSemantica {

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.comentarioAbierto = false;
			return -1;
		}

	}
	

	public static class ASConsumirComentario extends IAccionSemantica {
	
		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.indiceLectura--;
			AnalizadorLexico.comentarioAbierto = true;
			cadena.delete(0,cadena.length());
			return -1;
		}
		
	}

	public static class ASError extends IAccionSemantica {


		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			Error nuevoError = new Error("Token ",AnalizadorLexico.nroLinea,"'"+caracter+"' invalido","ERROR");
			AnalizadorLexico.listaErrores.add(nuevoError);
			if (cadena.length() != 0) {// es porque la cadena quiere arrancar con este caracter
				IAccionSemantica ASaux = new ASFinalID();
				ASaux.ejecutar(caracter, cadena, tablaTokens, tablaSimbolos);
				AnalizadorLexico.indiceLectura++;
			}
			return -1;
		}

	}
	
	public static class ASErrorCadenaMultilinea extends IAccionSemantica {

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			cadena.delete(0, cadena.length());
			Error nuevoError = new Error("La cadena no permite salto de linea",AnalizadorLexico.nroLinea,"","ERROR");
			AnalizadorLexico.listaErrores.add(nuevoError);	
			AnalizadorLexico.nroLinea++;
			return -1;
		}

	}

	public static class ASErrorCaracterFaltante extends IAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			
			String simboloEsperado = new String();
			char ultimoChar = cadena.charAt(cadena.length()-1);
			if ( ultimoChar == '.')
				simboloEsperado = "un digito o un identificador";		
			else if ( (ultimoChar == 'E') || (ultimoChar == 'e') || (ultimoChar == '+') || (ultimoChar == '-'))
				simboloEsperado="un digito";
			else
				simboloEsperado = "=";
			
		
			
			Error nuevoError = new Error("Se espera " + simboloEsperado+ " luego de ",AnalizadorLexico.nroLinea,"'"+ultimoChar+"'","ERROR");
			AnalizadorLexico.listaErrores.add(nuevoError);
			AnalizadorLexico.indiceLectura--;
			
			cadena.delete(0, cadena.length());
			return -1;
		}

	}
	
	public static class ASFinalCadena extends IAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		

			tablaSimbolos.agregar(cadena.toString(),new Registro("Cadena"));
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Cadena: " + cadena.toString());

			return tablaTokens.getToken("CADENA");
		}

	}
	
	public static class ASFinalEntero extends IAccionSemantica {

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.indiceLectura--;
			Short nro;
			try {
				nro = Short.parseShort(cadena.toString());
				AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Entera: " + nro.toString());
			}catch (Exception e) {
				Error nuevoWarning = new Error("El numero " + cadena.toString() + " excede el mayor entero posible, se trunca a 32768. ",AnalizadorLexico.nroLinea," ","WARNING");
				AnalizadorLexico.listaWarning.add(nuevoWarning);
				cadena.delete(0, cadena.length());
				cadena.append("32768");
			}	
			tablaSimbolos.agregar(cadena.toString(),new Registro("int"));
			return tablaTokens.getToken("CONSTANTE E");
		}
		
	}
	
	public static class ASFinalFloat extends IAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.indiceLectura--;
			Double numeroFloat = Double.parseDouble(cadena.toString());
			if (numeroFloat > Float.MAX_VALUE) {
				Error nuevoWarning = new Error("El numero " + cadena.toString() + " excede el rango de los Float, fue truncado a " + Float.MAX_VALUE +".",AnalizadorLexico.nroLinea," ","WARNING");
				AnalizadorLexico.listaWarning.add(nuevoWarning);
				cadena.delete(0, cadena.length());
				cadena.append(String.valueOf(Float.MAX_VALUE));
			}
			else
				if ((numeroFloat<Float.MIN_NORMAL) && (numeroFloat != 0.0)) {
					Error nuevoWarning = new Error("El numero " + cadena.toString() + " excede el rango de los Float, fue truncado a " + Float.MIN_NORMAL + ".",AnalizadorLexico.nroLinea," ","WARNING");
					AnalizadorLexico.listaWarning.add(nuevoWarning);
					cadena.delete(0, cadena.length());
					cadena.append(String.valueOf(Float.MIN_NORMAL));
				}
			
			tablaSimbolos.agregar(cadena.toString(),new Registro("float"));
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Float: " + cadena.toString());
			return tablaTokens.getToken("CONSTANTE F");
		}

	}
	
	public static class ASFinalID extends IAccionSemantica{

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.indiceLectura--; // 
			String salida;
			if (cadena.length() > 25) {
				salida = cadena.substring(0, 24);
				Error w = new Error("La variable "+ cadena + " fue truncada",AnalizadorLexico.nroLinea,"","WARNING");
				AnalizadorLexico.listaWarning.add(w);
			}
			else
				salida = cadena.toString();	

					
			if (tablaTokens.contieneClave(salida)) { 
				AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Palabra Reservada: " + cadena.toString());
				return tablaTokens.getToken(salida);
			}

			
			tablaSimbolos.agregar(salida,new Registro("")); 
			
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Identificador: " + salida);

			return tablaTokens.getToken("IDENTIFICADOR");
			}
		
	}
	
	public static class ASFinalOperAsigComp extends IAccionSemantica {

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			cadena.append(caracter);
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + ": " + cadena.toString());
			return tablaTokens.getToken(cadena.toString());
		}

	}

	
	public static class ASFinalSimple extends IAccionSemantica {

		public int ejecutar(char caracter, StringBuilder cadena, TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			AnalizadorLexico.indiceLectura--;
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + ": " + cadena.toString());
			return tablaTokens.getToken(cadena.toString());
		}

	}

	
	
}
      