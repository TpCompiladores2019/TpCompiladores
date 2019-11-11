package AccionesSemanticas;

import Lexico.AnalizadorLexico;
import Lexico.Error;
import Lexico.TablaSimbolos;
import Lexico.TablaTokens;
import Lexico.Token;

public abstract class IAccionSemantica {
	public static final int OTROS = -1;
	public static final String CTE = "CONSTANTE E";
	public static final String CTF = "CONSTANTE F";
	public static final String CADENA = "CADENA";
	public static final String ID = "IDENTIFICADOR";

	private static TablaTokens tablaTokens;
	private static TablaSimbolos tablaSimbolos;
	
	
	public IAccionSemantica(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
		this.tablaSimbolos=tablaSimbolos;
		this.tablaTokens=tablaTokens;
	}
	
	
	public abstract int ejecutar(char caracter, StringBuilder cadena);
	
	
	public static class ASAgregar extends IAccionSemantica {
		public ASAgregar(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
			// TODO Auto-generated constructor stub
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			cadena.append(caracter);
			return OTROS;
		}
	}
	
	public static class ASAumentarNumLinea extends IAccionSemantica{

		public ASAumentarNumLinea(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			AnalizadorLexico.nroLinea++;
			return OTROS;
		}

	}
	
	public static class ASCerrarComentario extends IAccionSemantica {

		public ASCerrarComentario(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			AnalizadorLexico.comentarioAbierto = false;
			return OTROS;
		}

	}
	

	public static class ASConsumirComentario extends IAccionSemantica {
	
		public ASConsumirComentario(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			AnalizadorLexico.indiceLectura--;
			AnalizadorLexico.comentarioAbierto = true;
			cadena.delete(0,cadena.length());
			return OTROS;
		}
		
	}

	public static class ASError extends IAccionSemantica {


		public ASError(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			Error nuevoError = new Error("Token ",AnalizadorLexico.nroLinea,"'"+caracter+"' invalido","ERROR");
			AnalizadorLexico.listaErrores.add(nuevoError);
			if (cadena.length() != 0) {
				IAccionSemantica ASaux = new ASFinalID(tablaTokens,tablaSimbolos);
				ASaux.ejecutar(caracter, cadena);
				AnalizadorLexico.indiceLectura++;
			}
			return OTROS;
		}

	}
	
	public static class ASErrorCadenaMultilinea extends IAccionSemantica {

		public ASErrorCadenaMultilinea(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			tablaSimbolos.agregar(cadena.toString(),new Token("Cadena",1));
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Cadena: " + cadena.toString());
			Error nuevoError = new Error("La cadena no fue cerrada correctamente",AnalizadorLexico.nroLinea,"","WARNING");//Chequear
			AnalizadorLexico.listaWarning.add(nuevoError);	
			AnalizadorLexico.nroLinea++;
			AnalizadorLexico.porcentajeAbierto=false;
			return tablaTokens.getToken(CADENA);	
		}	
	}

	public static class ASErrorCaracterFaltante extends IAccionSemantica{

		public ASErrorCaracterFaltante(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			
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
			return OTROS;
		}

	}
	
	public static class ASFinalCadena extends IAccionSemantica{

		public ASFinalCadena(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
		

			tablaSimbolos.agregar(cadena.toString(),new Token("Cadena",1));
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Cadena: " + cadena.toString());
			AnalizadorLexico.porcentajeAbierto=false;
			return tablaTokens.getToken(CADENA);
		}

	}
	
	public static class ASFinalEntero extends IAccionSemantica {

		public ASFinalEntero(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
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
			tablaSimbolos.agregar(cadena.toString(),new Token("int",1));
			return tablaTokens.getToken(CTE);
		}
		
	}
	
	public static class ASFinalFloat extends IAccionSemantica{

		public ASFinalFloat(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
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
			
			tablaSimbolos.agregar(cadena.toString(),new Token("float",1));
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Constante Float: " + cadena.toString());
			return tablaTokens.getToken(CTF);
		}

	}
	
	public static class ASFinalID extends IAccionSemantica{

		public ASFinalID(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
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

			
			tablaSimbolos.agregar(salida,new Token("",1)); 
			
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + " Identificador: " + salida);

			return tablaTokens.getToken(ID);
			}
		
	}
	
	public static class ASFinalOperAsigComp extends IAccionSemantica {

		public ASFinalOperAsigComp(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			cadena.append(caracter);
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + ": " + cadena.toString());
			return tablaTokens.getToken(cadena.toString());
		}

	}

	
	public static class ASFinalSimple extends IAccionSemantica {

		public ASFinalSimple(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		public int ejecutar(char caracter, StringBuilder cadena) {
			AnalizadorLexico.indiceLectura--;
			AnalizadorLexico.listaCorrectas.add("Linea " +AnalizadorLexico.nroLinea + ": " + cadena.toString());
			return tablaTokens.getToken(cadena.toString());
		}

	}
	

	public static class ASActivarPorcentaje extends IAccionSemantica {
	
		public ASActivarPorcentaje(TablaTokens tablaTokens, TablaSimbolos tablaSimbolos) {
			super(tablaTokens, tablaSimbolos);
		}

		@Override
		public int ejecutar(char caracter, StringBuilder cadena) {
			AnalizadorLexico.porcentajeAbierto=true;
			return -1;
		}
		
		
	
	}

	
	
}
      