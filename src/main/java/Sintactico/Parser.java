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
	import Lexico.Registro;
//#line 26 "Parser.java"




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
    5,    5,    5,    5,    5,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,   12,
   12,   12,   14,   14,   14,   16,   16,   16,   17,   17,
   17,   17,   17,   17,   18,   18,   18,   15,   15,   15,
   15,   15,   15,   13,   13,   13,    9,    9,    9,    9,
    9,    9,    9,   10,   10,   10,   10,   10,   10,   11,
   11,   11,   11,   11,   20,   20,   20,   19,   19,   19,
};
final static short yylen[] = {                            2,
    1,    1,    4,    3,    2,    2,    1,    3,    3,    2,
    2,    2,    1,    2,    1,    3,    6,    5,    2,    5,
    2,    5,    5,    2,    5,    6,    1,    1,    3,    1,
    2,    1,    1,    1,    1,    8,    6,    7,    7,    7,
    7,    7,    7,    7,    5,    5,    5,    5,    5,    3,
    2,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    2,    2,    1,    4,    4,    1,    1,    1,    1,
    1,    1,    1,    1,    3,    2,    7,    6,    6,    6,
    6,    6,    6,    5,    4,    4,    5,    4,    4,    4,
    3,    3,    3,    3,    1,    1,    1,    5,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   27,    0,    0,   28,    0,    0,
    1,    0,    0,   13,   15,    0,    0,    0,   32,   33,
   34,    0,    0,    0,   60,   61,    0,    0,    0,   58,
   59,   64,   71,   69,   72,   73,    0,   68,   70,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,   74,
    0,    0,    0,    0,    0,   12,    5,   14,    0,   24,
    0,    0,   19,    0,   31,    0,    0,    0,    0,    0,
    0,   62,   63,    0,    0,   91,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    4,   76,    0,    0,
    0,    0,    0,    0,    0,    8,    0,    0,    0,   16,
   29,   93,    0,   92,   65,    0,    0,   95,   96,   97,
    0,    0,    0,    0,   56,   57,    0,    0,    0,    0,
   52,    0,   86,    0,   88,    0,   75,    0,    0,    0,
    0,   85,    0,    3,    0,    0,    0,    0,   90,   18,
   66,  100,    0,   99,    0,   47,    0,   49,    0,    0,
   48,    0,   46,   87,   84,    0,    0,    0,    0,    0,
    0,   45,   23,    0,   22,    0,   25,   98,    0,    0,
    0,   37,    0,    0,    0,   78,   81,   82,    0,   80,
   79,    0,   26,   17,   42,   40,   39,    0,   38,   41,
   43,   77,   44,   36,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   14,   50,   16,   17,   18,   19,   20,
   21,   40,   51,   41,   42,   29,   30,   22,   32,  112,
};
final static short yysindex[] = {                       -27,
  -80,  108,  407,  -35,    0,  302,  207,    0,  475,    0,
    0,  -16,  319,    0,    0,  -52,  -29,  -25,    0,    0,
    0,  -42, -240,  -36,    0,    0, -182,   -3,   24,    0,
    0,    0,    0,    0,    0,    0,  456,    0,    0,   -4,
  -41,  108,   16, -213,  -22,    0,  321,  342,   42,    0,
  -34,   52,   64,  388,  344,    0,    0,    0,  -83,    0,
 -159,    6,    0, -141,    0,   58,   27,   29,   33, -170,
  -40,    0,    0,  108,  108,    0,  108,  108,  363,  273,
  363,  155,  -18,   73,   94,  -13,    0,    0,  369,  487,
  425,  487,   77,  363,  386,    0,   46, -205,   47,    0,
    0,    0,   49,    0,    0,   82,   62,    0,    0,    0,
  101,   50,   24,   24,    0,    0, -172,  225, -157, -149,
    0,  -18,    0,   86,    0,   95,    0,  115,  465,  116,
  117,    0, -137,    0,  100,   67,  -45,  102,    0,    0,
    0,    0,  121,    0,  363,    0,  363,    0,  246,  363,
    0,  363,    0,    0,    0,  104,  105,    3,  106,  112,
  363,    0,    0,  113,    0,  114,    0,    0,  -92,  -91,
  296,    0,  -90,  -89,  -88,    0,    0,    0,  122,    0,
    0,  -86,    0,    0,    0,    0,    0,  -85,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  445,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  184,  185,    0,    0,    0,    0,  128,    0,    0,
    0,    0,    0,   38,    0,    0,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  129,    0,  186,    0,    0,    0,
    0,    0,    0,    0,  187,    0,    0,    0,    1,    0,
    0,   12,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  190,    0,    0,    0,    0,    0,
    0,    0,  149,    0,    0,  418,    0,    0,    0,    0,
    0,    0,   84,  107,    0,    0,    0,    0,    0,    0,
    0,  279,    0,    0,    0,  170,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  188,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   10,  179,   20,    0,  176,    0,    0,    0,
    0,   22,  678,    7,  152,   59,   60,  689,    0,    0,
};
final static int YYTABLESIZE=849;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        111,
   30,   74,   27,   75,   44,   92,   60,   98,   28,   71,
   23,   21,    9,  165,   64,   47,   68,   69,   38,   15,
   39,   55,   20,    9,   74,   15,   75,  126,   67,   63,
   53,   15,   58,   65,   51,   51,   81,   67,   61,   74,
   30,   75,   85,  179,   30,  125,   86,  166,   83,   64,
  136,   21,  137,   51,   70,   76,   84,   89,   80,   30,
   55,  178,   20,   95,  100,   77,   58,   15,   70,   74,
   78,   75,  103,   15,   58,   72,   73,   67,   67,   67,
   67,   90,   67,   53,   67,  104,   68,  107,  122,  143,
  144,   74,   93,   75,  145,  146,   67,   67,   99,   67,
   55,   55,   27,   55,   94,   55,   54,  139,   58,  150,
  151,  128,  130,  131,   58,  101,  102,  152,  153,   55,
   55,  105,   55,   53,   53,  106,   53,   35,   53,  161,
  162,  123,  113,  114,  124,  132,  115,  116,  135,  138,
  140,  142,   53,   53,  154,   53,   54,   54,   94,   54,
  158,   54,   27,  155,  141,  156,  159,  160,  163,  164,
  167,  168,  176,  177,  180,   54,   54,   35,   54,   89,
  181,  183,  184,   67,   97,  185,  186,  189,  190,  191,
  192,  193,  194,    2,    7,    6,   10,   83,   94,    9,
   56,   62,   82,    0,    0,    0,    0,    0,    0,   27,
    0,    0,    0,    0,   59,    0,    0,    0,    0,   89,
    0,    0,    0,    0,   24,   25,   26,    0,   66,    0,
   33,   34,   35,   36,   43,    0,    0,   83,    0,    1,
    0,    0,    0,    2,    0,  108,  109,  110,    3,   91,
    1,    4,    5,    6,    2,    7,    9,    8,    0,    3,
    0,   51,    4,    5,   54,   51,    7,   30,    8,    0,
   51,   30,    0,   51,    9,   51,   30,   51,   21,   30,
   30,   30,   21,   30,    0,   30,    0,   21,    0,   20,
   21,   21,   21,   20,   21,    9,   21,    0,   20,    0,
    0,   20,   20,   20,   67,   20,    0,   20,   67,   67,
   67,   67,   67,   67,   67,   67,   67,    0,   67,   67,
   67,   67,    9,  118,   24,   25,   26,   55,   50,   50,
    0,   55,   55,   55,   55,   55,   55,   55,   55,   55,
    0,   55,   55,   55,   55,    9,    0,   50,    0,    0,
   53,    9,    0,    0,   53,   53,   53,   53,   53,   53,
   53,   53,   53,    0,   53,   53,   53,   53,    9,    0,
    9,    0,    0,   54,   24,   25,   26,   54,   54,   54,
   54,   54,   54,   54,   54,   54,    0,   54,   54,   54,
   54,    9,    0,    9,   35,   67,   67,   67,   35,   67,
    0,    0,    0,   35,   35,   35,   35,    0,   35,   35,
   35,   35,    9,    0,    0,   94,    0,    0,    9,   94,
  121,   24,   25,   26,   94,   94,   94,   94,    0,   94,
   94,   94,   94,    0,    0,    9,   89,    9,    0,    0,
   89,    0,    0,    0,    0,   89,   89,   89,   89,    0,
   89,   89,   89,   89,   83,    0,   37,    0,   83,    0,
    0,   27,    0,   83,   83,   83,   83,    0,   83,   83,
   83,   83,   66,   45,  129,    0,   38,    2,   39,   27,
    0,    0,    3,    0,    0,    4,    0,   48,    0,    7,
   49,   45,    0,    0,   38,    2,   39,    0,   30,   67,
    3,  147,  148,    4,    0,   48,   79,    7,    0,    0,
   27,    0,   45,   30,    0,  157,    2,    0,    0,   27,
    0,    3,  171,  172,    4,   38,   48,   39,    7,   27,
    0,    0,    0,    0,   38,    0,   39,    0,    0,   45,
    0,   27,    0,    2,   38,   50,   39,    0,    3,   50,
    0,    4,    0,   48,   50,    7,   38,   50,   39,   50,
    0,   50,   45,    0,    0,    0,    2,    0,   45,    0,
    0,    3,    2,  187,    4,    0,   48,    3,    7,    0,
    4,    0,    0,   46,    7,   45,    0,   45,    0,    2,
    0,    2,    0,    0,    3,    0,    3,    4,    0,    4,
   57,    7,   87,    7,    0,    0,    0,    0,   45,    0,
   45,    0,    2,    0,    2,    0,    0,    3,    0,    3,
    4,    0,    4,   88,    7,   96,    7,    0,    0,   45,
    0,    0,    0,    2,    0,   45,    0,    0,    3,    2,
    0,    4,    0,   48,    3,    7,    0,    4,    0,    0,
  127,    7,   45,    0,   45,    0,    2,    0,    2,    0,
    0,    3,    0,    3,    4,    0,    4,  134,    7,    0,
    7,    0,    0,   24,   25,   26,    0,    0,   33,   34,
   35,   36,    0,    0,   66,   66,   66,    0,   66,    0,
    0,   24,   25,   26,    0,    0,   33,   34,   35,   36,
   31,   31,    0,    0,    0,    0,    0,   31,    0,    0,
    0,   67,   67,   67,    0,   67,    0,    0,    0,    0,
   31,    0,   24,   25,   26,    0,    0,   33,   34,   35,
   36,   24,   25,   26,    0,   31,   33,   34,   35,   36,
   31,   24,   25,   26,   52,    0,   33,   34,   35,   36,
    0,    0,    0,   24,   25,   26,    0,    0,   33,   34,
   35,   36,    0,    0,   31,    0,  117,  119,  120,    0,
    0,    0,   31,   31,    0,   31,   31,    0,    0,    0,
   31,  133,    0,    0,    0,    0,    0,    0,   31,   31,
   31,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  149,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   31,    0,    0,
    0,    0,  169,    0,  170,    0,  173,  174,    0,  175,
    0,    0,    0,    0,    0,    0,    0,    0,  182,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  188,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   45,   45,   40,   40,   59,   91,    2,   46,
   91,    0,   40,   59,   44,    6,  257,  258,   60,    0,
   62,   12,    0,   40,   43,    6,   45,   41,   22,   59,
    9,   12,   13,   59,   40,   41,   41,    0,   91,   43,
   40,   45,  256,   41,   44,   59,  260,   93,   42,   44,
  256,   40,  258,   59,   91,   59,   41,   48,   37,   59,
    0,   59,   40,   54,   59,   42,   47,   48,   91,   43,
   47,   45,   66,   54,   55,  258,  259,   40,   41,   42,
   43,   40,   45,    0,   47,   59,  257,  258,   82,   40,
   41,   43,   41,   45,  267,  268,   59,   60,  258,   62,
   40,   41,   45,   43,   41,   45,    0,   59,   89,  267,
  268,   90,   91,   92,   95,  257,   59,  267,  268,   59,
   60,   93,   62,   40,   41,   93,   43,    0,   45,  267,
  268,   59,   74,   75,   41,   59,   77,   78,   93,   93,
   59,   41,   59,   60,   59,   62,   40,   41,    0,   43,
  129,   45,   45,   59,   93,   41,   41,   41,   59,   93,
   59,   41,   59,   59,   59,   59,   60,   40,   62,    0,
   59,   59,   59,   45,  258,  268,  268,  268,  268,  268,
   59,  268,  268,    0,    0,    0,    0,    0,   40,    0,
   12,   16,   41,   -1,   -1,   -1,   -1,   -1,   -1,   45,
   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   -1,  257,  258,  259,   -1,  261,   -1,
  262,  263,  264,  265,  260,   -1,   -1,   40,   -1,  257,
   -1,   -1,   -1,  261,   -1,  276,  277,  278,  266,  274,
  257,  269,  270,  271,  261,  273,   40,  275,   -1,  266,
   -1,  257,  269,  270,  271,  261,  273,  257,  275,   -1,
  266,  261,   -1,  269,   40,  271,  266,  273,  257,  269,
  270,  271,  261,  273,   -1,  275,   -1,  266,   -1,  257,
  269,  270,  271,  261,  273,   40,  275,   -1,  266,   -1,
   -1,  269,  270,  271,  257,  273,   -1,  275,  261,  262,
  263,  264,  265,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,   40,   41,  257,  258,  259,  257,   40,   41,
   -1,  261,  262,  263,  264,  265,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,   40,   -1,   59,   -1,   -1,
  257,   40,   -1,   -1,  261,  262,  263,  264,  265,  266,
  267,  268,  269,   -1,  271,  272,  273,  274,   40,   -1,
   40,   -1,   -1,  257,  257,  258,  259,  261,  262,  263,
  264,  265,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,   40,   -1,   40,  257,  257,  258,  259,  261,  261,
   -1,   -1,   -1,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,   40,   -1,   -1,  257,   -1,   -1,   40,  261,
  256,  257,  258,  259,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,   -1,   40,  257,   40,   -1,   -1,
  261,   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,
  271,  272,  273,  274,  257,   -1,   40,   -1,  261,   -1,
   -1,   45,   -1,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,   45,  257,   40,   -1,   60,  261,   62,   45,
   -1,   -1,  266,   -1,   -1,  269,   -1,  271,   -1,  273,
  274,  257,   -1,   -1,   60,  261,   62,   -1,   44,   45,
  266,  267,  268,  269,   -1,  271,   41,  273,   -1,   -1,
   45,   -1,  257,   59,   -1,   41,  261,   -1,   -1,   45,
   -1,  266,  267,  268,  269,   60,  271,   62,  273,   45,
   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,  257,
   -1,   45,   -1,  261,   60,  257,   62,   -1,  266,  261,
   -1,  269,   -1,  271,  266,  273,   60,  269,   62,  271,
   -1,  273,  257,   -1,   -1,   -1,  261,   -1,  257,   -1,
   -1,  266,  261,  268,  269,   -1,  271,  266,  273,   -1,
  269,   -1,   -1,  272,  273,  257,   -1,  257,   -1,  261,
   -1,  261,   -1,   -1,  266,   -1,  266,  269,   -1,  269,
  272,  273,  272,  273,   -1,   -1,   -1,   -1,  257,   -1,
  257,   -1,  261,   -1,  261,   -1,   -1,  266,   -1,  266,
  269,   -1,  269,  272,  273,  272,  273,   -1,   -1,  257,
   -1,   -1,   -1,  261,   -1,  257,   -1,   -1,  266,  261,
   -1,  269,   -1,  271,  266,  273,   -1,  269,   -1,   -1,
  272,  273,  257,   -1,  257,   -1,  261,   -1,  261,   -1,
   -1,  266,   -1,  266,  269,   -1,  269,  272,  273,   -1,
  273,   -1,   -1,  257,  258,  259,   -1,   -1,  262,  263,
  264,  265,   -1,   -1,  257,  258,  259,   -1,  261,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,  265,
    2,    3,   -1,   -1,   -1,   -1,   -1,    9,   -1,   -1,
   -1,  257,  258,  259,   -1,  261,   -1,   -1,   -1,   -1,
   22,   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,
  265,  257,  258,  259,   -1,   37,  262,  263,  264,  265,
   42,  257,  258,  259,  260,   -1,  262,  263,  264,  265,
   -1,   -1,   -1,  257,  258,  259,   -1,   -1,  262,  263,
  264,  265,   -1,   -1,   66,   -1,   79,   80,   81,   -1,
   -1,   -1,   74,   75,   -1,   77,   78,   -1,   -1,   -1,
   82,   94,   -1,   -1,   -1,   -1,   -1,   -1,   90,   91,
   92,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  118,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  129,   -1,   -1,
   -1,   -1,  145,   -1,  147,   -1,  149,  150,   -1,  152,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  161,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  171,
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
"sentencia_ejecutable : control_ejecutable",
"sentencia_ejecutable : salida_ejecutable",
"sentencia_ejecutable : asign",
"sentencia_ejecutable : seleccion_ejecutable",
"seleccion_ejecutable : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion ')' bloque_sentencias bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion ')' bloque_sentencias ELSE END_IF",
"seleccion_ejecutable : IF '(' condicion ')' ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : IF condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"seleccion_ejecutable : '(' condicion ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF condicion ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' ')' bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion bloque_sentencias END_IF",
"seleccion_ejecutable : IF '(' condicion ')' END_IF",
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
"control_ejecutable : DO bloque_sentencias UNTIL '(' condicion ')' ';'",
"control_ejecutable : DO UNTIL '(' condicion ')' ';'",
"control_ejecutable : DO bloque_sentencias '(' condicion ')' ';'",
"control_ejecutable : DO bloque_sentencias UNTIL condicion ')' ';'",
"control_ejecutable : DO bloque_sentencias UNTIL '(' ')' ';'",
"control_ejecutable : DO bloque_sentencias UNTIL '(' condicion ';'",
"control_ejecutable : DO bloque_sentencias UNTIL '(' condicion ')'",
"salida_ejecutable : PRINT '(' CADENA ')' ';'",
"salida_ejecutable : '(' CADENA ')' ';'",
"salida_ejecutable : PRINT CADENA ')' ';'",
"salida_ejecutable : PRINT '(' error ')' ';'",
"salida_ejecutable : PRINT '(' CADENA ';'",
"salida_ejecutable : PRINT '(' CADENA ')'",
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

