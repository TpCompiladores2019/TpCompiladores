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






//#line 2 "GramaticaFinal.y"
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
	import CodigoIntermedio.AnalizadorTercetos;
	import CodigoIntermedio.TercetoAbstracto;
	import CodigoIntermedio.TercetoAsignacion;
	import CodigoIntermedio.TercetoComparacion;
	import CodigoIntermedio.TercetoDO;
	import CodigoIntermedio.TercetoExpresionMult;
	import CodigoIntermedio.TercetoIF;
	import CodigoIntermedio.TercetoPrint;
	import CodigoIntermedio.TercetoExpresionDivision;
	import CodigoIntermedio.TercetoExpresion;
	import CodigoIntermedio.TercetoEtiqueta;
	import CodigoIntermedio.TercetoAsignacionRowing;
	import CodigoIntermedio.TercetoMetodos;
	import CodigoIntermedio.TercetoColeccionDer;
	import CodigoIntermedio.TercetoColeccionIzq;
//#line 40 "Parser.java"




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
public final static short LENGTH=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    3,    3,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    6,    6,    7,    7,
    5,    5,    5,    5,    5,    5,    5,   12,   15,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,   13,   13,   13,   16,   16,   16,
   18,   18,   18,   19,   19,   19,   19,   19,   19,   19,
   19,   20,   20,   20,   17,   17,   17,   17,   17,   17,
   14,   14,   14,   22,    9,    9,    9,    9,    9,    9,
   23,   23,   10,   10,   10,   10,   10,   24,   24,   24,
   11,   11,   11,   11,   11,   25,   25,   25,   21,   21,
   21,   21,
};
final static short yylen[] = {                            2,
    1,    1,    4,    3,    2,    2,    1,    3,    3,    2,
    2,    2,    1,    2,    1,    3,    6,    5,    2,    5,
    2,    5,    5,    2,    5,    6,    1,    1,    3,    1,
    2,    2,    2,    1,    1,    1,    1,    1,    0,    9,
    6,    7,    7,    7,    7,    7,    7,    7,    5,    5,
    5,    5,    5,    6,    3,    2,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    2,    2,    1,    2,
    2,    4,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    2,    1,    6,    5,    5,    5,    5,    5,
    1,    1,    4,    3,    3,    3,    3,    4,    4,    1,
    4,    3,    3,    3,    3,    1,    1,    1,    5,    4,
    4,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   27,    0,   84,   28,    0,    0,
    1,    0,    0,   13,   15,    0,    0,    0,    0,    0,
   34,    0,    0,    0,    0,   65,   66,    0,    0,    0,
   63,   64,   69,   78,   76,   79,   80,    0,   75,   77,
    0,   38,    0,    0,   92,    0,   91,    0,    0,   11,
    0,    0,    0,    0,    0,    0,   12,    5,   14,    0,
   24,    0,    0,   19,    0,   31,   32,   33,    0,    0,
   81,    0,    0,    0,    0,    0,    0,    0,   67,   68,
   70,   71,    0,    0,  102,    0,    0,    0,    0,    0,
    0,    0,   96,    0,   95,    0,    4,    0,   94,    0,
    8,    0,    0,    0,   16,   29,   83,    0,    0,    0,
    0,  104,    0,  103,   98,    0,    0,    0,    0,  106,
  107,  108,    0,    0,    0,    0,   61,   62,    0,    0,
    0,    0,   57,    0,   93,    0,    0,    3,    0,    0,
    0,    0,   82,    0,    0,    0,    0,  101,   18,   72,
   73,    0,  111,    0,  110,    0,   51,    0,   53,    0,
    0,   52,    0,   50,   99,    0,   49,   23,    0,   22,
    0,   25,   86,   89,    0,   88,   87,  112,  109,    0,
    0,   54,    0,   41,    0,    0,    0,    0,   26,   17,
   85,   46,   44,   43,    0,   42,   45,   47,   48,    0,
   40,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   14,   71,   16,   17,   18,   19,   20,
   21,   41,   42,   72,  195,   43,   44,   30,   31,   32,
   33,   22,   48,   23,  124,
};
final static short yysindex[] = {                       304,
  -82,  -12,  -34,   -8,    0,  397,    0,    0,  538,    0,
    0,  319,  416,    0,    0,  -56,  -25,  -43,  -20,  -11,
    0,  330,  157, -178,  -41,    0,    0, -190,  -21,  -32,
    0,    0,    0,    0,    0,    0,    0,  522,    0,    0,
   17,    0,   56,  -12,    0,    6,    0,   36,  -66,    0,
  418,   40,    0,   46,  -13,  437,    0,    0,    0,  -89,
    0, -205,  -23,    0, -163,    0,    0,    0,  439,   60,
    0,  -40,  141,   -3,   11,   15, -165,  -36,    0,    0,
    0,    0,  -12,  -12,    0,  -12,  -12,  457,  391,  457,
  376,   19,    0,   69,    0, -160,    0,  457,    0,  463,
    0,   20, -180,   22,    0,    0,    0,  476,  580,  504,
  580,    0,   16,    0,    0,   58,   26,   34,   91,    0,
    0,    0,   92,   81,  -32,  -32,    0,    0, -144,  370,
 -131, -109,    0,   19,    0,   48, -107,    0,   80,   50,
  -39,   87,    0,  113,  552,  123,  124,    0,    0,    0,
    0,  125,    0,  126,    0,  457,    0,  457,    0,  349,
  457,    0,  457,    0,    0,  457,    0,    0,  109,    0,
  111,    0,    0,    0,  132,    0,    0,    0,    0,  -90,
  -86,    0,  -85,    0,  -84,  -83,  -81,  -79,    0,    0,
    0,    0,    0,    0,  457,    0,    0,    0,    0,  -78,
    0,
};
final static short yyrindex[] = {                         0,
  136,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  188,  191,    0,    0,    0,    0,  219,  239,  261,
    0,    0,    0,    0,   43,    0,    0,    0,    0,   66,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  279,    0,
  192,    0,  566,    0,    0,  193,    0,    0,    0,    1,
    0,    0,   12,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  135,    0,  177,    0,    0,    0,    0,    0,  199,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  286,    0,    0,  517,    0,    0,    0,    0,
    0,    0,    0,    0,   89,  112,    0,    0,    0,    0,
    0,    0,    0,  156,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   23,    0,    0,    0,  198,    0,    0,    0,    0,    0,
    0,    0,  486,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    2,  194,  806,    0,  187,    0,    0,    0,
    0,    8,  -80,  -16,    0,   47,  161,   79,    9,  179,
  180,    0,   -2,    0,    0,
};
final static int YYTABLESIZE=914;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        111,
   30,  103,   61,  123,   78,   38,   54,   51,   24,   86,
   28,   21,   47,   56,   87,   66,   52,   53,   65,  170,
   65,   83,   20,   84,   96,   39,    9,   40,  144,  146,
  147,   46,   28,   64,   62,  105,   28,   85,   67,   83,
   30,   84,   74,   94,   30,   89,   93,   68,   29,   77,
   28,   21,  104,  171,   47,  114,  100,   90,   83,   30,
   84,   83,   20,   84,  175,   60,   25,   79,   80,   74,
  108,  129,  131,  132,  148,  140,   95,  141,   75,   76,
   98,  137,   74,   74,   74,   74,   99,   74,   58,   74,
   92,  117,  118,  106,  127,  128,   75,  136,   83,  109,
   84,   74,   74,  115,   74,   60,   60,  116,   60,  135,
   60,   59,  139,  160,  142,   39,  149,   40,  150,  113,
  154,  155,  156,  157,   60,   60,  151,   60,   58,   58,
  152,   58,  153,   58,   56,  161,  162,  134,  168,  180,
  165,  181,  169,  185,  186,  172,  187,   58,   58,  188,
   58,   59,   59,  173,   59,   55,   59,  163,  164,  166,
  167,  125,  126,  176,  177,  178,  179,  189,  102,  190,
   59,   59,  191,   59,   56,   56,   97,  192,  200,   30,
  100,  193,  194,  196,  197,   28,  198,    2,  199,  201,
    7,    6,   10,   56,   30,   55,   55,   90,    9,  112,
   60,   28,   63,   91,    0,   57,   81,   82,    0,    0,
    0,    0,    0,    0,   55,    0,   97,    0,   35,    0,
  119,    0,   25,   26,   27,    0,    0,   34,   35,   36,
   37,    0,    0,  110,    0,   97,    0,   90,   36,  120,
  121,  122,    0,   49,   25,   26,   27,    2,   25,   26,
   27,   45,    3,    0,    0,    4,   90,   30,   35,    7,
   37,   30,   25,   26,   27,   45,   30,    0,   21,   30,
   30,   30,   21,   30,    0,   30,    0,   21,   36,   20,
   21,   21,   21,   20,   21,  105,   21,    0,   20,    0,
    0,   20,   20,   20,    0,   20,    0,   20,   74,   74,
   37,    0,    0,   74,   74,   74,   74,   74,   74,   74,
   74,   74,    0,   74,   74,   74,   74,   34,   35,   36,
   37,   60,   60,  100,    0,  105,   60,   60,   60,   60,
   60,   60,   60,   60,   60,    0,   60,   60,   60,   60,
    0,    0,    0,    9,   58,   58,    0,    0,    0,   58,
   58,   58,   58,   58,   58,   58,   58,   58,    9,   58,
   58,   58,   58,    0,    0,    0,    0,   59,   59,    9,
    0,    0,   59,   59,   59,   59,   59,   59,   59,   59,
   59,    0,   59,   59,   59,   59,    0,    0,    9,    0,
   56,   56,  100,  100,  100,   56,  100,   25,   26,   27,
   56,   56,   56,   56,    0,   56,   56,   56,   56,    9,
    0,   55,   55,   25,   26,   27,   55,   73,    0,    0,
   28,   55,   55,   55,   55,    0,   55,   55,   55,   55,
    9,  130,   97,   97,    0,    0,    9,   97,    0,    0,
    0,    0,   97,   97,   97,   97,    0,   97,   97,   97,
   97,    0,    0,   90,   90,    9,    0,    9,   90,    0,
    0,    0,    0,   90,   90,   90,   90,    0,   90,   90,
   90,   90,    0,    0,   35,   35,    9,    0,    9,   35,
    0,    0,    0,    0,   35,   35,   35,   35,    0,   35,
   35,   35,   35,    0,   36,   36,    9,    0,    0,   36,
    0,    0,    9,    0,   36,   36,   36,   36,    0,   36,
   36,   36,   36,    0,    0,    9,   37,   37,    0,    0,
    0,   37,    0,    0,    0,   39,   37,   37,   37,   37,
    0,   37,   37,   37,   37,  100,  100,  100,    0,  100,
    0,  105,  105,  145,    0,    0,  105,    0,   28,    0,
    0,  105,  105,  105,  105,    0,  105,  105,  105,  105,
    1,   99,   88,   39,    2,   40,   28,    0,    0,    3,
    0,    0,    4,    5,    6,    1,    7,    0,    8,    2,
    0,   39,   28,   40,    3,    0,   49,    4,    5,   55,
    2,    7,  174,    8,    0,    3,   28,   39,    4,   40,
   69,    0,    7,   70,  182,   49,   91,   63,   63,    2,
   63,   39,   63,   40,    3,  183,  184,    4,    0,   69,
    0,    7,    0,    0,   28,   63,   49,   63,    0,    0,
    2,  133,   25,   26,   27,    3,  158,  159,    4,   39,
   69,   40,    7,    0,    0,    0,    0,   49,    0,    0,
    0,    2,    0,   49,    0,    0,    3,    2,    0,    4,
    0,   69,    3,    7,    0,    4,    0,    0,   50,    7,
    0,    0,   49,    0,   49,    0,    2,    0,    2,    0,
    0,    3,    0,    3,    4,    0,    4,   58,    7,   97,
    7,    0,    0,   49,    0,   49,    0,    2,    0,    2,
    0,    0,    3,    0,    3,    4,    0,    4,  101,    7,
  107,    7,    0,   49,    0,    0,    0,    2,    0,   49,
    0,    0,    3,    2,    0,    4,    0,   69,    3,    7,
    0,    4,   49,    0,  138,    7,    2,    0,    0,    0,
    0,    3,   39,    0,    4,    0,   39,  143,    7,    0,
    0,   39,    0,    0,   39,    0,   39,    0,   39,    0,
   25,   26,   27,    0,    0,   34,   35,   36,   37,    0,
    0,    0,    0,   99,   99,   99,    0,   99,   25,   26,
   27,    0,    0,   34,   35,   36,   37,    0,    0,    0,
    0,    0,    0,    0,   25,   26,   27,   45,    0,   34,
   35,   36,   37,    0,    0,   15,    0,    0,   25,   26,
   27,   15,    0,   34,   35,   36,   37,   15,   59,    0,
    0,    0,    0,    0,    0,    0,    0,   63,   63,   63,
   63,    0,    0,    0,    0,    0,   25,   26,   27,    0,
    0,   34,   35,   36,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   59,    0,    0,    0,
   15,   59,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   15,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
    0,    0,    0,   59,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   91,   59,   40,   46,   40,    9,    6,   91,   42,
   45,    0,    4,   12,   47,   59,    9,    9,   44,   59,
   44,   43,    0,   45,   91,   60,   40,   62,  109,  110,
  111,   40,   45,   59,   91,   59,   45,   59,   59,   43,
   40,   45,    0,   46,   44,   38,   41,   59,    2,   91,
   45,   40,  258,   93,   46,   59,   55,   41,   43,   59,
   45,   43,   40,   45,  145,    0,  257,  258,  259,   23,
   69,   88,   89,   90,   59,  256,   41,  258,  257,  258,
   41,   98,   40,   41,   42,   43,   41,   45,    0,   47,
   44,  257,  258,  257,   86,   87,  257,  258,   43,   40,
   45,   59,   60,   93,   62,   40,   41,   93,   43,   41,
   45,    0,   93,  130,   93,   60,   59,   62,   93,   73,
   40,   41,  267,  268,   59,   60,   93,   62,   40,   41,
   40,   43,   41,   45,    0,  267,  268,   91,   59,  156,
   93,  158,   93,  160,  161,   59,  163,   59,   60,  166,
   62,   40,   41,   41,   43,    0,   45,  267,  268,  267,
  268,   83,   84,   41,   41,   41,   41,   59,  258,   59,
   59,   60,   41,   62,   40,   41,    0,  268,  195,   44,
   45,  268,  268,  268,  268,   45,  268,    0,  268,  268,
    0,    0,    0,   59,   59,   40,   41,    0,    0,   59,
  257,   45,   16,   43,   -1,   12,   28,   28,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   40,   -1,    0,   -1,
  257,   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,
  265,   -1,   -1,  274,   -1,   59,   -1,   40,    0,  276,
  277,  278,   -1,  257,  257,  258,  259,  261,  257,  258,
  259,  260,  266,   -1,   -1,  269,   59,  257,   40,  273,
    0,  261,  257,  258,  259,  260,  266,   -1,  257,  269,
  270,  271,  261,  273,   -1,  275,   -1,  266,   40,  257,
  269,  270,  271,  261,  273,    0,  275,   -1,  266,   -1,
   -1,  269,  270,  271,   -1,  273,   -1,  275,  256,  257,
   40,   -1,   -1,  261,  262,  263,  264,  265,  266,  267,
  268,  269,   -1,  271,  272,  273,  274,  262,  263,  264,
  265,  256,  257,   45,   -1,   40,  261,  262,  263,  264,
  265,  266,  267,  268,  269,   -1,  271,  272,  273,  274,
   -1,   -1,   -1,   40,  256,  257,   -1,   -1,   -1,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   40,  271,
  272,  273,  274,   -1,   -1,   -1,   -1,  256,  257,   40,
   -1,   -1,  261,  262,  263,  264,  265,  266,  267,  268,
  269,   -1,  271,  272,  273,  274,   -1,   -1,   40,   -1,
  256,  257,  257,  258,  259,  261,  261,  257,  258,  259,
  266,  267,  268,  269,   -1,  271,  272,  273,  274,   40,
   -1,  256,  257,  257,  258,  259,  261,  261,   -1,   -1,
   45,  266,  267,  268,  269,   -1,  271,  272,  273,  274,
   40,   41,  256,  257,   -1,   -1,   40,  261,   -1,   -1,
   -1,   -1,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,   -1,   -1,  256,  257,   40,   -1,   40,  261,   -1,
   -1,   -1,   -1,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,   -1,   -1,  256,  257,   40,   -1,   40,  261,
   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,  256,  257,   40,   -1,   -1,  261,
   -1,   -1,   40,   -1,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,   -1,   40,  256,  257,   -1,   -1,
   -1,  261,   -1,   -1,   -1,   40,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,  257,  258,  259,   -1,  261,
   -1,  256,  257,   40,   -1,   -1,  261,   -1,   45,   -1,
   -1,  266,  267,  268,  269,   -1,  271,  272,  273,  274,
  257,   45,   41,   60,  261,   62,   45,   -1,   -1,  266,
   -1,   -1,  269,  270,  271,  257,  273,   -1,  275,  261,
   -1,   60,   45,   62,  266,   -1,  257,  269,  270,  271,
  261,  273,   41,  275,   -1,  266,   45,   60,  269,   62,
  271,   -1,  273,  274,  256,  257,   41,   42,   43,  261,
   45,   60,   47,   62,  266,  267,  268,  269,   -1,  271,
   -1,  273,   -1,   -1,   45,   60,  257,   62,   -1,   -1,
  261,  256,  257,  258,  259,  266,  267,  268,  269,   60,
  271,   62,  273,   -1,   -1,   -1,   -1,  257,   -1,   -1,
   -1,  261,   -1,  257,   -1,   -1,  266,  261,   -1,  269,
   -1,  271,  266,  273,   -1,  269,   -1,   -1,  272,  273,
   -1,   -1,  257,   -1,  257,   -1,  261,   -1,  261,   -1,
   -1,  266,   -1,  266,  269,   -1,  269,  272,  273,  272,
  273,   -1,   -1,  257,   -1,  257,   -1,  261,   -1,  261,
   -1,   -1,  266,   -1,  266,  269,   -1,  269,  272,  273,
  272,  273,   -1,  257,   -1,   -1,   -1,  261,   -1,  257,
   -1,   -1,  266,  261,   -1,  269,   -1,  271,  266,  273,
   -1,  269,  257,   -1,  272,  273,  261,   -1,   -1,   -1,
   -1,  266,  257,   -1,  269,   -1,  261,  272,  273,   -1,
   -1,  266,   -1,   -1,  269,   -1,  271,   -1,  273,   -1,
  257,  258,  259,   -1,   -1,  262,  263,  264,  265,   -1,
   -1,   -1,   -1,  257,  258,  259,   -1,  261,  257,  258,
  259,   -1,   -1,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,   -1,  262,
  263,  264,  265,   -1,   -1,    0,   -1,   -1,  257,  258,
  259,    6,   -1,  262,  263,  264,  265,   12,   13,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,
   -1,  262,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   51,   -1,   -1,   -1,
   55,   56,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   69,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  100,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  108,
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
"BEGIN","END","DO","UNTIL","FLOAT","FIRST","LAST","LENGTH",
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
"seleccion_ejecutable : IF '(' condicion_if ')' bloque_sentencias error",
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
"asignacion_izq : ID '[' ID ']'",
"asignacion_izq : ID '[' CTE_E ']'",
"asignacion_izq : ID",
"asign : asignacion_izq ASIGNACION expresion_aritmetica ';'",
"asign : ASIGNACION expresion_aritmetica ';'",
"asign : asignacion_izq expresion_aritmetica ';'",
"asign : asignacion_izq ASIGNACION ';'",
"asign : asignacion_izq ASIGNACION expresion_aritmetica",
"metodo : FIRST",
"metodo : LAST",
"metodo : LENGTH",
"invocacion_metodo : ID '.' metodo '(' ')'",
"invocacion_metodo : ID '.' metodo ')'",
"invocacion_metodo : ID '.' '(' ')'",
"invocacion_metodo : ID '.' ID '(' ')'",
};

