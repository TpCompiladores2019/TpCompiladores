//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "GramaticaCorreciones.y"
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
	import CodigoIntermedio.AnalizadorTercetos;
	import CodigoIntermedio.TercetoAbstracto;
	import CodigoIntermedio.TercetoAsignacion;
import CodigoIntermedio.TercetoColeccion;
import CodigoIntermedio.TercetoComparacion;
	import CodigoIntermedio.TercetoDO;
	import CodigoIntermedio.TercetoExpresionMult;
	import CodigoIntermedio.TercetoIF;
	import CodigoIntermedio.TercetoPrint;
	import CodigoIntermedio.TercetoExpresionDivision;
	import CodigoIntermedio.TercetoExpresion;
	import CodigoIntermedio.TercetoEtiqueta;
//#line 36 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE_E=258;
public final static short CTE_F=259;
public final static short CADENA=260;
public final static short ASIGNACION=261;
public final static short MAYORIGUAL=262;
public final static short MENORIGUAL=263;
public final static short DESTINTO=264;
public final static short IGUAL=265;
public final static short IF=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short PRINT=269;
public final static short INT=270;
public final static short BEGIN=271;
public final static short END=272;
public final static short DO=273;
public final static short UNTIL=274;
public final static short FLOAT=275;
public final static short FIRST=276;
public final static short LAST=277;
public final static short LENGHT=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    3,    3,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    6,    6,    7,    7,
    5,    5,    5,    5,    5,    5,    5,   12,   15,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,   13,   13,   13,   16,   16,   16,   18,
   18,   18,   19,   19,   19,   19,   19,   19,   19,   19,
   20,   20,   20,   17,   17,   17,   17,   17,   17,   14,
   14,   14,   22,    9,    9,    9,    9,    9,    9,   23,
   23,   10,   10,   10,   10,   10,   11,   11,   11,   11,
   11,   24,   24,   24,   21,   21,   21,
};
final static short yylen[] = {                            2,
    1,    1,    4,    3,    2,    2,    1,    3,    3,    2,
    2,    2,    1,    2,    1,    3,    6,    5,    2,    5,
    2,    5,    5,    2,    5,    6,    1,    1,    3,    1,
    2,    2,    2,    1,    1,    1,    1,    1,    0,    9,
    6,    7,    7,    7,    7,    7,    7,    7,    5,    5,
    5,    5,    5,    3,    2,    3,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    2,    2,    1,    2,    2,
    4,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    3,    2,    1,    6,    5,    5,    5,    5,    5,    1,
    1,    4,    3,    3,    3,    3,    4,    3,    3,    3,
    3,    1,    1,    1,    5,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   27,    0,   83,   28,    0,    0,
    1,    0,    0,   13,   15,    0,    0,    0,    0,    0,
   34,    0,    0,    0,    0,   64,   65,    0,    0,    0,
   62,   63,   68,   77,   75,   78,   79,    0,   74,   76,
    0,   38,    0,    0,   91,    0,   90,    0,    0,   11,
    0,    0,    0,    0,    0,    0,   12,    5,   14,    0,
   24,    0,    0,   19,    0,   31,   32,   33,    0,    0,
    0,    0,   80,    0,    0,    0,    0,    0,   66,   67,
   69,   70,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,   95,    0,   94,    4,    0,   93,    0,    8,
    0,    0,    0,   16,   29,  100,    0,   99,   82,    0,
    0,    0,    0,   71,    0,    0,  102,  103,  104,    0,
    0,    0,    0,   60,   61,    0,    0,    0,    0,   56,
    0,   92,    0,    3,    0,    0,    0,    0,   97,   81,
    0,    0,    0,    0,   18,   72,  107,    0,  106,    0,
   51,    0,   53,    0,    0,   52,    0,   50,    0,   49,
   23,    0,   22,    0,   25,   85,   88,    0,   87,   86,
  105,    0,    0,    0,   41,    0,    0,    0,    0,   26,
   17,   84,   46,   44,   43,    0,   42,   45,   47,   48,
    0,   40,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   14,   73,   16,   17,   18,   19,   20,
   21,   41,   42,   74,  186,   43,   44,   30,   31,   22,
   33,   23,   48,  121,
};
final static short yysindex[] = {                       -16,
  -45,  131,  -30,  156,    0,  365,    0,    0,  555,    0,
    0,  291,  367,    0,    0,  -52,  -22,  -10,   -2,   11,
    0,  -42,  302, -223,  -37,    0,    0, -191,  -17,  -11,
    0,    0,    0,    0,    0,    0,    0,  505,    0,    0,
   41,    0,  -41,  131,    0,  529,    0,   45,  -27,    0,
  386,   64,    0,   67,  409,  388,    0,    0,    0,  -85,
    0, -184,  -15,    0, -158,    0,    0,    0,    6,   10,
  408,   78,    0,  -35,   26,   29, -169,  -40,    0,    0,
    0,    0,  131,  131,    0,  131,  131,  426,  352,  426,
   81,   28,    0,   87,    0,    0,  426,    0,  432,    0,
   39, -181,   40,    0,    0,    0,   13,    0,    0,  445,
  567,  483,  567,    0,   72,   42,    0,    0,    0,   93,
   53,  -11,  -11,    0,    0, -172,  320, -157, -155,    0,
   28,    0, -153,    0,   77,   44,  -34,   79,    0,    0,
   98,  537,   99,  100,    0,    0,    0,  101,    0,  426,
    0,  426,    0,  337,  426,    0,  426,    0,  426,    0,
    0,   86,    0,   94,    0,    0,    0,  113,    0,    0,
    0, -113, -112, -111,    0, -110, -109, -108, -107,    0,
    0,    0,    0,    0,    0,  426,    0,    0,    0,    0,
 -106,    0,
};
final static short yyrindex[] = {                         0,
  597,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  163,  164,    0,    0,    0,    0,  208,  226,  246,
    0,    0,    0,    0,   38,    0,    0,    0,    0,   61,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   58,    0,
  165,    0,  711,    0,    0,  168,    0,    0,    0,    1,
    0,    0,   12,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  130,    0,  172,    0,    0,    0,    0,  174,    0,
    0,    0,    0,    0,    0,    0,  268,    0,    0,    0,
    0,    0,    0,    0,  104,    0,    0,    0,    0,    0,
    0,   84,  107,    0,    0,    0,    0,    0,    0,    0,
  151,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   23,    0,    0,    0,  190,    0,    0,
    0,    0,    0,  462,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   21,  166,  797,    0,  159,    0,    0,    0,
    0,    5,  -95,  784,    0,   18,  134,   33,    4,  819,
  152,    0,    2,    0,
};
final static int YYTABLESIZE=976;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        120,
   30,   83,   28,   84,  113,  102,   61,   47,   78,   38,
   54,   21,   53,   52,   28,  141,  143,  144,   39,   29,
   40,   65,   20,    9,  163,   83,   51,   84,   65,   39,
   86,   40,   56,   75,   76,   87,   64,   73,   62,   70,
   30,   85,   89,  104,   30,   24,  168,   94,   66,   47,
   28,   21,   83,   77,   84,   83,   67,   84,  164,   30,
   59,   92,   20,   77,  106,   25,   79,   80,  108,   68,
   83,  139,   84,  103,  136,   99,  137,   73,   73,   73,
   73,   90,   73,   57,   73,   95,  107,   75,  116,  124,
  125,  110,  148,  149,  150,  151,   73,   73,  105,   73,
   59,   59,   73,   59,   97,   59,   58,   98,  131,  155,
  156,  157,  158,  159,  160,  122,  123,  111,  114,   59,
   59,  115,   59,   57,   57,   28,   57,  132,   57,   55,
  145,  135,  138,  147,  146,  161,  162,  165,  166,  169,
  170,  171,   57,   57,  180,   57,   58,   58,   72,   58,
   54,   58,  181,  182,  183,  184,  185,  187,  188,  189,
  190,  192,    2,    7,    6,   58,   58,   10,   58,   55,
   55,   96,  101,    9,   63,   28,   91,   57,    0,   82,
    0,    0,    0,    0,    0,    0,    0,    0,   55,   89,
   54,   54,    0,    0,    0,   46,    0,    0,    0,    0,
   28,    0,    0,    0,   60,    0,    0,   35,    0,   54,
    0,   96,    0,    0,   25,   26,   27,    0,   69,    0,
   34,   35,   36,   37,    0,   36,   25,   26,   27,   89,
   96,   34,   35,   36,   37,  117,  118,  119,  112,    0,
    1,    0,    0,    0,    2,   37,    0,   35,   89,    3,
    0,    0,    4,    5,    6,    0,    7,   30,    8,    0,
    0,   30,   25,   26,   27,   36,   30,  101,   21,   30,
   30,   30,   21,   30,    0,   30,    0,   21,    0,   20,
   21,   21,   21,   20,   21,   37,   21,    0,   20,    0,
    0,   20,   20,   20,   73,   20,    0,   20,   73,   73,
   73,   73,   73,   73,   73,   73,   73,  101,   73,   73,
   73,   73,    0,    0,   73,   73,   73,   59,   73,    0,
    0,   59,   59,   59,   59,   59,   59,   59,   59,   59,
    9,   59,   59,   59,   59,    0,  130,   25,   26,   27,
   57,    9,    0,    0,   57,   57,   57,   57,   57,   57,
   57,   57,   57,    0,   57,   57,   57,   57,    0,    9,
   72,   72,   72,   58,   72,    0,    0,   58,   58,   58,
   58,   58,   58,   58,   58,   58,    9,   58,   58,   58,
   58,    0,    0,    0,    0,    0,   55,   25,   26,   27,
   55,    9,  127,    0,    0,   55,   55,   55,   55,    0,
   55,   55,   55,   55,    9,    0,    9,   54,    0,    0,
    0,   54,   25,   26,   27,   45,   54,   54,   54,   54,
    0,   54,   54,   54,   54,    9,    0,    9,   96,    0,
    0,    0,   96,    0,    0,    0,    0,   96,   96,   96,
   96,    0,   96,   96,   96,   96,   89,    9,    9,    0,
   89,    0,    0,    0,    0,   89,   89,   89,   89,    0,
   89,   89,   89,   89,   35,    9,    0,    0,   35,    0,
    0,    9,    0,   35,   35,   35,   35,    0,   35,   35,
   35,   35,   36,    0,    9,    0,   36,    0,    0,    0,
    0,   36,   36,   36,   36,    0,   36,   36,   36,   36,
    0,   39,   37,    0,    0,    0,   37,    0,    0,    0,
    0,   37,   37,   37,   37,    0,   37,   37,   37,   37,
    0,    0,  142,    0,  101,    0,    0,   28,  101,    0,
    0,    0,    0,  101,  101,  101,  101,    0,  101,  101,
  101,  101,   39,    0,   40,   88,    0,    1,    0,   28,
    0,    2,    0,    0,    0,    0,    3,    0,   49,    4,
    5,   55,    2,    7,   39,    8,   40,    3,    0,   93,
    4,    0,   71,   28,    7,   72,   49,  167,    0,    0,
    2,   28,    0,    0,    0,    3,  152,  153,    4,    0,
   71,    0,    7,   49,    0,    0,   39,    2,   40,   28,
    0,    0,    3,  174,  175,    4,    0,   71,   49,    7,
    0,   28,    2,    0,   39,    0,   40,    3,    0,    0,
    4,   49,   71,   49,    7,    2,   39,    2,   40,    0,
    3,    0,    3,    4,    0,    4,   50,    7,   58,    7,
   30,   73,   49,    0,   49,    0,    2,    0,    2,    0,
    0,    3,    0,    3,    4,   30,    4,   96,    7,  100,
    7,    0,    0,    0,   49,   49,    0,    0,    2,    2,
    0,    0,    0,    3,    3,    0,    4,    4,    0,  109,
    7,    7,   49,    0,    0,    0,    2,    0,   49,    0,
    0,    3,    2,    0,    4,    0,   71,    3,    7,    0,
    4,   49,    0,  134,    7,    2,    0,    0,    0,    0,
    3,    0,    0,    4,    0,    0,  140,    7,   39,    0,
    0,    0,   39,    0,    0,    0,    0,   39,    0,    0,
   39,    0,   39,    0,   39,    0,    0,    0,    0,   25,
   26,   27,    0,    0,   34,   35,   36,   37,    0,    0,
    0,   90,   62,   62,    0,   62,    0,   62,    0,    0,
    0,   25,   26,   27,    0,    0,   34,   35,   36,   37,
   62,    0,   62,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   25,   26,   27,   45,    0,
    0,    0,    0,   25,   26,   27,   15,    0,   34,   35,
   36,   37,   15,    0,    0,    0,    0,    0,   15,   59,
    0,   25,   26,   27,   45,    0,   34,   35,   36,   37,
   32,   32,   32,   25,   26,   27,    0,   32,   34,   35,
   36,   37,    0,    0,    0,    0,    0,    0,    0,    0,
   32,    0,    0,    0,    0,    0,   81,   59,    0,    0,
    0,   15,   59,   73,   73,   73,   32,   73,    0,    0,
    0,    0,   32,    0,   32,    0,    0,   15,    0,    0,
    0,  126,  128,  129,    0,    0,    0,    0,    0,    0,
  133,    0,    0,    0,    0,    0,    0,   32,    0,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
    0,   32,   32,    0,   32,   32,   59,    0,    0,   32,
  154,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   32,
   32,   32,    0,  172,    0,  173,    0,  176,  177,    0,
  178,    0,  179,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   32,    0,    0,    0,    0,    0,    0,    0,    0,  191,
    0,    0,   62,   62,   62,   62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   45,   45,   40,   91,   59,    4,   46,   40,
    9,    0,    9,    9,   45,  111,  112,  113,   60,    2,
   62,   44,    0,   40,   59,   43,    6,   45,   44,   60,
   42,   62,   12,  257,  258,   47,   59,    0,   91,   22,
   40,   59,   38,   59,   44,   91,  142,   46,   59,   46,
   45,   40,   43,   91,   45,   43,   59,   45,   93,   59,
    0,   44,   40,   91,   59,  257,  258,  259,   59,   59,
   43,   59,   45,  258,  256,   55,  258,   40,   41,   42,
   43,   41,   45,    0,   47,   41,   69,  257,  258,   86,
   87,   71,   40,   41,  267,  268,   59,   60,  257,   62,
   40,   41,   45,   43,   41,   45,    0,   41,   91,  267,
  268,  267,  268,  267,  268,   83,   84,   40,   93,   59,
   60,   93,   62,   40,   41,   45,   43,   41,   45,    0,
   59,   93,   93,   41,   93,   59,   93,   59,   41,   41,
   41,   41,   59,   60,   59,   62,   40,   41,   45,   43,
    0,   45,   59,   41,  268,  268,  268,  268,  268,  268,
  268,  268,    0,    0,    0,   59,   60,    0,   62,   40,
   41,    0,  258,    0,   16,   45,   43,   12,   -1,   28,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,    0,
   40,   41,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   45,   -1,   -1,   -1,  257,   -1,   -1,    0,   -1,   59,
   -1,   40,   -1,   -1,  257,  258,  259,   -1,  261,   -1,
  262,  263,  264,  265,   -1,    0,  257,  258,  259,   40,
   59,  262,  263,  264,  265,  276,  277,  278,  274,   -1,
  257,   -1,   -1,   -1,  261,    0,   -1,   40,   59,  266,
   -1,   -1,  269,  270,  271,   -1,  273,  257,  275,   -1,
   -1,  261,  257,  258,  259,   40,  266,    0,  257,  269,
  270,  271,  261,  273,   -1,  275,   -1,  266,   -1,  257,
  269,  270,  271,  261,  273,   40,  275,   -1,  266,   -1,
   -1,  269,  270,  271,  257,  273,   -1,  275,  261,  262,
  263,  264,  265,  266,  267,  268,  269,   40,  271,  272,
  273,  274,   -1,   -1,  257,  258,  259,  257,  261,   -1,
   -1,  261,  262,  263,  264,  265,  266,  267,  268,  269,
   40,  271,  272,  273,  274,   -1,  256,  257,  258,  259,
  257,   40,   -1,   -1,  261,  262,  263,  264,  265,  266,
  267,  268,  269,   -1,  271,  272,  273,  274,   -1,   40,
  257,  258,  259,  257,  261,   -1,   -1,  261,  262,  263,
  264,  265,  266,  267,  268,  269,   40,  271,  272,  273,
  274,   -1,   -1,   -1,   -1,   -1,  257,  257,  258,  259,
  261,   40,   41,   -1,   -1,  266,  267,  268,  269,   -1,
  271,  272,  273,  274,   40,   -1,   40,  257,   -1,   -1,
   -1,  261,  257,  258,  259,  260,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,   40,   -1,   40,  257,   -1,
   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,  267,  268,
  269,   -1,  271,  272,  273,  274,  257,   40,   40,   -1,
  261,   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,
  271,  272,  273,  274,  257,   40,   -1,   -1,  261,   -1,
   -1,   40,   -1,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,  257,   -1,   40,   -1,  261,   -1,   -1,   -1,
   -1,  266,  267,  268,  269,   -1,  271,  272,  273,  274,
   -1,   40,  257,   -1,   -1,   -1,  261,   -1,   -1,   -1,
   -1,  266,  267,  268,  269,   -1,  271,  272,  273,  274,
   -1,   -1,   40,   -1,  257,   -1,   -1,   45,  261,   -1,
   -1,   -1,   -1,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,   60,   -1,   62,   41,   -1,  257,   -1,   45,
   -1,  261,   -1,   -1,   -1,   -1,  266,   -1,  257,  269,
  270,  271,  261,  273,   60,  275,   62,  266,   -1,   41,
  269,   -1,  271,   45,  273,  274,  257,   41,   -1,   -1,
  261,   45,   -1,   -1,   -1,  266,  267,  268,  269,   -1,
  271,   -1,  273,  257,   -1,   -1,   60,  261,   62,   45,
   -1,   -1,  266,  267,  268,  269,   -1,  271,  257,  273,
   -1,   45,  261,   -1,   60,   -1,   62,  266,   -1,   -1,
  269,  257,  271,  257,  273,  261,   60,  261,   62,   -1,
  266,   -1,  266,  269,   -1,  269,  272,  273,  272,  273,
   44,   45,  257,   -1,  257,   -1,  261,   -1,  261,   -1,
   -1,  266,   -1,  266,  269,   59,  269,  272,  273,  272,
  273,   -1,   -1,   -1,  257,  257,   -1,   -1,  261,  261,
   -1,   -1,   -1,  266,  266,   -1,  269,  269,   -1,  272,
  273,  273,  257,   -1,   -1,   -1,  261,   -1,  257,   -1,
   -1,  266,  261,   -1,  269,   -1,  271,  266,  273,   -1,
  269,  257,   -1,  272,  273,  261,   -1,   -1,   -1,   -1,
  266,   -1,   -1,  269,   -1,   -1,  272,  273,  257,   -1,
   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,   -1,   -1,
  269,   -1,  271,   -1,  273,   -1,   -1,   -1,   -1,  257,
  258,  259,   -1,   -1,  262,  263,  264,  265,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   -1,   47,   -1,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,  265,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,   -1,
   -1,   -1,   -1,  257,  258,  259,    0,   -1,  262,  263,
  264,  265,    6,   -1,   -1,   -1,   -1,   -1,   12,   13,
   -1,  257,  258,  259,  260,   -1,  262,  263,  264,  265,
    2,    3,    4,  257,  258,  259,   -1,    9,  262,  263,
  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   22,   -1,   -1,   -1,   -1,   -1,   28,   51,   -1,   -1,
   -1,   55,   56,  257,  258,  259,   38,  261,   -1,   -1,
   -1,   -1,   44,   -1,   46,   -1,   -1,   71,   -1,   -1,
   -1,   88,   89,   90,   -1,   -1,   -1,   -1,   -1,   -1,
   97,   -1,   -1,   -1,   -1,   -1,   -1,   69,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   99,   -1,   -1,   -1,   -1,
   -1,   83,   84,   -1,   86,   87,  110,   -1,   -1,   91,
  127,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  111,
  112,  113,   -1,  150,   -1,  152,   -1,  154,  155,   -1,
  157,   -1,  159,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  142,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  186,
   -1,   -1,  262,  263,  264,  265,
};
}
final static short YYFINAL=10;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE_E","CTE_F","CADENA","ASIGNACION",
"MAYORIGUAL","MENORIGUAL","DESTINTO","IGUAL","IF","ELSE","END_IF","PRINT","INT",
"BEGIN","END","DO","UNTIL","FLOAT","FIRST","LAST","LENGHT",
};
final static String yyrule[] = {
"$accept : programa",
"programa : conjunto_sentencias",
"conjunto_sentencias : sentencias_declarativas",
"conjunto_sentencias : sentencias_declarativas BEGIN sentencias_ejecutables END",
"conjunto_sentencias : BEGIN sentencias_ejecutables END",
"conjunto_sentencias : sentencias_ejecutables END",
"conjunto_sentencias : BEGIN sentencias_ejecutables",
"conjunto_sentencias : sentencias_ejecutables",
"conjunto_sentencias : sentencias_declarativas sentencias_ejecutables END",
"conjunto_sentencias : sentencias_declarativas BEGIN sentencias_ejecutables",
"conjunto_sentencias : sentencias_declarativas sentencias_ejecutables",
"conjunto_sentencias : BEGIN END",
"sentencias_declarativas : sentencias_declarativas sentencia_declarativa",
"sentencias_declarativas : sentencia_declarativa",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : tipo ID '[' CTE_E ']' ';'",
"sentencia_declarativa : ID '[' CTE_E ']' ';'",
"sentencia_declarativa : lista_variables ';'",
"sentencia_declarativa : tipo ID '[' CTE_E ']'",
"sentencia_declarativa : tipo lista_variables",
"sentencia_declarativa : tipo ID '[' CTE_E ';'",
"sentencia_declarativa : tipo ID CTE_E ']' ';'",
"sentencia_declarativa : tipo ';'",
"sentencia_declarativa : tipo '[' CTE_E ']' ';'",
"sentencia_declarativa : tipo ID '[' error ']' ';'",
"tipo : INT",
"tipo : FLOAT",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"sentencia_ejecutable : seleccion_ejecutable ';'",
"sentencia_ejecutable : control_ejecutable ';'",
"sentencia_ejecutable : salida_ejecutable ';'",
"sentencia_ejecutable : asign",
"sentencia_ejecutable : seleccion_ejecutable",
"sentencia_ejecutable : control_ejecutable",
"sentencia_ejecutable : salida_ejecutable",
"condicion_if : condicion",
"$$1 :",
"seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias ELSE $$1 bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias ELSE END_IF",
"seleccion_ejecutable : IF '(' condicion_if ')' ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion_if bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF condicion_if ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : '(' condicion_if ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : '(' condicion_if ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF condicion_if ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion_if bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion_if ')' END_IF",
"condicion : expresion_aritmetica operador expresion_aritmetica",
"condicion : operador expresion_aritmetica",
"condicion : expresion_aritmetica operador error",
"expresion_aritmetica : expresion_aritmetica '+' termino",
"expresion_aritmetica : expresion_aritmetica '-' termino",
"expresion_aritmetica : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : variable",
"factor : CTE_E",
"factor : CTE_F",
"factor : '-' CTE_E",
"factor : '-' CTE_F",
"factor : invocacion_metodo",
"factor : '-' variable",
"factor : '-' invocacion_metodo",
"variable : ID '[' ID ']'",
"variable : ID '[' CTE_E ']'",
"variable : ID",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : DESTINTO",
"operador : IGUAL",
"bloque_sentencias : sentencia_ejecutable",
"bloque_sentencias : BEGIN sentencias_ejecutables END",
"bloque_sentencias : BEGIN END",
"inicio_do : DO",
"control_ejecutable : inicio_do bloque_sentencias UNTIL '(' condicion ')'",
"control_ejecutable : inicio_do UNTIL '(' condicion ')'",
"control_ejecutable : inicio_do bloque_sentencias '(' condicion ')'",
"control_ejecutable : inicio_do bloque_sentencias UNTIL condicion ')'",
"control_ejecutable : inicio_do bloque_sentencias UNTIL '(' ')'",
"control_ejecutable : inicio_do bloque_sentencias UNTIL '(' condicion",
"imprimir : factor",
"imprimir : CADENA",
"salida_ejecutable : PRINT '(' imprimir ')'",
"salida_ejecutable : '(' imprimir ')'",
"salida_ejecutable : PRINT imprimir ')'",
"salida_ejecutable : PRINT '(' ')'",
"salida_ejecutable : PRINT '(' imprimir",
"asign : variable ASIGNACION expresion_aritmetica ';'",
"asign : ASIGNACION expresion_aritmetica ';'",
"asign : variable expresion_aritmetica ';'",
"asign : variable ASIGNACION ';'",
"asign : variable ASIGNACION expresion_aritmetica",
"metodo : FIRST",
"metodo : LAST",
"metodo : LENGHT",
"invocacion_metodo : ID '.' metodo '(' ')'",
"invocacion_metodo : ID '.' metodo ')'",
"invocacion_metodo : ID '.' '(' ')'",
};