//#line 227 "GramaticaCorreciones.y"




private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

private ArrayList<Token> listaVariables = new ArrayList<Token>();
private ArrayList<String> listaCompatibilidad = new ArrayList<String>();


public Parser(AnalizadorLexico lexico, TablaSimbolos tablaSimbolos) {
	this.lexico = lexico;
	this.tablaSimbolos = tablaSimbolos;
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
		if(lexema.equals(String.valueOf(Short.MAX_VALUE+1))){
			if (tablaSimbolos.getToken(lexema).getCantRef()==1){
				tablaSimbolos.eliminarClave(lexema);
			}
			else
				tablaSimbolos.getToken(lexema).decrementarRef();
		
		if(tablaSimbolos.existeClave(String.valueOf(Short.MAX_VALUE))) 
			tablaSimbolos.getToken(String.valueOf(Short.MAX_VALUE)).incrementarRef();
		else 
			tablaSimbolos.agregar(String.valueOf(Short.MAX_VALUE), new Registro("int"));
		}
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.existeClave(lexema)) {
		if (tablaSimbolos.getToken(lexema).getCantRef()==1){
			tablaSimbolos.eliminarClave(lexema);
		}
		else
			tablaSimbolos.getToken(lexema).decrementarRef();
	}
	if(tablaSimbolos.existeClave(lexemaNuevo)) {
		tablaSimbolos.getToken(lexemaNuevo).incrementarRef();
	}
	else
		tablaSimbolos.agregar(lexemaNuevo, new Registro("int"));
}




