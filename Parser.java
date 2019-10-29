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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    3,    3,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    6,
    6,    7,    7,    5,    5,    5,    5,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,   12,   12,   12,   14,   14,   14,   16,   16,   16,
   17,   17,   17,   17,   17,   17,   18,   18,   18,   15,
   15,   15,   15,   15,   15,   13,   13,    9,    9,    9,
    9,    9,    9,    9,   10,   10,   10,   10,   10,   10,
   11,   11,   11,   11,   11,   19,   19,   19,
};
final static short yylen[] = {                            2,
    1,    1,    4,    3,    2,    1,    2,    1,    3,    6,
    5,    2,    5,    2,    5,    5,    2,    5,    6,    1,
    1,    3,    1,    1,    1,    1,    1,    8,    6,    7,
    7,    7,    7,    7,    7,    7,    5,    5,    5,    5,
    5,    3,    2,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    2,    2,    1,    4,    4,    1,    1,
    1,    1,    1,    1,    1,    1,    4,    7,    6,    6,
    6,    6,    6,    6,    5,    4,    4,    5,    4,    4,
    4,    3,    3,    3,    3,    5,    4,    4,
};
final static short yydefred[] = {                         0,
    0,   20,    0,   21,    0,    1,    0,    6,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    8,   24,
   25,   26,   27,    0,    0,    5,    0,   17,    0,    0,
   12,    0,    0,    0,    0,   52,   53,    0,    0,    0,
   50,   51,   56,   63,   61,   64,   65,    0,   60,   62,
    0,    0,    0,    0,    0,    0,    0,   66,    0,    0,
    0,    4,    7,    0,    0,    0,    0,    0,    0,    9,
   22,    0,    0,    0,    0,   54,   55,    0,    0,   82,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   84,    0,   83,    3,
    0,    0,    0,    0,   11,   57,   58,    0,    0,    0,
    0,   48,   49,    0,    0,    0,    0,   44,    0,   77,
    0,   79,    0,    0,    0,    0,    0,    0,   76,    0,
   81,   16,    0,   15,    0,   18,    0,   87,   88,    0,
   39,    0,   41,    0,    0,   40,    0,   38,   78,   75,
   67,    0,    0,    0,    0,    0,    0,   37,   19,   10,
   86,    0,    0,    0,   29,    0,    0,    0,   69,   72,
   73,    0,   71,   70,    0,   34,   32,   31,    0,   30,
   33,   35,   68,   36,   28,
};
final static short yydgoto[] = {                          5,
    6,    7,   18,    8,   58,    9,   10,   20,   21,   22,
   23,   51,   59,   52,   53,   40,   41,   24,   43,
};
final static short yysindex[] = {                      -237,
  -56,    0,  243,    0,    0,    0, -231,    0,  -42,   15,
 -216,  -33,   71,  306,  -36,  124,  390,  222,    0,    0,
    0,    0,    0,   44,  243,    0,  -78,    0, -196,   26,
    0, -189,   19, -203,  -28,    0,    0, -158,   -2,  -11,
    0,    0,    0,    0,    0,    0,    0,  366,    0,    0,
   30,   60,   71,   35, -208,  243,   56,    0,  -29,   61,
   67,    0,    0,  -45,   32,  224,   28, -228,   54,    0,
    0,   78,   55,   57,  -38,    0,    0,   71,   71,    0,
   71,   71,  242,  201,  242,   11,   37,   90,  110,  -22,
  243,  399,  324,  399,   93,  242,    0,   36,    0,    0,
   94,   62,  -43,   95,    0,    0,    0,   77,  115,  -11,
  -11,    0,    0, -144,  135, -134, -132,    0,   37,    0,
   99,    0,  100, -153,  119,  379,  120,  121,    0, -127,
    0,    0,  104,    0,  107,    0,  126,    0,    0,  242,
    0,  242,    0,  148,  242,    0,  242,    0,    0,    0,
    0,  109,  111,  -12,  112,  113,  242,    0,    0,    0,
    0, -103,  -99,  207,    0,  -95,  -94,  -92,    0,    0,
    0,  118,    0,    0,  -90,    0,    0,    0,  -89,    0,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
   34,    0,    0,    0,    0,    0,  181,    0,    0,    0,
    0,  261,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    0,    0,    3,
    0,    0,    0,    0,  -35,    0,    0,    0,    0,   24,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  161,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -17,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
   70,    0,    0,    0,    0,    0,    0,    0,  176,    0,
    0,    0,   88,  260,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  106,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -10,  178,   48,    0,  174,    0,    0,    0,
    0,    5,   42,    8,  138,   64,   63,  514,    0,
};
final static int YYTABLESIZE=664;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   23,  109,   14,   55,   59,   59,   59,   59,   13,   59,
   94,   59,   68,   97,   66,  134,   28,   75,  123,    1,
   39,   61,   85,   59,   59,    1,   59,  102,  172,  103,
   81,   65,    2,    3,   11,   82,  122,    4,    2,   25,
   78,   33,   79,    4,   23,   91,  171,   89,   29,  135,
   19,   90,   84,   73,   74,   38,   80,   34,   32,   23,
   87,   69,   34,   47,   47,   63,   47,   71,   47,   32,
   85,   98,   19,   31,   78,   88,   79,   23,   78,   78,
   79,   79,   47,   47,   70,   47,   45,   45,   38,   45,
   99,   45,   23,  119,  131,   92,  125,  127,  128,   76,
   77,   95,   78,   19,   79,   45,   45,   96,   45,   46,
   46,   72,   46,   63,   46,   38,  137,  138,  151,   49,
  101,   50,  140,  141,  114,  116,  117,   80,   46,   46,
  154,   46,  145,  146,  147,  148,  105,  130,  124,  157,
  158,  110,  111,  112,  113,   74,  104,  106,  120,  107,
  121,  129,  132,  136,  133,  139,  144,  149,  150,  152,
  155,  156,  159,   17,  176,  160,  161,  169,  177,  170,
  173,  174,  180,  181,   17,  182,  183,  184,  185,   67,
    2,  162,   30,  163,   26,  166,  167,   17,  168,   86,
    0,    0,    0,    0,    0,    0,    0,    0,  175,    0,
   43,   43,    0,    0,    0,  179,    0,    0,    0,    0,
    0,   35,   36,   37,   27,   42,   42,    0,  108,   43,
    0,   59,    0,   54,    0,   59,   59,   59,   59,   59,
   59,   59,   59,   59,   42,   59,   59,   59,   59,   85,
   17,  115,    0,   85,   93,    0,   17,    0,   85,   85,
   85,   85,    0,   85,   85,   85,   85,   23,    0,   14,
    0,   17,    0,   17,    0,   13,  118,   35,   36,   37,
   23,   23,   14,   14,    0,   23,    0,   14,   13,   13,
   47,   17,   17,   13,   47,   47,   47,   47,   47,   47,
   47,   47,   47,    0,   47,   47,   47,   47,    0,    7,
   35,   36,   37,   45,   64,   59,    0,   45,   45,   45,
   45,   45,   45,   45,   45,   45,    0,   45,   45,   45,
   45,   44,   45,   46,   47,    0,   46,   35,   36,   37,
   46,   46,   46,   46,   46,   46,   46,   46,   46,    0,
   46,   46,   46,   46,   80,   48,    0,    0,   80,    0,
   38,    0,    0,   80,   80,   80,   80,    0,   80,   80,
   80,   80,   74,  126,    0,   49,   74,   50,   38,    0,
    0,   74,   74,   74,   74,    0,   74,   74,   74,   74,
   12,    0,    0,   49,   13,   50,    0,    0,    0,   14,
    0,   12,   15,    0,   56,   13,   16,   57,    0,    0,
   14,  142,  143,   15,   12,   56,   83,   16,   13,    0,
   38,    0,    0,   14,  164,  165,   15,   43,   56,  153,
   16,   43,    0,   38,    0,   49,   43,   50,    0,   43,
    0,   43,   42,   43,   38,    0,   42,    0,   49,    0,
   50,   42,    0,   38,   42,    0,   42,    0,   42,   49,
    0,   50,    0,    0,    0,    0,    0,   12,   49,    0,
   50,   13,    0,   12,    0,    0,   14,   13,    0,   15,
    0,   56,   14,   16,  178,   15,    0,   56,   12,   16,
   12,    0,   13,    0,   13,    0,    0,   14,    0,   14,
   15,    0,   15,   62,   16,  100,   16,    0,   12,   12,
    0,    0,   13,   13,    0,    0,    0,   14,   14,    0,
   15,   15,   56,    0,   16,   16,    7,   59,   59,   59,
    7,   59,    0,    0,    0,    7,   42,   42,    7,    0,
   42,    0,    7,    0,    0,    0,    0,   42,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   42,   35,   36,   37,    0,   42,   44,   45,   46,
   47,    0,    0,    0,    0,    0,    0,   42,    0,    0,
   35,   36,   37,    0,    0,   44,   45,   46,   47,    0,
    0,   42,   42,    0,   42,   42,    0,    0,    0,   42,
    0,    0,    0,    0,    0,   42,   42,   42,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   35,   36,   37,    0,    0,   44,   45,   46,
   47,    0,    0,    0,    0,   35,   36,   37,    0,   42,
   44,   45,   46,   47,    0,    0,   35,   36,   37,   60,
    0,   44,   45,   46,   47,   35,   36,   37,    0,    0,
   44,   45,   46,   47,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,    0,   40,   40,   41,   42,   43,    0,   45,
   40,   47,   91,   59,   25,   59,   59,   46,   41,  257,
   13,   17,   40,   59,   60,  257,   62,  256,   41,  258,
   42,   24,  270,  271,   91,   47,   59,  275,  270,  271,
   43,  258,   45,  275,   44,   56,   59,  256,   91,   93,
    3,  260,   48,  257,  258,   45,   59,   91,   44,   59,
   53,  258,   91,   40,   41,   18,   43,  257,   45,   44,
   41,   64,   25,   59,   43,   41,   45,   44,   43,   43,
   45,   45,   59,   60,   59,   62,   40,   41,   45,   43,
   59,   45,   59,   86,   59,   40,   92,   93,   94,  258,
  259,   41,   43,   56,   45,   59,   60,   41,   62,   40,
   41,   93,   43,   66,   45,   45,   40,   41,  272,   60,
   93,   62,  267,  268,   83,   84,   85,   40,   59,   60,
  126,   62,  267,  268,  267,  268,   59,   96,   91,  267,
  268,   78,   79,   81,   82,   40,   93,   93,   59,   93,
   41,   59,   59,   59,   93,   41,  115,   59,   59,   41,
   41,   41,   59,   40,  268,   59,   41,   59,  268,   59,
   59,   59,  268,  268,   40,  268,   59,  268,  268,  258,
    0,  140,    9,  142,    7,  144,  145,   40,  147,   52,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  157,   -1,
   40,   41,   -1,   -1,   -1,  164,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  257,   40,   41,   -1,  257,   59,
   -1,  257,   -1,  260,   -1,  261,  262,  263,  264,  265,
  266,  267,  268,  269,   59,  271,  272,  273,  274,  257,
   40,   41,   -1,  261,  274,   -1,   40,   -1,  266,  267,
  268,  269,   -1,  271,  272,  273,  274,  257,   -1,  257,
   -1,   40,   -1,   40,   -1,  257,  256,  257,  258,  259,
  270,  271,  270,  271,   -1,  275,   -1,  275,  270,  271,
  257,   40,   40,  275,  261,  262,  263,  264,  265,  266,
  267,  268,  269,   -1,  271,  272,  273,  274,   -1,   40,
  257,  258,  259,  257,  261,   45,   -1,  261,  262,  263,
  264,  265,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,  262,  263,  264,  265,   -1,  257,  257,  258,  259,
  261,  262,  263,  264,  265,  266,  267,  268,  269,   -1,
  271,  272,  273,  274,  257,   40,   -1,   -1,  261,   -1,
   45,   -1,   -1,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,  257,   40,   -1,   60,  261,   62,   45,   -1,
   -1,  266,  267,  268,  269,   -1,  271,  272,  273,  274,
  257,   -1,   -1,   60,  261,   62,   -1,   -1,   -1,  266,
   -1,  257,  269,   -1,  271,  261,  273,  274,   -1,   -1,
  266,  267,  268,  269,  257,  271,   41,  273,  261,   -1,
   45,   -1,   -1,  266,  267,  268,  269,  257,  271,   41,
  273,  261,   -1,   45,   -1,   60,  266,   62,   -1,  269,
   -1,  271,  257,  273,   45,   -1,  261,   -1,   60,   -1,
   62,  266,   -1,   45,  269,   -1,  271,   -1,  273,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,  257,   60,   -1,
   62,  261,   -1,  257,   -1,   -1,  266,  261,   -1,  269,
   -1,  271,  266,  273,  268,  269,   -1,  271,  257,  273,
  257,   -1,  261,   -1,  261,   -1,   -1,  266,   -1,  266,
  269,   -1,  269,  272,  273,  272,  273,   -1,  257,  257,
   -1,   -1,  261,  261,   -1,   -1,   -1,  266,  266,   -1,
  269,  269,  271,   -1,  273,  273,  257,  257,  258,  259,
  261,  261,   -1,   -1,   -1,  266,   13,   14,  269,   -1,
   17,   -1,  273,   -1,   -1,   -1,   -1,   24,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   48,  257,  258,  259,   -1,   53,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,   -1,   -1,   64,   -1,   -1,
  257,  258,  259,   -1,   -1,  262,  263,  264,  265,   -1,
   -1,   78,   79,   -1,   81,   82,   -1,   -1,   -1,   86,
   -1,   -1,   -1,   -1,   -1,   92,   93,   94,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,  126,
  262,  263,  264,  265,   -1,   -1,  257,  258,  259,  260,
   -1,  262,  263,  264,  265,  257,  258,  259,   -1,   -1,
  262,  263,  264,  265,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=275;
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
"BEGIN","END","DO","UNTIL","FLOAT",
};
final static String yyrule[] = {
"$accept : programa",
"programa : conjunto_sentencias",
"conjunto_sentencias : sentencias_declarativas",
"conjunto_sentencias : sentencias_declarativas BEGIN sentencias_ejecutables END",
"conjunto_sentencias : BEGIN sentencias_ejecutables END",
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
"sentencia_ejecutable : selecion_ejecutable",
"sentencia_ejecutable : control_ejecutable",
"sentencia_ejecutable : salida_ejecutable",
"sentencia_ejecutable : asign",
"selecion_ejecutable : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' condicion ')' bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' condicion ')' bloque_sentencias bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' condicion ')' bloque_sentencias ELSE END_IF",
"selecion_ejecutable : IF '(' condicion ')' ELSE bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' condicion bloque_sentencias ELSE bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"selecion_ejecutable : IF condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"selecion_ejecutable : '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"selecion_ejecutable : '(' condicion ')' bloque_sentencias END_IF",
"selecion_ejecutable : IF condicion ')' bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' ')' bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' condicion bloque_sentencias END_IF",
"selecion_ejecutable : IF '(' condicion ')' END_IF",
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
"bloque_sentencias : BEGIN sentencias_ejecutables sentencia_ejecutable END",
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
"invocacion_metodo : ID '.' ID '(' ')'",
"invocacion_metodo : ID '.' ID ')'",
"invocacion_metodo : ID '.' '(' ')'",
};