//#line 411 "GramaticaCorreciones.y"




private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

private ArrayList<Token> listaVariables = new ArrayList<Token>();

private AnalizadorTercetos analizadorTerceto;


public Parser(AnalizadorLexico lexico, TablaSimbolos tablaSimbolos, AnalizadorTercetos terceto) {
	this.lexico = lexico;
	this.tablaSimbolos = tablaSimbolos;
	this.analizadorTerceto= terceto;
}

public int yylex() {
	int idToken = lexico.yylex();
	yylval = new ParserVal(lexico.getToken());
	return idToken;
	}

public void addError(String descripcion, int nroLinea){
	listaErrores.add(new Error(descripcion,nroLinea,"","Error"));
}

private void yyerror(String string) {
	// TODO Auto-generated method stub
	
}

public int yyparser() {
	return yyparse();
}

public String informacionEstructuras() {
	String informacion="";
	if (!listaCorrectas.isEmpty()){
		informacion = "\nEstructuras Sintacticas: \n";
		for (String info : listaCorrectas) {
			informacion+=info + "\n";
		}
	}
	return informacion;
}

public String informacionError() {
	String informacion = "";
	if (!listaErrores.isEmpty()){
		informacion = "\nErrores Sintacticos: \n";
		for (Error e : listaErrores) {
			informacion += e +"\n";
		}
	}
	return informacion;
}