public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
		if (tablaSimbolos.existeClave(lexema))
			if(tablaSimbolos.getToken(lexema).getCantRef() == 1) 
				tablaSimbolos.eliminarClave(lexema);
			else
				tablaSimbolos.getToken(lexema).decrementarRef();
		if(tablaSimbolos.existeClave(lexemaNuevo)) 
			tablaSimbolos.getToken(lexemaNuevo).incrementarRef();
		else
			tablaSimbolos.agregar(lexemaNuevo, new Registro("float"));
}

/*
public void actualizarTablaPositivoFloat(String lexema) {
	if(lexema.equals(String.valueOf(Float.MAX_VALUE))){
		if (tablaSimbolos.getToken(lexema).getCantRef()==1){
			tablaSimbolos.eliminarClave(lexema);
		}
		else
			tablaSimbolos.getToken(lexema).decrementarRef();
	
	if(tablaSimbolos.existeClave(String.valueOf(Short.MAX_VALUE))) 
		tablaSimbolos.getToken(String.valueOf(Short.MAX_VALUE)).incrementarRef();
	else 
		tablaSimbolos.agregar(String.valueOf(Short.MAX_VALUE), new Registro("int"));
	}
}
*/

public void actualizarTablaVariables(Token tipo,String uso,Token cteE) {
	String lexema = "";
	Token t = null;
	
	while(!listaVariables.isEmpty()) {
		t = listaVariables.get(0);
		listaVariables.remove(0);
		lexema = t.getLexema();
		if(!tablaSimbolos.getToken(lexema).getDeclarada()) {
			tablaSimbolos.getToken(lexema).setTipo(tipo.getLexema());
			tablaSimbolos.getToken(lexema).setDeclarada(true);
			tablaSimbolos.getToken(lexema).setUso(uso);
			if(tablaSimbolos.getToken(lexema).getUso().equals("Nombre de Coleccion")) {
				tablaSimbolos.getToken(lexema).setTamanioColeccion(Integer.parseInt(cteE.getLexema()));
			}
		}
		else
			this.addError("Error en declaracion, variable o coleccion '" + lexema + "' redeclarada",t.getNroLinea());
	}
}