//#line 677 "GramaticaFinal.y"




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
	if (t.getLexema().contains("@")) // es una variable aux. No puede escribir un id comun con @
		return true;
	Token token = tablaSimbolos.getClave(t.getLexema());
	if (token==null) {
		return false;
	}
	return (!token.getTipo().isEmpty());
}

public boolean esCompatible(Token t1, Token t2){
	return (t1.getTipo().equals(t2.getTipo()));
}

public boolean esColeccion(String lexema){
	Token t = tablaSimbolos.getClave(lexema);
	if (t!=null)
		if(t.getUso().equals("Nombre de Coleccion"))
			return true;
	return false;
}

public boolean estaVacia() {
	return listaErrores.isEmpty();

}

public boolean noHayErrores(){
	return (listaErrores.isEmpty() && analizadorTerceto.estaVacia() && lexico.estaVacia() );
}

//#line 747 "Parser.java"
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
//#line 67 "GramaticaFinal.y"
{this.addError("Error, falta BEGIN.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 6:
//#line 68 "GramaticaFinal.y"
{this.addError("Error, falta END.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 7:
//#line 69 "GramaticaFinal.y"
{this.addError("Error, falta los delimitadores BEGIN y END.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 8:
//#line 70 "GramaticaFinal.y"
{this.addError("Error, falta BEGIN.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 9:
//#line 71 "GramaticaFinal.y"
{this.addError("Error, falta END.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 10:
//#line 72 "GramaticaFinal.y"
{this.addError("Error, falta los delimitadores BEGIN  y END.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 11:
//#line 73 "GramaticaFinal.y"
{this.addError("Error, falta sentencias ejecutables.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 16:
//#line 85 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(2).obj).getNroLinea() + ": Sentencia declarativa");
														actualizarTablaVariables(((Token)val_peek(2).obj),"Variable",((Token)val_peek(2).obj));}
break;
case 17:
//#line 87 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia declarativa");
														listaVariables.add(((Token)val_peek(4).obj));
														actualizarTablaVariables(((Token)val_peek(5).obj),"Nombre de Coleccion",((Token)val_peek(2).obj));}
break;
case 18:
//#line 90 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 19:
//#line 91 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 20:
//#line 92 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 21:
//#line 93 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 22:
//#line 94 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta ']'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 23:
//#line 95 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta '['.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 24:
//#line 96 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta variables.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 25:
//#line 97 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta identIFicador.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 26:
//#line 98 "GramaticaFinal.y"
{this.addError("Error en declaracion, la coleccion solo permite constantes enteras.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 29:
//#line 105 "GramaticaFinal.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 30:
//#line 106 "GramaticaFinal.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 35:
//#line 113 "GramaticaFinal.y"
{this.addError("Error, falta ';'",lexico.getNroLinea());}
break;
case 36:
//#line 114 "GramaticaFinal.y"
{this.addError("Error, falta ';'",lexico.getNroLinea());}
break;
case 37:
//#line 115 "GramaticaFinal.y"
{this.addError("Error, falta ';'",lexico.getNroLinea());}
break;
case 38:
//#line 119 "GramaticaFinal.y"
{ if (noHayErrores()){
								if (((Token)val_peek(0).obj).getOperador()!=null){
								TercetoIF tercetoIF = new TercetoIF ( new Token( "BF"), new Token("@"+analizadorTerceto.getNumeroTerceto()), null, analizadorTerceto.getProximoTerceto());
								tercetoIF.setTipoSalto(((Token)val_peek(0).obj).getOperador(),((Token)val_peek(0).obj).getTipo());
								analizadorTerceto.addTerceto(tercetoIF);
								analizadorTerceto.apilar();
								}
											}
											}
break;
case 39:
//#line 132 "GramaticaFinal.y"
{
																if (noHayErrores()){
																TercetoAbstracto tercetoIF = new TercetoIF (new Token("BI"), null, null, analizadorTerceto.getProximoTerceto() );
                                                             	analizadorTerceto.addTerceto (tercetoIF);
                                                             	analizadorTerceto.desapilar();
                                                             	analizadorTerceto.apilar();}}
break;
case 40:
//#line 139 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(8).obj).getNroLinea() + ": Sentencia IF-ELSE");
												
												if (noHayErrores()){
												analizadorTerceto.desapilar();}}
break;
case 41:
//#line 145 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia IF");
																		if (noHayErrores()){
																			analizadorTerceto.desapilar();}}
break;
case 42:
//#line 149 "GramaticaFinal.y"
{this.addError("Falta 'ELSE'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 43:
//#line 150 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 44:
//#line 151 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 45:
//#line 152 "GramaticaFinal.y"
{this.addError( "Falta ')'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 46:
//#line 153 "GramaticaFinal.y"
{this.addError("Falta condicion.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 47:
//#line 154 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 48:
//#line 155 "GramaticaFinal.y"
{this.addError("Falta 'IF'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 49:
//#line 156 "GramaticaFinal.y"
{this.addError("Falta 'IF'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 50:
//#line 157 "GramaticaFinal.y"
{this.addError("Falta '('.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 51:
//#line 158 "GramaticaFinal.y"
{this.addError("Falta condicion.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 52:
//#line 159 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 53:
//#line 160 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 54:
//#line 161 "GramaticaFinal.y"
{this.addError("Falta 'end_if'",lexico.getNroLinea());}
break;
case 55:
//#line 164 "GramaticaFinal.y"
{
															Token t1 = ((Token)val_peek(2).obj);
															Token t3 = ((Token)val_peek(0).obj);
															if (estaDeclarada(t1))
																if (estaDeclarada(t3)) 
																	if( esCompatible(t1,t3)){
																	   if (noHayErrores()){
																			TercetoAbstracto tercetoComparacion = new TercetoComparacion(((Token)val_peek(1).obj), t1, t3,analizadorTerceto.getProximoTerceto());
																			analizadorTerceto.addTerceto(tercetoComparacion);
																			
																			Token nuevo = new Token ( "@" + analizadorTerceto.getNumeroTerceto());
																			nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
																			nuevo.setOperador(((Token)val_peek(1).obj).getLexema());
																			yyval = new ParserVal(nuevo);
																		}
																	}
																	else
																		analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
																else{
																	analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
																	if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																		tablaSimbolos.eliminarClave(t3.getLexema());
																	else
																		tablaSimbolos.getClave(t3.getLexema()).decrementarRef();}
															else{
																analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
																if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																	tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
														}
break;
case 56:
//#line 197 "GramaticaFinal.y"
{this.addError("Falta expresion del lado izquierdo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 57:
//#line 198 "GramaticaFinal.y"
{this.addError("Falta expresion del lado derecho.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 58:
//#line 201 "GramaticaFinal.y"
{
															
															Token t1 = ((Token)val_peek(2).obj);
															Token t3 = ((Token)val_peek(0).obj);
															if (estaDeclarada(t1))
																if (estaDeclarada(t3)) 
																	if( esCompatible(t1,t3)){
																		if (noHayErrores()){
																			TercetoAbstracto tercetoExpresion = new TercetoExpresion(((Token)val_peek(1).obj), t1,t3,analizadorTerceto.getProximoTerceto());
																			analizadorTerceto.addTerceto(tercetoExpresion);
																			Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																			nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
																			nuevo.setUso("Variable Auxiliar");
																			tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
																			yyval = new ParserVal(nuevo);
																			}
																	}
																	else
																		analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
																else{
															analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
															if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t3.getLexema()).decrementarRef();
															}
															
														else{
															analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
															if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();
															}	
										
														}
break;
case 59:
//#line 240 "GramaticaFinal.y"
{
															Token t1 = ((Token)val_peek(2).obj);
															Token t3 = ((Token)val_peek(0).obj);
															
															if (estaDeclarada(t1))
																if (estaDeclarada(t3))
																	if( esCompatible(t1,t3)){
																		if (noHayErrores()){
																			TercetoAbstracto tercetoExpresion = new TercetoExpresion(((Token)val_peek(1).obj), t1, t3,analizadorTerceto.getProximoTerceto());
																			analizadorTerceto.addTerceto(tercetoExpresion);
								
																			Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
																			nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
																			nuevo.setUso("Variable Auxiliar");
																			tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
																			yyval = new ParserVal(nuevo);
																		}
																	}
																	else
																		analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
																else{
																	analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
																	if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}
															else{
																analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
																if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
									
														}
break;
case 61:
//#line 278 "GramaticaFinal.y"
{	
											Token t1 = ((Token)val_peek(2).obj);
											Token t3 = ((Token)val_peek(0).obj);
											if (estaDeclarada(t1))
												if (estaDeclarada(t3))
													if( esCompatible(t1,t3)){
														if (noHayErrores()){
															TercetoAbstracto tercetoExpresion = new TercetoExpresionMult(((Token)val_peek(1).obj),t1, t3,analizadorTerceto.getProximoTerceto());
															analizadorTerceto.addTerceto(tercetoExpresion);
															Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
															nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
															nuevo.setUso("Variable Auxiliar");
															tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
															yyval = new ParserVal(nuevo);
														}
													}
													else
														analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
												else{
											analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t3.getLexema()).decrementarRef();}
									else{
										analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
									
								}
break;
case 62:
//#line 313 "GramaticaFinal.y"
{  
									Token t1 = ((Token)val_peek(2).obj);
								    Token t3 = ((Token)val_peek(0).obj);
									if (estaDeclarada(t1))
										if (estaDeclarada(t3))
											if( esCompatible(t1,t3)){
												if (noHayErrores()){
													TercetoAbstracto tercetoExpresion = new TercetoExpresionDivision(((Token)val_peek(1).obj), t1, t3,analizadorTerceto.getProximoTerceto());
													analizadorTerceto.addTerceto(tercetoExpresion);
													Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
													nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
													nuevo.setUso("Variable Auxiliar");
													tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
													yyval = new ParserVal(nuevo);
													}
											}
											else
												analizadorTerceto.agregarError("Tipos incompatibles entre " + t1.getLexema() + " y " + t3.getLexema() + "." ,lexico.nroLinea);
										else{
											analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(t3.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t3.getLexema());
															else
																tablaSimbolos.getClave(t3.getLexema()).decrementarRef();}
									else{
										analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(t1.getLexema()).getCantRef()==1)
																tablaSimbolos.eliminarClave(t1.getLexema());
															else
																tablaSimbolos.getClave(t1.getLexema()).decrementarRef();}	
									}
break;
case 63:
//#line 348 "GramaticaFinal.y"
{}
break;
case 64:
//#line 352 "GramaticaFinal.y"
{}
break;
case 65:
//#line 353 "GramaticaFinal.y"
{actualizarTablaPositivo(((Token)val_peek(0).obj).getLexema());
					((Token)val_peek(0).obj).setTipo("int");
					if(((Token)val_peek(0).obj).getLexema().equals("32768"))
						((Token)val_peek(0).obj).setLexema("32767");
					yyval = val_peek(0);
					}
break;
case 66:
//#line 359 "GramaticaFinal.y"
{((Token)val_peek(0).obj).setTipo("float");
					tablaSimbolos.getClave(((Token)val_peek(0).obj).getLexema()).setUso("CTF");
					yyval = val_peek(0);
					}
break;
case 67:
//#line 363 "GramaticaFinal.y"
{actualizarTablaNegativo(((Token)val_peek(0).obj).getLexema());
						Token tNegativo = new Token("-"+((Token)val_peek(0).obj).getLexema());
						tNegativo.setTipo("int");
						yyval = new ParserVal(tNegativo);}
break;
case 68:
//#line 367 "GramaticaFinal.y"
{actualizarTablaNegativoFloat(((Token)val_peek(0).obj).getLexema());
						Token tNegativo = new Token("-"+((Token)val_peek(0).obj).getLexema());
						tNegativo.setTipo("float");
						yyval = new ParserVal(tNegativo);}
break;
case 70:
//#line 372 "GramaticaFinal.y"
{Token tNegativo = new Token("-"+((Token)val_peek(0).obj).getLexema());
							tNegativo.setTipo(((Token)val_peek(0).obj).getTipo());
							yyval = new ParserVal(tNegativo);}
break;
case 72:
//#line 379 "GramaticaFinal.y"
{	
								if (!estaDeclarada(((Token)val_peek(3).obj))){
										analizadorTerceto.agregarError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).decrementarRef();}
										yyval = new ParserVal(new Token("@" + ((Token)val_peek(3).obj).getLexema()));
								}
								else
									if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
										analizadorTerceto.agregarError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",lexico.nroLinea);
								if (!estaDeclarada(((Token)val_peek(1).obj))){
										analizadorTerceto.agregarError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)val_peek(1).obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).decrementarRef();}
									
								}
								else
									if (!tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getTipo().equals("int"))
										analizadorTerceto.agregarError("El tipo del subindice no es entero",lexico.nroLinea);
									else
										if (tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getUso().equals("Nombre de Coleccion"))
											analizadorTerceto.agregarError("Error '"+((Token)val_peek(1).obj).getLexema() + "' es una coleccion.",lexico.nroLinea);
										else{
											if (noHayErrores()){
												((Token)val_peek(3).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												((Token)val_peek(1).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												TercetoColeccionDer terceto = new TercetoColeccionDer((new Token("OFFSET")), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
												
												
												terceto.setTamanio(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												yyval = new ParserVal(nuevo);
											}
										}
								}
break;
case 73:
//#line 426 "GramaticaFinal.y"
{	
									if (!estaDeclarada(((Token)val_peek(3).obj))){
											analizadorTerceto.agregarError("Coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
											}
											else {
												tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).decrementarRef();}
											yyval = new ParserVal(new Token("@" + ((Token)val_peek(3).obj).getLexema()));
										
									}
									else{
										if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
											analizadorTerceto.agregarError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",lexico.nroLinea);
										else{
											if (noHayErrores()){
												((Token)val_peek(3).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												((Token)val_peek(1).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).setUso("CTE");
												TercetoColeccionDer terceto = new TercetoColeccionDer(new Token("OFFSET"), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
												terceto.setTamanio(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												
												yyval = new ParserVal(nuevo);
												}
										}
									}
								
								}
break;
case 74:
//#line 459 "GramaticaFinal.y"
{	
					Token t = ((Token)val_peek(0).obj);
					t.setTipo(tablaSimbolos.getClave(t.getLexema()).getTipo());
					yyval = new ParserVal(t);
				}
break;
case 84:
//#line 482 "GramaticaFinal.y"
{ if (noHayErrores()){
					analizadorTerceto.apilar();
					TercetoAbstracto tercetoEtiqueta = new TercetoEtiqueta(new Token("Lbl" + analizadorTerceto.getProximoTerceto()),null,null,analizadorTerceto.getProximoTerceto());
					tercetoEtiqueta.setNombre("Etiqueta");
					analizadorTerceto.addTerceto(tercetoEtiqueta);}}
break;
case 85:
//#line 488 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia until");
													if (noHayErrores()){
																TercetoDO tercetoDO = new TercetoDO(new Token("BF"), null,null,analizadorTerceto.getProximoTerceto());
																tercetoDO.setTipoSalto(((Token)val_peek(1).obj).getOperador(),((Token)val_peek(1).obj).getTipo());

															analizadorTerceto.addTerceto(tercetoDO);
															analizadorTerceto.desapilarControl();
	}
	}
break;
case 86:
//#line 497 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 87:
//#line 498 "GramaticaFinal.y"
{this.addError("Falta 'until'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 88:
//#line 499 "GramaticaFinal.y"
{this.addError("Falta '('.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 89:
//#line 500 "GramaticaFinal.y"
{this.addError("Falta condicion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 90:
//#line 501 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 92:
//#line 505 "GramaticaFinal.y"
{yyval = val_peek(0);}
break;
case 93:
//#line 507 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Sentencia PRINT");
												if (noHayErrores()){
												TercetoPrint tercetoPrint = new TercetoPrint ( ((Token)val_peek(3).obj), ((Token)val_peek(1).obj ), null, analizadorTerceto.getProximoTerceto() );
												tercetoPrint.setNombre("Print");
												analizadorTerceto.addTerceto(tercetoPrint);
												}
											}
break;
case 94:
//#line 514 "GramaticaFinal.y"
{this.addError("Falta 'PRINT'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 95:
//#line 515 "GramaticaFinal.y"
{this.addError("Falta '('.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 96:
//#line 516 "GramaticaFinal.y"
{this.addError("Falta variable o cadena .",((Token)val_peek(1).obj).getNroLinea());}
break;
case 97:
//#line 517 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 98:
//#line 520 "GramaticaFinal.y"
{	
								if (!estaDeclarada(((Token)val_peek(3).obj))){
										analizadorTerceto.agregarError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).decrementarRef();}
										yyval = new ParserVal(new Token("@" + ((Token)val_peek(3).obj).getLexema()));
								}
								else
									if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
										analizadorTerceto.agregarError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",lexico.nroLinea);
									
								if (!estaDeclarada(((Token)val_peek(1).obj))){
										analizadorTerceto.agregarError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",lexico.nroLinea);
										if (tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)val_peek(1).obj).getLexema());
										}
										else {
											tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).decrementarRef();}
								}
								else
									if (!tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getTipo().equals("int"))
										analizadorTerceto.agregarError("El tipo del subindice no es entero",lexico.nroLinea);
									else
										if (tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getUso().equals("Nombre de Coleccion"))
											analizadorTerceto.agregarError("Error '"+((Token)val_peek(1).obj).getLexema() + "' es una coleccion.",lexico.nroLinea);										
										else{
											if (noHayErrores()){
												((Token)val_peek(3).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												((Token)val_peek(1).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getTipo());
												TercetoColeccionIzq terceto = new TercetoColeccionIzq((new Token("OFFSET")), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
												terceto.setTamanio(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												yyval = new ParserVal(nuevo);
											}
									}
								}
break;
case 99:
//#line 565 "GramaticaFinal.y"
{	
									if (!estaDeclarada(((Token)val_peek(3).obj))){
											analizadorTerceto.agregarError("Coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",lexico.nroLinea);
											if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getCantRef() == 1) {
											tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
											}
											else {
												tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).decrementarRef();}
											yyval = new ParserVal(new Token("@" + ((Token)val_peek(3).obj).getLexema()));
									}
									else{
										if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
											analizadorTerceto.agregarError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",lexico.nroLinea);
										else{
											if (noHayErrores()){
												((Token)val_peek(3).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												((Token)val_peek(1).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getTipo());
												tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).setUso("CTE");
												TercetoColeccionIzq terceto = new TercetoColeccionIzq(new Token("OFFSET"), ((Token)val_peek(3).obj), ((Token)val_peek(1).obj),analizadorTerceto.getProximoTerceto());
												terceto.setTamanio(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTamanioColeccion());
												analizadorTerceto.addTerceto(terceto);
												Token nuevo = new Token( "@" + analizadorTerceto.getNumeroTerceto());
												nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getTipo());
												nuevo.setUso("Variable Auxiliar");
												tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
												
												yyval = new ParserVal(nuevo);
											}
										}
									}
								}
break;
case 100:
//#line 597 "GramaticaFinal.y"
{	
					Token t = ((Token)val_peek(0).obj);
					t.setTipo(tablaSimbolos.getClave(t.getLexema()).getTipo());
					yyval = new ParserVal(t);
				}
break;
case 101:
//#line 604 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + lexico.getNroLinea() + ": Asignacion");
														
														Token t1 = ((Token)val_peek(3).obj);
														Token t3 = ((Token)val_peek(1).obj);
														if (estaDeclarada(t1))
															if (estaDeclarada(t3))
																if( esCompatible(t1,t3)){
																	if (esColeccion(t1.getLexema())){
																		if (noHayErrores()){
																			TercetoAsignacionRowing tercetoAsignacionRowing = new TercetoAsignacionRowing(((Token)val_peek(2).obj), t1, t3,analizadorTerceto.getProximoTerceto());
																			 tercetoAsignacionRowing.setTamanio(tablaSimbolos.getClave(t1.getLexema()).getTamanioColeccion());

																			analizadorTerceto.addTerceto(tercetoAsignacionRowing);
																		}
																	}
																	else{
																		if (noHayErrores()){
																			TercetoAbstracto tercetoAsignacion = new TercetoAsignacion(((Token)val_peek(2).obj), t1, t3,analizadorTerceto.getProximoTerceto());			
																			analizadorTerceto.addTerceto(tercetoAsignacion);
																		}
																	}
																}
																else
																	analizadorTerceto.agregarError("Tipos incompatibles",lexico.nroLinea);
															else{
																analizadorTerceto.agregarError("Error variable '"+t3.getLexema() + "' no declarada.",lexico.nroLinea);
																tablaSimbolos.eliminarClave(t3.getLexema());}
														else{
															analizadorTerceto.agregarError("Error variable '"+t1.getLexema() + "' no declarada.",lexico.nroLinea);
															tablaSimbolos.eliminarClave(t1.getLexema());}	
														}
break;
case 102:
//#line 637 "GramaticaFinal.y"
{this.addError("Falta variable.",lexico.getNroLinea());}
break;
case 103:
//#line 638 "GramaticaFinal.y"
{this.addError("Falta ':='.",lexico.getNroLinea());}
break;
case 104:
//#line 639 "GramaticaFinal.y"
{this.addError("Falta expresion.",lexico.getNroLinea());}
break;
case 105:
//#line 640 "GramaticaFinal.y"
{this.addError("Falta ';'.",lexico.getNroLinea());}
break;
case 109:
//#line 648 "GramaticaFinal.y"
{ 
										
											if (esColeccion(((Token)val_peek(4).obj).getLexema())){
												if (noHayErrores()){
													((Token)val_peek(4).obj).setTipo(tablaSimbolos.getClave(((Token)val_peek(4).obj).getLexema()).getTipo());
													TercetoMetodos tercetoMetodo = new TercetoMetodos(new Token("METODO"), ((Token)val_peek(4).obj) , 					((Token)val_peek(2).obj),analizadorTerceto.getProximoTerceto());
													tercetoMetodo.setTamanio(tablaSimbolos.getClave(((Token)val_peek(4).obj).getLexema()).getTamanioColeccion()); 
													analizadorTerceto.addTerceto(tercetoMetodo);
											
													Token nuevo = new Token("@" + analizadorTerceto.getNumeroTerceto());
													if (((Token)val_peek(2).obj).getLexema().equals("length"))
														nuevo.setTipo("int");
													else
														nuevo.setTipo(tablaSimbolos.getClave(((Token)val_peek(4).obj).getLexema()).getTipo());
													nuevo.setUso("Variable Auxiliar");
													tablaSimbolos.agregar("auxiliar" +nuevo.getLexema(),nuevo);
													yyval = new ParserVal(nuevo);
												}
											}
											else
												analizadorTerceto.agregarError("La variable '" + ((Token)val_peek(4).obj).getLexema() +"' no puede invocar metodos, debe ser una coleccion." ,lexico.nroLinea);
										}
break;
case 110:
//#line 671 "GramaticaFinal.y"
{this.addError("Falta '(.'",((Token)val_peek(1).obj).getNroLinea());}
break;
case 111:
//#line 672 "GramaticaFinal.y"
{this.addError("Falta metodo.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 112:
//#line 673 "GramaticaFinal.y"
{this.addError("No es un metodo predefinido.",((Token)val_peek(3).obj).getNroLinea());}
break;
//#line 1645 "Parser.java"
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