public void actualizarTablaPositivo(String lexema) {
		if(lexema.equals("32768")){
			if (tablaSimbolos.getClave(lexema).getCantRef()==1)
				tablaSimbolos.eliminarClave(lexema);
			else
				tablaSimbolos.getClave(lexema).decrementarRef();
		
			if(tablaSimbolos.existeClave("32767")) 
				tablaSimbolos.getClave("32767").incrementarRef();
			else{
				tablaSimbolos.agregar("32767", new Token("int",1));
				tablaSimbolos.getClave("32767").setUso("CTE");
			}
		}
		else
			tablaSimbolos.getClave(lexema).setUso("CTE");
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.existeClave(lexema)) {
		if (tablaSimbolos.getClave(lexema).getCantRef()==1)
			tablaSimbolos.eliminarClave(lexema);
		else{
			tablaSimbolos.getClave(lexema).decrementarRef();
			tablaSimbolos.getClave(lexema).setUso("CTE");
		}
	}
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else{
		tablaSimbolos.agregar(lexemaNuevo, new Token("int",1));
		tablaSimbolos.getClave(lexemaNuevo).setUso("CTE");
	}
}


public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if (tablaSimbolos.existeClave(lexema))
		if(tablaSimbolos.getClave(lexema).getCantRef() == 1) 
			tablaSimbolos.eliminarClave(lexema);
		else{
			tablaSimbolos.getClave(lexema).decrementarRef();
			tablaSimbolos.getClave(lexema).setUso("CTF");
		}
			
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else{
		tablaSimbolos.agregar(lexemaNuevo, new Token("float",1));
		tablaSimbolos.getClave(lexemaNuevo).setUso("CTF");
	}

}