//#line 212 "GramaticaFinal.y"




private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

private ArrayList<Token> listaVariables = new ArrayList<Token>();

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
		if(lexema == "32768"){
			if (tablaSimbolos.tablaSimbolos.get(lexema).getCantRef()==1){
				tablaSimbolos.tablaSimbolos.remove(lexema);
			}
			else
				tablaSimbolos.tablaSimbolos.get(lexema).decrementarRef();
		
		if(tablaSimbolos.tablaSimbolos.containsKey("32767")) {
			tablaSimbolos.tablaSimbolos.get("32767").incrementarRef();
		}
		else
			tablaSimbolos.agregar("32767", new Registro("int"));
	}
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.tablaSimbolos.containsKey(lexema)) {
		if (tablaSimbolos.tablaSimbolos.get(lexema).getCantRef()==1){
			tablaSimbolos.tablaSimbolos.remove(lexema);
		}
		else
			tablaSimbolos.tablaSimbolos.get(lexema).decrementarRef();
	}
	if(tablaSimbolos.tablaSimbolos.containsKey(lexemaNuevo)) {
		tablaSimbolos.tablaSimbolos.get(lexemaNuevo).incrementarRef();
	}
	else
		tablaSimbolos.agregar(lexemaNuevo, new Registro("int"));
}