public boolean estaDeclarada(Token t){
	if (tablaSimbolos.getToken(t.getLexema()).getDeclarada())
		return true;
	else
		return false;
}

public boolean esCompatible(Token t1, Token t2){
	if (tablaSimbolos.getToken(t1.getLexema()).getTipo().equals(tablaSimbolos.getToken(t2.getLexema()).getTipo()))
		return true;
	return false;
}

public boolean sonCompatibles(String tipoAComparar){
	for (String tipo : listaCompatibilidad){
		if (!tipo.equals(tipoAComparar))
			return false;
	}
	return true;
}
//#line 694 "Parser.java"
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
//#line 53 "GramaticaCorreciones.y"
{this.addError("Error, falta BEGIN.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 6:
//#line 54 "GramaticaCorreciones.y"
{this.addError("Error, falta END.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 7:
//#line 55 "GramaticaCorreciones.y"
{this.addError("Error, falta los delimitadores BEGIN y END.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 8:
//#line 56 "GramaticaCorreciones.y"
{this.addError("Error, falta BEGIN.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 9:
//#line 57 "GramaticaCorreciones.y"
{this.addError("Error, falta END.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 10:
//#line 58 "GramaticaCorreciones.y"
{this.addError("Error, falta los delimitadores BEGIN  y END.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 11:
//#line 59 "GramaticaCorreciones.y"
{this.addError("Error, falta sentencias ejecutables.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 16:
//#line 71 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(2).obj).getNroLinea() + ": Sentencia declarativa");
														actualizarTablaVariables(((Token)val_peek(2).obj),"Variable",((Token)val_peek(2).obj));}
break;
case 17:
//#line 73 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia declarativa");
														listaVariables.add(((Token)val_peek(4).obj));
														actualizarTablaVariables(((Token)val_peek(5).obj),"Nombre de Coleccion",((Token)val_peek(2).obj));}
break;
case 18:
//#line 76 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 19:
//#line 77 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 20:
//#line 78 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 21:
//#line 79 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 22:
//#line 80 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta ']'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 23:
//#line 81 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta '['.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 24:
//#line 82 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta variables.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 25:
//#line 83 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, falta identIFicador.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 26:
//#line 84 "GramaticaCorreciones.y"
{this.addError("Error en declaracion, la coleccion solo permite constantes enteras.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 29:
//#line 91 "GramaticaCorreciones.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 30:
//#line 92 "GramaticaCorreciones.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 35:
//#line 101 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 36:
//#line 104 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(7).obj).getNroLinea() + ": Sentencia IF-ELSE");}
break;
case 37:
//#line 105 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia IF");}
break;
case 38:
//#line 106 "GramaticaCorreciones.y"
{this.addError("Falta 'ELSE'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 39:
//#line 107 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 40:
//#line 108 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 41:
//#line 109 "GramaticaCorreciones.y"
{this.addError( "Falta ')'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 42:
//#line 110 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 43:
//#line 111 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 44:
//#line 112 "GramaticaCorreciones.y"
{this.addError("Falta 'IF'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 45:
//#line 113 "GramaticaCorreciones.y"
{this.addError("Falta 'IF'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 46:
//#line 114 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 47:
//#line 115 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 48:
//#line 116 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 49:
//#line 117 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 50:
//#line 121 "GramaticaCorreciones.y"
{if (!sonCompatibles(tablaSimbolos.getToken(((Token)val_peek(2).obj).getLexema()).getTipo()))	
																this.addError("Error tipos incompatibles en comparacion.",((Token)val_peek(0).obj).getNroLinea());
															listaCompatibilidad.clear();}
break;
case 51:
//#line 124 "GramaticaCorreciones.y"
{this.addError("Falta expresion del lado izquierdo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 52:
//#line 125 "GramaticaCorreciones.y"
{this.addError("Falta expresion del lado derecho.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 58:
//#line 135 "GramaticaCorreciones.y"
{//listaCompatibilidad.add(((Token)val_peek(0).obj).getLexema().getTipo());
	}

break;
case 59:
//#line 138 "GramaticaCorreciones.y"
{}
break;
case 60:
//#line 139 "GramaticaCorreciones.y"
{actualizarTablaPositivo(((Token)val_peek(0).obj).getLexema());}
break;
case 62:
//#line 141 "GramaticaCorreciones.y"
{actualizarTablaNegativo(((Token)val_peek(0).obj).getLexema());}
break;
case 63:
//#line 142 "GramaticaCorreciones.y"
{actualizarTablaNegativoFloat(((Token)val_peek(0).obj).getLexema());}
break;
case 65:
//#line 146 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj)))
									this.addError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
								else
									if (tablaSimbolos.getToken(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
										this.addError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",((Token)val_peek(3).obj).getNroLinea());
								if (!estaDeclarada(((Token)val_peek(1).obj)))
									this.addError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",((Token)val_peek(1).obj).getNroLinea());
								else
									if (!tablaSimbolos.getToken(((Token)val_peek(1).obj).getLexema()).getTipo().equals("int"))
										this.addError("Error El tipo del subindice no es entero",((Token)val_peek(1).obj).getNroLinea());
								else
									if (tablaSimbolos.getToken(((Token)val_peek(1).obj).getLexema()).getUso().equals("Nombre de Coleccion"))
										this.addError("Error '"+((Token)val_peek(1).obj).getLexema() + "' es una coleccion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 66:
//#line 160 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj)))
										this.addError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
									else
										if (tablaSimbolos.getToken(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
											this.addError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 67:
//#line 165 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(0).obj)))
						this.addError("Error variable '"+((Token)val_peek(0).obj).getLexema() + "' no declarada.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 77:
//#line 185 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(6).obj).getNroLinea() + ": Sentencia until");}
break;
case 78:
//#line 186 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 79:
//#line 187 "GramaticaCorreciones.y"
{this.addError("Falta 'until'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 80:
//#line 188 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 81:
//#line 189 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 82:
//#line 190 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 83:
//#line 191 "GramaticaCorreciones.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 84:
//#line 195 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(4).obj).getNroLinea() + ": Sentencia PRINT");}
break;
case 85:
//#line 196 "GramaticaCorreciones.y"
{this.addError("Falta 'PRINT'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 86:
//#line 197 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 87:
//#line 198 "GramaticaCorreciones.y"
{this.addError("Solo se puede definir una cadena.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 88:
//#line 199 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 89:
//#line 200 "GramaticaCorreciones.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 90:
//#line 203 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Asignacion");
															
															if (!sonCompatibles(tablaSimbolos.getToken(((Token)val_peek(3).obj) .getLexema()).getTipo()))	
																this.addError("Error tipos incompatibles.",((Token)val_peek(1).obj).getNroLinea());
																
															listaCompatibilidad.clear();	
														}
break;
case 91:
//#line 210 "GramaticaCorreciones.y"
{this.addError("Falta variable.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 92:
//#line 211 "GramaticaCorreciones.y"
{this.addError("Falta ':='.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 93:
//#line 212 "GramaticaCorreciones.y"
{this.addError("Falta expresion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 94:
//#line 213 "GramaticaCorreciones.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 99:
//#line 222 "GramaticaCorreciones.y"
{this.addError("Falta '(.'",((Token)val_peek(1).obj).getNroLinea());}
break;
case 100:
//#line 223 "GramaticaCorreciones.y"
{this.addError("Falta metodo.",((Token)val_peek(2).obj).getNroLinea());}
break;
//#line 1135 "Parser.java"
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