public void actualizarTablaVariables(Token tipo,String uso,Token cteE) {
	String lexema = "";
	Token t = null;
	
	while(!listaVariables.isEmpty()) {
		t = listaVariables.get(0);
		listaVariables.remove(0);
		lexema = t.getLexema();
		if(!tablaSimbolos.getClave(lexema).getDeclarada()) {
			tablaSimbolos.getClave(lexema).setTipo(tipo.getLexema());
			tablaSimbolos.getClave(lexema).setDeclarada(true);
			tablaSimbolos.getClave(lexema).setUso(uso);
			if(tablaSimbolos.getClave(lexema).getUso().equals("Nombre de Coleccion")) {
				tablaSimbolos.getClave(lexema).setTamanioColeccion(Integer.parseInt(cteE.getLexema()));
			}
		}
		else
			this.addError("Error en declaracion, variable o coleccion '" + lexema + "' redeclarada",t.getNroLinea());
	}
}

public boolean estaDeclarada(Token t){
	if (tablaSimbolos.getClave(t.getLexema()).getDeclarada())
		return true;
	else
		return false;
}

public boolean esCompatible(Token t1, Token t2){
	if (t1.getTipo().equals(t2.getTipo()))
		return true;
	return false;
}



//#line 729 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 5:
//#line 63 "GramaticaCorreciones.y"
{this.addError("Error, falta BEGIN.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 6:
//#line 64 "GramaticaCorreciones.y"
{this.addError("Error, falta END.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 7:
//#line 65 "GramaticaCorreciones.y"
{this.addError("Error, falta los delimitadores BEGIN y END.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 8:
//#line 66 "GramaticaCorreciones.y"
{this.addError("Error, falta BEGIN.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 9:
//#line 67 "GramaticaCorreciones.y"
{this.addError("Error, falta END.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 10:
//#line 68 "GramaticaCorreciones.y"
{this.addError("Error, falta los delimitadores BEGIN  y END.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 11:
//#line 69 "GramaticaCorreciones.y"
{this.addError("Error, falta sentencias ejecutables.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 16:
//#line 81 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(2).obj).getNroLinea() + ": Sentencia declarativa");
														actualizarTablaVariables(((Token)val_peek(2).obj),"Variable",((Token)val_peek(2).obj));}
break;
case 17:
//#line 83 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia declarativa");
														listaVariables.add(((Token)val_peek(4).obj));
														actualizarTablaVariables(((Token)val_peek(5).obj),"Nombre de Coleccion",((Token)val_peek(2).obj));}
break;
case 18:
//#line 86 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 19:
//#line 87 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 20:
//#line 88 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 21:
//#line 89 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 22:
//#line 90 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta ']'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 23:
//#line 91 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta '['.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 24:
//#line 92 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta variables.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 25:
//#line 93 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta identIFicador.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 26:
//#line 94 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, la coleccion solo permite constantes enteras.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 29:
//#line 101 "GramaticaCorreciones.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 30:
//#line 102 "GramaticaCorreciones.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 35:
//#line 113 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 36:
//#line 114 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 37:
//#line 115 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 38:
//#line 119 "GramaticaCorreciones.y"
{ 
								TercetoIF tercetoIF = new TercetoIF ( new Token( "BF"), new Token("@"+analizadorTerceto.getNumeroTerceto()), null, analizadorTerceto.getProximoTerceto());
								tercetoIF.setTipoSalto(((Token)val_peek(0).obj).getOperador());
								tercetoIF.setNombre("IF");
								analizadorTerceto.addTerceto(tercetoIF);
								analizadorTerceto.apilar();
								
											}
break;
case 39:
//#line 129 "GramaticaCorreciones.y"
{TercetoAbstracto tercetoIF = new TercetoIF (new Token("BI"), null, null, analizadorTerceto.getProximoTerceto() );
	tercetoIF.setNombre("IF");
                                                             	analizadorTerceto.addTerceto (tercetoIF);
                                                             	analizadorTerceto.desapilar();
                                                             	analizadorTerceto.apilar();}
break;
case 40:
//#line 135 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(8).obj).getNroLinea() + ": Sentencia IF-ELSE");
												analizadorTerceto.desapilar();}
break;
case 41:
//#line 138 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia IF");
																			analizadorTerceto.desapilar();}
break;
case 42:
//#line 140 "GramaticaCorreciones.y"
{this.addError("Falta 'ELSE'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 43:
//#line 141 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 44:
//#line 142 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 45:
//#line 143 "GramaticaCorreciones.y"
{this.addError( "Falta ')'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 46:
//#line 144 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 47:
//#line 145 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 48:
//#line 146 "GramaticaCorreciones.y"
{this.addError("Falta 'IF'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 49:
//#line 147 "GramaticaCorreciones.y"
{this.addError("Falta 'IF'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 50:
//#line 148 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 51:
//#line 149 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 52:
//#line 150 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 53:
//#line 151 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 54:
//#line 156 "GramaticaCorreciones.y"
{
															if( esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj))){
																TercetoAbstracto tercetoComparacion = new TercetoComparacion(((Token)val_peek(1).obj), ((Token)val_peek(2).obj), ((Token)val_peek(0).obj),analizadorTerceto.getProximoTerceto());
																tercetoComparacion.setNombre("Comparacion");
																analizadorTerceto.addTerceto(tercetoComparacion);
																
																Token nuevo = new Token ( "@" + analizadorTerceto.getNumeroTerceto());
																nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
																nuevo.setOperador(((Token)val_peek(1).obj).getLexema());
																yyval = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);									
															}
break;
case 55:
//#line 170 "GramaticaCorreciones.y"
{this.addError("Falta expresion del lado izquierdo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 56:
//#line 171 "GramaticaCorreciones.y"
{this.addError("Falta expresion del lado derecho.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 57:
//#line 174 "GramaticaCorreciones.y"
{ if( esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj))){
																TercetoAbstracto tercetoExpresion = new TercetoExpresion(((Token)val_peek(1).obj), ((Token)val_peek(2).obj),((Token)val_peek(0).obj),analizadorTerceto.getProximoTerceto());
																tercetoExpresion.setNombre("Expresion");
																analizadorTerceto.addTerceto(tercetoExpresion);
																Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
																nuevo.setUso("Variable Auxiliar");
																tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
																yyval = new ParserVal(nuevo);
																}
																else
																	analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															}
break;
case 58:
//#line 188 "GramaticaCorreciones.y"
{
															if( esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj))){
																TercetoAbstracto tercetoExpresion = new TercetoExpresion(((Token)val_peek(1).obj), ((Token)val_peek(2).obj), ((Token)val_peek(0).obj),analizadorTerceto.getProximoTerceto());
																tercetoExpresion.setNombre("Expresion");
																analizadorTerceto.addTerceto(tercetoExpresion);
					
																Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
																nuevo.setUso("Variable Auxiliar");
																tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
																yyval = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															}
break;
case 60:
//#line 207 "GramaticaCorreciones.y"
{	if( esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj))){
															TercetoAbstracto tercetoExpresion = new TercetoExpresionMult(((Token)val_peek(1).obj),((Token)val_peek(2).obj), ((Token)val_peek(0).obj),analizadorTerceto.getProximoTerceto());
															tercetoExpresion.setNombre("Expresion");
															analizadorTerceto.addTerceto(tercetoExpresion);
															Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
															nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
															nuevo.setUso("Variable Auxiliar");
															tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
															yyval = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															
															}
break;
case 61:
//#line 222 "GramaticaCorreciones.y"
{ if( esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj))){
															TercetoAbstracto tercetoExpresion = new TercetoExpresionDivision(((Token)val_peek(1).obj), ((Token)val_peek(2).obj), ((Token)val_peek(0).obj),analizadorTerceto.getProximoTerceto());
															tercetoExpresion.setNombre("Expresion");
															analizadorTerceto.addTerceto(tercetoExpresion);
															Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
															nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
															nuevo.setUso("Variable Auxiliar");
															tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
															yyval = new ParserVal(nuevo);
															}
															else
																analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															
															}
break;
case 62:
//#line 238 "GramaticaCorreciones.y"
{}
break;
case 63:
//#line 242 "GramaticaCorreciones.y"
{}
break;
case 64:
//#line 243 "GramaticaCorreciones.y"
{actualizarTablaPositivo(((Token)val_peek(0).obj).getLexema());
					((Token)val_peek(0).obj).setTipo("int");
	
					yyval = val_peek(0);
					}
break;
case 65:
//#line 248 "GramaticaCorreciones.y"
{((Token)val_peek(0).obj).setTipo("float");
					tablaSimbolos.getClave(((Token)val_peek(0).obj).getLexema()).setUso("CTF");
					yyval = val_peek(0);
					}
break;
case 66:
//#line 252 "GramaticaCorreciones.y"
{actualizarTablaNegativo(((Token)val_peek(0).obj).getLexema());
						Token tNegativo = new Token("-"+((Token)val_peek(0).obj).getLexema());
						tNegativo.setTipo("int");
						yyval = new ParserVal(tNegativo);}
break;
case 67:
//#line 256 "GramaticaCorreciones.y"
{actualizarTablaNegativoFloat(((Token)val_peek(0).obj).getLexema());
						Token tNegativo = new Token("-"+((Token)val_peek(0).obj).getLexema());
						tNegativo.setTipo("float");
						yyval = new ParserVal(tNegativo);}
break;
case 69:
//#line 261 "GramaticaCorreciones.y"
{Token tNegativo = new Token("-"+((Token)val_peek(0).obj).getLexema());
							tNegativo.setTipo(((Token)val_peek(0).obj).getTipo());
							yyval = new ParserVal(tNegativo);}
break;
case 71:
//#line 268 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj))){
									analizadorTerceto.agregarError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",lexico.nroLinea);
									tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
								}
								else
									if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
										analizadorTerceto.agregarError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",lexico.nroLinea);
									
								if (!estaDeclarada(((Token)val_peek(1).obj))){
									analizadorTerceto.agregarError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",lexico.nroLinea);
									this.addError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",((Token)val_peek(1).obj).getNroLinea());
									tablaSimbolos.eliminarClave(((Token)val_peek(1).obj).getLexema());
								}
								else
									if (!tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getTipo().equals("int"))
										analizadorTerceto.agregarError("El tipo del subindice no es entero",lexico.nroLinea);
								else
									if (tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getUso().equals("Nombre de Coleccion"))
										
										analizadorTerceto.agregarError("Error '"+((Token)val_peek(1).obj).getLexema() + "' es una coleccion.",lexico.nroLinea);
										
									else{
										TercetoAbstracto terceto = new TercetoColeccion((new Token("OFFSET")), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
										analizadorTerceto.addTerceto(terceto);
										Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
										nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
										yyval = new ParserVal(nuevo);
									}
								}
break;
case 72:
//#line 298 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj))){
										analizadorTerceto.agregarError("Coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",lexico.nroLinea);
										tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
									}
									else{
										if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
											analizadorTerceto.agregarError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",lexico.nroLinea);
										else{
											TercetoAbstracto terceto = new TercetoIF(new Token("OFFSET"), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
											analizadorTerceto.addTerceto(terceto);
											Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
											nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
											yyval = new ParserVal(nuevo);
										}
									}
								}
break;
case 73:
//#line 314 "GramaticaCorreciones.y"
{	
					Token t = ((Token)val_peek(0).obj);
					if (!estaDeclarada(t)){
						analizadorTerceto.agregarError("Error variable '"+t.getLexema() + "' no declarada.",lexico.nroLinea);
						tablaSimbolos.eliminarClave(t.getLexema());}
					else{
						t.setTipo(tablaSimbolos.getClave(t.getLexema()).getTipo());
						yyval = new ParserVal(t);
					}
				}
break;
case 83:
//#line 344 "GramaticaCorreciones.y"
{ 
					analizadorTerceto.apilar();
					TercetoAbstracto tercetoEtiqueta = new TercetoEtiqueta(new Token("Label" + analizadorTerceto.getProximoTerceto()),null,null,analizadorTerceto.getProximoTerceto());
					tercetoEtiqueta.setNombre("Etiqueta");
					analizadorTerceto.addTerceto(tercetoEtiqueta);}
break;
case 84:
//#line 350 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia until");
	TercetoDO tercetoDO = new TercetoDO(new Token("BF"), null,null,analizadorTerceto.getProximoTerceto());
	tercetoDO.setTipoSalto(((Token)val_peek(1).obj).getOperador());
	tercetoDO.setNombre("DO");
															analizadorTerceto.addTerceto(tercetoDO);
															analizadorTerceto.desapilarControl();
	}
break;
case 85:
//#line 357 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 86:
//#line 358 "GramaticaCorreciones.y"
{this.addError("Falta 'until'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 87:
//#line 359 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 88:
//#line 360 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 89:
//#line 361 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 91:
//#line 367 "GramaticaCorreciones.y"
{yyval = val_peek(0);}
break;
case 92:
//#line 369 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Sentencia PRINT");
												TercetoPrint tercetoPrint = new TercetoPrint ( ((Token)val_peek(3).obj), ((Token)val_peek(1).obj ), null, analizadorTerceto.getProximoTerceto() );
												tercetoPrint.setNombre("Print");
												analizadorTerceto.addTerceto(tercetoPrint);

	
	}
break;
case 93:
//#line 376 "GramaticaCorreciones.y"
{this.addError("Falta 'PRINT'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 94:
//#line 377 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 95:
//#line 378 "GramaticaCorreciones.y"
{this.addError("Falta cadena .",((Token)val_peek(1).obj).getNroLinea());}
break;
case 96:
//#line 379 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 97:
//#line 383 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Asignacion");
														
														if( esCompatible(((Token)val_peek(3).obj),((Token)val_peek(1).obj))){
															TercetoAbstracto tercetoAsignacion = new TercetoAsignacion(((Token)val_peek(2).obj), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
															tercetoAsignacion.setNombre("Asignacion");
															analizadorTerceto.addTerceto(tercetoAsignacion);
														}
														else
															analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
														}
break;
case 98:
//#line 393 "GramaticaCorreciones.y"
{this.addError("Falta variable.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 99:
//#line 394 "GramaticaCorreciones.y"
{this.addError("Falta ':='.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 100:
//#line 395 "GramaticaCorreciones.y"
{this.addError("Falta expresion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 101:
//#line 396 "GramaticaCorreciones.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 106:
//#line 405 "GramaticaCorreciones.y"
{this.addError("Falta '(.'",((Token)val_peek(1).obj).getNroLinea());}
break;
case 107:
//#line 406 "GramaticaCorreciones.y"
{this.addError("Falta metodo.",((Token)val_peek(2).obj).getNroLinea());}
break;
//#line 1358 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