public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if ((Float.parseFloat(lexemaNuevo)< -Float.MIN_NORMAL && Float.parseFloat(lexemaNuevo) > -Float.MAX_VALUE)) {
		if (tablaSimbolos.existeClave(lexema))
			if(tablaSimbolos.tablaSimbolos.get(lexema).getCantRef() == 1) 
				tablaSimbolos.tablaSimbolos.remove(lexema);
			else
				tablaSimbolos.tablaSimbolos.get(lexema).decrementarRef();
		if(tablaSimbolos.tablaSimbolos.containsKey(lexemaNuevo)) 
			tablaSimbolos.tablaSimbolos.get(lexemaNuevo).incrementarRef();
		else
			tablaSimbolos.agregar(lexemaNuevo, new Registro("float"));
	}
	
}

public void actualizarTablaVariables(Token tipo,String uso,Token cteE) {
	String lexema = "";
	Token t = null;
	
	while(!listaVariables.isEmpty()) {
		t = listaVariables.get(0);
		listaVariables.remove(0);
		lexema = t.getLexema();
		if(!tablaSimbolos.tablaSimbolos.get(lexema).getDeclarada()) {
			tablaSimbolos.tablaSimbolos.get(lexema).setTipo(tipo.getLexema());
			tablaSimbolos.tablaSimbolos.get(lexema).setDeclarada(true);
			tablaSimbolos.tablaSimbolos.get(lexema).setUso(uso);
			if(tablaSimbolos.tablaSimbolos.get(lexema).getUso().equals("Nombre de Coleccion")) {
				tablaSimbolos.tablaSimbolos.get(lexema).setTamanioColeccion(Integer.parseInt(cteE.getLexema()));
			}
		}
		else
			this.addError("Error en declaracion, variable o coleccion '" + lexema + "' redeclarada",t.getNroLinea());
	}
}

public boolean estaDeclarada(Token t){
	if (tablaSimbolos.tablaSimbolos.get(t.getLexema()).getDeclarada())
		return true;
	else
		return false;
}

public boolean esCompatible(Token t1, Token t2){
	if (tablaSimbolos.tablaSimbolos.get(t1.getLexema()).getTipo().equals(tablaSimbolos.tablaSimbolos.get(t2.getLexema()).getTipo()))
		return true;
	return false;
}
//#line 611 "Parser.java"
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
case 9:
//#line 60 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(2).obj).getNroLinea() + ": Sentencia declarativa");
														actualizarTablaVariables(((Token)val_peek(2).obj),"Variable",((Token)val_peek(2).obj));}
break;
case 10:
//#line 62 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia declarativa");
														listaVariables.add(((Token)val_peek(4).obj));
														actualizarTablaVariables(((Token)val_peek(5).obj),"Nombre de Coleccion",((Token)val_peek(2).obj));}
break;
case 11:
//#line 65 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 12:
//#line 66 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 13:
//#line 67 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 14:
//#line 68 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 15:
//#line 69 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta ']'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 16:
//#line 70 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta '['.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 17:
//#line 71 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta variables.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 18:
//#line 72 "GramaticaFinal.y"
{this.addError("Error en declaracion, falta identIFicador.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 19:
//#line 73 "GramaticaFinal.y"
{this.addError("Error en declaracion, la coleccion solo permite constantes enteras.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 22:
//#line 80 "GramaticaFinal.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 23:
//#line 81 "GramaticaFinal.y"
{listaVariables.add(((Token)val_peek(0).obj));}
break;
case 28:
//#line 90 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(7).obj).getNroLinea() + ": Sentencia IF-ELSE");}
break;
case 29:
//#line 91 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia IF");}
break;
case 30:
//#line 92 "GramaticaFinal.y"
{this.addError("Falta 'ELSE'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 31:
//#line 93 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 32:
//#line 94 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 33:
//#line 95 "GramaticaFinal.y"
{this.addError( "Falta ')'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 34:
//#line 96 "GramaticaFinal.y"
{this.addError("Falta condicion.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 35:
//#line 97 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 36:
//#line 98 "GramaticaFinal.y"
{this.addError("Falta 'IF'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 37:
//#line 99 "GramaticaFinal.y"
{this.addError("Falta 'IF'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 38:
//#line 100 "GramaticaFinal.y"
{this.addError("Falta '('.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 39:
//#line 101 "GramaticaFinal.y"
{this.addError("Falta condicion.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 40:
//#line 102 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 41:
//#line 103 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 43:
//#line 107 "GramaticaFinal.y"
{this.addError("Falta expresion del lado izquierdo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 44:
//#line 108 "GramaticaFinal.y"
{this.addError("Falta expresion del lado derecho.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 45:
//#line 111 "GramaticaFinal.y"
{if (!esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj)))
																this.addError("Error tipos incompatibles.",((Token)val_peek(0).obj).getNroLinea());
															else
																System.out.println("son compatibles ");}
break;
case 46:
//#line 115 "GramaticaFinal.y"
{if (!esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj)))
																this.addError("Error tipos incompatibles.",((Token)val_peek(0).obj).getNroLinea());
															else
																System.out.println("son compatibles ");}
break;
case 48:
//#line 122 "GramaticaFinal.y"
{if (!esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj)))
																this.addError("Error tipos incompatibles.",((Token)val_peek(0).obj).getNroLinea());
															else
																System.out.println("son compatibles ");}
break;
case 49:
//#line 126 "GramaticaFinal.y"
{if (!esCompatible(((Token)val_peek(2).obj),((Token)val_peek(0).obj)))
																this.addError("Error tipos incompatibles.",((Token)val_peek(0).obj).getNroLinea());
															else
																System.out.println("son compatibles ");}
break;
case 52:
//#line 134 "GramaticaFinal.y"
{actualizarTablaPositivo(((Token)val_peek(0).obj).getLexema());}
break;
case 54:
//#line 136 "GramaticaFinal.y"
{actualizarTablaNegativo(((Token)val_peek(0).obj).getLexema());}
break;
case 55:
//#line 137 "GramaticaFinal.y"
{actualizarTablaNegativoFloat(((Token)val_peek(0).obj).getLexema());}
break;
case 57:
//#line 141 "GramaticaFinal.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj)))
									this.addError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
								else
									if (tablaSimbolos.tablaSimbolos.get(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
										this.addError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",((Token)val_peek(3).obj).getNroLinea());
								if (!estaDeclarada(((Token)val_peek(1).obj)))
									this.addError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",((Token)val_peek(1).obj).getNroLinea());
								else
									if (tablaSimbolos.tablaSimbolos.get(((Token)val_peek(1).obj).getLexema()).getUso().equals("Nombre de Coleccion"))
										this.addError("Error '"+((Token)val_peek(1).obj).getLexema() + "' es una coleccion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 58:
//#line 152 "GramaticaFinal.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj)))
										this.addError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
									else
										if (tablaSimbolos.tablaSimbolos.get(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
											this.addError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 59:
//#line 157 "GramaticaFinal.y"
{	if (!estaDeclarada(((Token)val_peek(0).obj)))
						this.addError("Error variable '"+((Token)val_peek(0).obj).getLexema() + "' no declarada.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 68:
//#line 175 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(6).obj).getNroLinea() + ": Sentencia until");}
break;
case 69:
//#line 176 "GramaticaFinal.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 70:
//#line 177 "GramaticaFinal.y"
{this.addError("Falta 'until'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 71:
//#line 178 "GramaticaFinal.y"
{this.addError("Falta '('.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 72:
//#line 179 "GramaticaFinal.y"
{this.addError("Falta condicion.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 73:
//#line 180 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 74:
//#line 181 "GramaticaFinal.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 75:
//#line 184 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(4).obj).getNroLinea() + ": Sentencia PRINT");}
break;
case 76:
//#line 185 "GramaticaFinal.y"
{this.addError("Falta 'PRINT'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 77:
//#line 186 "GramaticaFinal.y"
{this.addError("Falta '('.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 78:
//#line 187 "GramaticaFinal.y"
{this.addError("Solo se puede definir una cadena.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 79:
//#line 188 "GramaticaFinal.y"
{this.addError("Falta ')'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 80:
//#line 189 "GramaticaFinal.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 81:
//#line 192 "GramaticaFinal.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Asignacion");
														  if (!estaDeclarada(((Token)val_peek(3).obj)))
															this.addError("Error variable '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
														  if (!esCompatible(((Token)val_peek(3).obj),((Token)val_peek(1).obj)))
																this.addError("Error tipos incompatibles.",((Token)val_peek(1).obj).getNroLinea());
															else
																System.out.println("son compatibles ");
														}
break;
case 82:
//#line 200 "GramaticaFinal.y"
{this.addError("Falta variable.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 83:
//#line 201 "GramaticaFinal.y"
{this.addError("Falta ':='.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 84:
//#line 202 "GramaticaFinal.y"
{this.addError("Falta expresion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 85:
//#line 203 "GramaticaFinal.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 87:
//#line 207 "GramaticaFinal.y"
{this.addError("Falta '(.'",((Token)val_peek(1).obj).getNroLinea());}
break;
case 88:
//#line 208 "GramaticaFinal.y"
{this.addError("Falta metodo.",((Token)val_peek(2).obj).getNroLinea());}
break;
//#line 1032 "Parser.java"
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
