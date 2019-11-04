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
    5,    5,    5,    5,    5,    5,    5,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,   12,   12,   12,   14,   14,   14,   16,   16,   16,
   17,   17,   17,   17,   17,   17,   18,   18,   18,   15,
   15,   15,   15,   15,   15,   13,   13,   13,    9,    9,
    9,    9,    9,    9,   20,   20,   10,   10,   10,   10,
   10,   11,   11,   11,   11,   11,   21,   21,   21,   19,
   19,   19,
};
final static short yylen[] = {                            2,
    1,    1,    4,    3,    2,    2,    1,    3,    3,    2,
    2,    2,    1,    2,    1,    3,    6,    5,    2,    5,
    2,    5,    5,    2,    5,    6,    1,    1,    3,    1,
    2,    2,    2,    1,    1,    1,    1,    8,    6,    7,
    7,    7,    7,    7,    7,    7,    5,    5,    5,    5,
    5,    3,    2,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    2,    2,    1,    4,    4,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    2,    6,    5,
    5,    5,    5,    5,    1,    1,    4,    3,    3,    3,
    3,    4,    3,    3,    3,    3,    1,    1,    1,    5,
    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   27,    0,    0,   28,    0,    0,
    1,    0,    0,   13,   15,    0,    0,    0,    0,    0,
   34,    0,    0,    0,   62,   63,    0,    0,    0,   60,
   61,   66,   73,   71,   74,   75,    0,   70,   72,    0,
    0,    0,   86,    0,   85,    0,    0,   11,    0,    0,
    0,   76,    0,    0,    0,    0,    0,    0,   12,    5,
   14,    0,   24,    0,    0,   19,    0,   31,   32,   33,
    0,    0,    0,    0,    0,    0,   64,   65,    0,    0,
   93,    0,    0,    0,    0,    0,    0,    0,   90,    0,
   89,    4,   78,    0,    0,    0,    0,    0,   88,    0,
    8,    0,    0,    0,   16,   29,   95,    0,   94,   67,
    0,    0,   97,   98,   99,    0,    0,    0,    0,   58,
   59,    0,    0,    0,    0,   54,    0,   87,   77,    0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,   92,
   18,   68,  102,    0,  101,    0,   49,    0,   51,    0,
    0,   50,    0,   48,   80,   83,    0,   82,   81,    0,
   47,   23,    0,   22,    0,   25,  100,    0,    0,    0,
   39,    0,    0,    0,   79,    0,   26,   17,   44,   42,
   41,    0,   40,   43,   45,   46,   38,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   14,   52,   16,   17,   18,   19,   20,
   21,   40,   53,   41,   42,   29,   30,   22,   32,   46,
  117,
};
final static short yysindex[] = {                       -16,
  -65,  131,  -30,  156,    0,  371,  302,    0,  555,    0,
    0,  291,  388,    0,    0,  -52,  -24,  -15,  -13,  -11,
    0,  -42, -189,  -37,    0,    0, -188,   29,  -14,    0,
    0,    0,    0,    0,    0,    0,  505,    0,    0,  -12,
  -41,  131,    0,  529,    0,   15,  -33,    0,  390,  408,
   24,    0,  -35,   41,    0,   49,  456,  410,    0,    0,
    0,  -85,    0, -192,  -19,    0, -164,    0,    0,    0,
    6,   30,   35,   43, -181,  -40,    0,    0,  131,  131,
    0,  131,  131,  427,  352,  427,   81,  -29,    0,   64,
    0,    0,    0,  433,  567,  483,  567,  427,    0,  446,
    0,   46, -222,   48,    0,    0,    0,   95,    0,    0,
   40,   52,    0,    0,    0,  101,   68,  -14,  -14,    0,
    0, -157,  320, -154, -152,    0,  -29,    0,    0,  114,
  537,  115,  116, -135,    0,   63,   65,  -51,  100,    0,
    0,    0,    0,  119,    0,  427,    0,  427,    0,  337,
  427,    0,  427,    0,    0,    0,  120,    0,    0,  427,
    0,    0,  103,    0,  105,    0,    0, -105, -103,  365,
    0, -100,  -94,  -93,    0,  -91,    0,    0,    0,    0,
    0,  -90,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  628,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  179,  180,    0,    0,    0,    0,  208,  226,  246,
    0,    0,    0,   38,    0,    0,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,    0,  181,    0,
    0,    0,    0,    0,  689,    0,    0,  182,    0,    0,
    0,    1,    0,    0,   12,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  130,    0,  172,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  183,
    0,    0,    0,    0,    0,    0,    0,  268,    0,    0,
  104,    0,    0,    0,    0,    0,    0,   84,  107,    0,
    0,    0,    0,    0,    0,    0,  151,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  190,    0,    0,    0,
    0,    0,    0,    0,   23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    5,  173,   37,    0,  168,    0,    0,    0,
    0,   22,  755,   25,  145,   55,    9,  801,    0,   13,
    0,
};
final static int YYTABLESIZE=954;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        116,
   30,   79,   27,   80,   97,  103,   63,  164,   76,   37,
   49,   21,   45,   79,   27,   80,   58,   55,   38,   67,
   39,   56,   20,    9,   67,   23,   28,   82,   86,   38,
   54,   39,   83,  137,   66,  138,   15,   69,   64,  105,
   30,  165,   15,   68,   30,   69,   72,   70,   15,   61,
   27,   21,   45,   75,   94,   91,   90,   75,   85,   30,
   57,  100,   20,   95,  107,  104,   88,   73,   74,   77,
   78,   79,   79,   80,   80,   73,  112,   69,   69,   69,
   69,   98,   69,   55,   69,   61,   15,   81,  109,   99,
  120,  121,  106,   15,   61,  108,   69,   69,  141,   69,
   57,   57,   69,   57,  128,   57,   56,  144,  145,  146,
  147,  127,  151,  152,  153,  154,  130,  132,  133,   57,
   57,  162,   57,   55,   55,   27,   55,  110,   55,   53,
   61,  160,  161,  118,  119,  111,   61,   79,  136,   80,
  139,  143,   55,   55,  142,   55,   56,   56,   68,   56,
   52,   56,  157,  140,  155,  158,  159,  163,  166,  167,
  175,  177,  179,  178,  180,   56,   56,  183,   56,   53,
   53,   91,  102,  184,  185,   27,  186,  187,    2,    7,
    6,   10,    9,   65,   59,   87,    0,    0,   53,   84,
   52,   52,    0,    0,    0,   44,    0,    0,    0,    0,
   27,    0,    0,    0,   62,    0,    0,   35,    0,   52,
    0,   91,    0,    0,   24,   25,   26,    0,   71,    0,
   33,   34,   35,   36,    0,   36,   24,   25,   26,   84,
   91,   33,   34,   35,   36,  113,  114,  115,   96,    0,
    1,    0,    0,    0,    2,   37,    0,   35,   84,    3,
    0,    0,    4,    5,    6,    0,    7,   30,    8,    0,
    0,   30,   24,   25,   26,   36,   30,   96,   21,   30,
   30,   30,   21,   30,    0,   30,    0,   21,    0,   20,
   21,   21,   21,   20,   21,   37,   21,    0,   20,    0,
    0,   20,   20,   20,   69,   20,    0,   20,   69,   69,
   69,   69,   69,   69,   69,   69,   69,   96,   69,   69,
   69,   69,    0,    0,   69,   69,   69,   57,   69,    0,
    0,   57,   57,   57,   57,   57,   57,   57,   57,   57,
    9,   57,   57,   57,   57,    0,  126,   24,   25,   26,
   55,    9,    0,    0,   55,   55,   55,   55,   55,   55,
   55,   55,   55,    0,   55,   55,   55,   55,    0,    9,
   68,   68,   68,   56,   68,    0,    0,   56,   56,   56,
   56,   56,   56,   56,   56,   56,    9,   56,   56,   56,
   56,    0,    0,    0,    0,    0,   53,   24,   25,   26,
   53,    9,  123,    0,    0,   53,   53,   53,   53,    0,
   53,   53,   53,   53,    9,    0,    0,   52,    0,    0,
    9,   52,   24,   25,   26,   43,   52,   52,   52,   52,
    0,   52,   52,   52,   52,    0,    0,    9,   91,    9,
    0,    0,   91,    0,    0,    0,    0,   91,   91,   91,
   91,    0,   91,   91,   91,   91,   84,    9,    0,    9,
   84,    0,    0,    0,    0,   84,   84,   84,   84,    0,
   84,   84,   84,   84,   35,    0,    9,    0,   35,    0,
    0,    0,    9,   35,   35,   35,   35,    0,   35,   35,
   35,   35,   36,    0,    0,    9,   36,    0,    0,    0,
    0,   36,   36,   36,   36,    9,   36,   36,   36,   36,
    0,    0,   37,    0,    0,    0,   37,    0,    0,    0,
    0,   37,   37,   37,   37,    0,   37,   37,   37,   37,
    0,    0,  131,    0,   96,    0,    0,   27,   96,    0,
    0,    0,    0,   96,   96,   96,   96,    0,   96,   96,
   96,   96,   38,    0,   39,   84,    0,    1,    0,   27,
    0,    2,    0,    0,    0,    0,    3,    0,   47,    4,
    5,   57,    2,    7,   38,    8,   39,    3,    0,   89,
    4,    0,   50,   27,    7,   51,   47,  156,    0,    0,
    2,   27,    0,    0,    0,    3,  148,  149,    4,    0,
   50,    0,    7,   47,    0,    0,   38,    2,   39,   27,
    0,    0,    3,  170,  171,    4,    0,   50,   47,    7,
    0,   27,    2,    0,   38,    0,   39,    3,    0,    0,
    4,   47,   50,    0,    7,    2,   38,   47,   39,    0,
    3,    2,  181,    4,    0,   50,    3,    7,    0,    4,
    0,    0,   48,    7,   47,    0,   47,    0,    2,    0,
    2,    0,    0,    3,    0,    3,    4,    0,    4,   60,
    7,   92,    7,    0,   47,    0,   47,    0,    2,    0,
    2,   30,   69,    3,    0,    3,    4,    0,    4,   93,
    7,  101,    7,   47,    0,    0,   30,    2,    0,   47,
    0,    0,    3,    2,    0,    4,    0,   50,    3,    7,
    0,    4,   47,    0,  129,    7,    2,    0,    0,    0,
    0,    3,   47,    0,    4,    0,    2,  135,    7,    0,
    0,    3,    0,    0,    4,    0,    0,    0,    7,   85,
   60,   60,    0,   60,    0,   60,    0,    0,    0,   24,
   25,   26,    0,    0,   33,   34,   35,   36,   60,    0,
   60,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   24,   25,   26,    0,    0,   33,   34,   35,   36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   24,   25,   26,   43,    0,
    0,    0,    0,   24,   25,   26,    0,    0,   33,   34,
   35,   36,   31,   31,   31,    0,    0,    0,    0,   31,
    0,   24,   25,   26,   43,    0,   33,   34,   35,   36,
    0,    0,   31,   24,   25,   26,    0,    0,   33,   34,
   35,   36,    0,    0,    0,    0,    0,   31,  122,  124,
  125,    0,   31,    0,   31,    0,    0,    0,    0,    0,
    0,    0,  134,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,  150,    0,   31,
   31,    0,   31,   31,   69,   69,   69,   31,   69,    0,
    0,    0,    0,    0,    0,   31,   31,   31,    0,    0,
  168,    0,  169,    0,  172,  173,    0,  174,    0,    0,
    0,    0,    0,    0,  176,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  182,    0,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,   60,   60,   60,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   45,   45,   40,   91,   59,   59,   46,   40,
    6,    0,    4,   43,   45,   45,   12,    9,   60,   44,
   62,    9,    0,   40,   44,   91,    2,   42,   41,   60,
    9,   62,   47,  256,   59,  258,    0,    0,   91,   59,
   40,   93,    6,   59,   44,   59,   22,   59,   12,   13,
   45,   40,   44,   91,   50,   41,   44,   91,   37,   59,
    0,   57,   40,   40,   59,  258,   42,  257,  258,  258,
  259,   43,   43,   45,   45,  257,  258,   40,   41,   42,
   43,   41,   45,    0,   47,   49,   50,   59,   59,   41,
   82,   83,  257,   57,   58,   71,   59,   60,   59,   62,
   40,   41,   45,   43,   41,   45,    0,   40,   41,  267,
  268,   87,  267,  268,  267,  268,   95,   96,   97,   59,
   60,   59,   62,   40,   41,   45,   43,   93,   45,    0,
   94,  267,  268,   79,   80,   93,  100,   43,   93,   45,
   93,   41,   59,   60,   93,   62,   40,   41,   45,   43,
    0,   45,  131,   59,   41,   41,   41,   93,   59,   41,
   41,   59,  268,   59,  268,   59,   60,  268,   62,   40,
   41,    0,  258,  268,  268,   45,  268,  268,    0,    0,
    0,    0,    0,   16,   12,   41,   -1,   -1,   59,    0,
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
  271,  272,  273,  274,   40,   -1,   -1,  257,   -1,   -1,
   40,  261,  257,  258,  259,  260,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,   -1,   -1,   40,  257,   40,
   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,  267,  268,
  269,   -1,  271,  272,  273,  274,  257,   40,   -1,   40,
  261,   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,
  271,  272,  273,  274,  257,   -1,   40,   -1,  261,   -1,
   -1,   -1,   40,  266,  267,  268,  269,   -1,  271,  272,
  273,  274,  257,   -1,   -1,   40,  261,   -1,   -1,   -1,
   -1,  266,  267,  268,  269,   40,  271,  272,  273,  274,
   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,   -1,   -1,
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
  269,  257,  271,   -1,  273,  261,   60,  257,   62,   -1,
  266,  261,  268,  269,   -1,  271,  266,  273,   -1,  269,
   -1,   -1,  272,  273,  257,   -1,  257,   -1,  261,   -1,
  261,   -1,   -1,  266,   -1,  266,  269,   -1,  269,  272,
  273,  272,  273,   -1,  257,   -1,  257,   -1,  261,   -1,
  261,   44,   45,  266,   -1,  266,  269,   -1,  269,  272,
  273,  272,  273,  257,   -1,   -1,   59,  261,   -1,  257,
   -1,   -1,  266,  261,   -1,  269,   -1,  271,  266,  273,
   -1,  269,  257,   -1,  272,  273,  261,   -1,   -1,   -1,
   -1,  266,  257,   -1,  269,   -1,  261,  272,  273,   -1,
   -1,  266,   -1,   -1,  269,   -1,   -1,   -1,  273,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,  257,
  258,  259,   -1,   -1,  262,  263,  264,  265,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,   -1,
   -1,   -1,   -1,  257,  258,  259,   -1,   -1,  262,  263,
  264,  265,    2,    3,    4,   -1,   -1,   -1,   -1,    9,
   -1,  257,  258,  259,  260,   -1,  262,  263,  264,  265,
   -1,   -1,   22,  257,  258,  259,   -1,   -1,  262,  263,
  264,  265,   -1,   -1,   -1,   -1,   -1,   37,   84,   85,
   86,   -1,   42,   -1,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   98,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   71,   -1,   -1,   -1,   -1,   -1,  123,   -1,   79,
   80,   -1,   82,   83,  257,  258,  259,   87,  261,   -1,
   -1,   -1,   -1,   -1,   -1,   95,   96,   97,   -1,   -1,
  146,   -1,  148,   -1,  150,  151,   -1,  153,   -1,   -1,
   -1,   -1,   -1,   -1,  160,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  170,   -1,   -1,   -1,   -1,   -1,
   -1,  131,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  262,  263,  264,  265,
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
"control_ejecutable : DO bloque_sentencias UNTIL '(' condicion ')'",
"control_ejecutable : DO UNTIL '(' condicion ')'",
"control_ejecutable : DO bloque_sentencias '(' condicion ')'",
"control_ejecutable : DO bloque_sentencias UNTIL condicion ')'",
"control_ejecutable : DO bloque_sentencias UNTIL '(' ')'",
"control_ejecutable : DO bloque_sentencias UNTIL '(' condicion",
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

//#line 245 "GramaticaCorreciones.y"




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
		if(lexema.equals("32768")){
			if (tablaSimbolos.getClave(lexema).getCantRef()==1)
				tablaSimbolos.eliminarClave(lexema);
			else
				tablaSimbolos.getClave(lexema).decrementarRef();
		
		if(tablaSimbolos.existeClave("32767")) 
			tablaSimbolos.getClave("32767").incrementarRef();
		else
			tablaSimbolos.agregar("32767", new Registro("int"));
	}
}

public void actualizarTablaNegativo(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if(tablaSimbolos.existeClave(lexema)) {
		if (tablaSimbolos.getClave(lexema).getCantRef()==1)
			tablaSimbolos.eliminarClave(lexema);
		else
			tablaSimbolos.getClave(lexema).decrementarRef();
	}
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else
		tablaSimbolos.agregar(lexemaNuevo, new Registro("int"));
}


public void actualizarTablaNegativoFloat(String lexema) {
	String lexemaNuevo = "-" + lexema;
	if (tablaSimbolos.existeClave(lexema))
		if(tablaSimbolos.getClave(lexema).getCantRef() == 1) 
			tablaSimbolos.eliminarClave(lexema);
		else
			tablaSimbolos.getClave(lexema).decrementarRef();
	if(tablaSimbolos.existeClave(lexemaNuevo)) 
		tablaSimbolos.getClave(lexemaNuevo).incrementarRef();
	else
		tablaSimbolos.agregar(lexemaNuevo, new Registro("float"));

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
	if (tablaSimbolos.getClave(t1.getLexema()).getTipo().equals(tablaSimbolos.getClave(t2.getLexema()).getTipo()))
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
//#line 697 "Parser.java"
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
//#line 103 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 36:
//#line 104 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 37:
//#line 105 "GramaticaCorreciones.y"
{this.addError("Error, falta ';'",((Token)val_peek(0).obj).getNroLinea());}
break;
case 38:
//#line 108 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(7).obj).getNroLinea() + ": Sentencia IF-ELSE");}
break;
case 39:
//#line 109 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia IF");}
break;
case 40:
//#line 110 "GramaticaCorreciones.y"
{this.addError("Falta 'ELSE'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 41:
//#line 111 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 42:
//#line 112 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 43:
//#line 113 "GramaticaCorreciones.y"
{this.addError( "Falta ')'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 44:
//#line 114 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 45:
//#line 115 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 46:
//#line 116 "GramaticaCorreciones.y"
{this.addError("Falta 'IF'.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 47:
//#line 117 "GramaticaCorreciones.y"
{this.addError("Falta 'IF'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 48:
//#line 118 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 49:
//#line 119 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 50:
//#line 120 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 51:
//#line 121 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 52:
//#line 126 "GramaticaCorreciones.y"
{if (!sonCompatibles(tablaSimbolos.getClave(((Token)val_peek(2).obj).getLexema()).getTipo()))	
																this.addError("Error tipos incompatibles en comparacion.",((Token)val_peek(0).obj).getNroLinea());
															listaCompatibilidad.clear();}
break;
case 53:
//#line 129 "GramaticaCorreciones.y"
{this.addError("Falta expresion del lado izquierdo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 54:
//#line 130 "GramaticaCorreciones.y"
{this.addError("Falta expresion del lado derecho.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 60:
//#line 140 "GramaticaCorreciones.y"
{listaCompatibilidad.add(tablaSimbolos.getClave(((Token)val_peek(0).obj).getLexema()).getTipo());}
break;
case 61:
//#line 143 "GramaticaCorreciones.y"
{}
break;
case 62:
//#line 144 "GramaticaCorreciones.y"
{actualizarTablaPositivo(((Token)val_peek(0).obj).getLexema());}
break;
case 64:
//#line 146 "GramaticaCorreciones.y"
{actualizarTablaNegativo(((Token)val_peek(0).obj).getLexema());}
break;
case 65:
//#line 147 "GramaticaCorreciones.y"
{actualizarTablaNegativoFloat(((Token)val_peek(0).obj).getLexema());}
break;
case 67:
//#line 151 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj))){
									this.addError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
									tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
								}
								else
									if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
										this.addError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",((Token)val_peek(3).obj).getNroLinea());
								if (!estaDeclarada(((Token)val_peek(1).obj))){
									this.addError("Error variable '"+((Token)val_peek(1).obj).getLexema() + "' no declarada.",((Token)val_peek(1).obj).getNroLinea());
									tablaSimbolos.eliminarClave(((Token)val_peek(1).obj).getLexema());
								}
									
								else
									if (!tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getTipo().equals("int"))
										this.addError("Error El tipo del subindice no es entero",((Token)val_peek(1).obj).getNroLinea());
								else
									if (tablaSimbolos.getClave(((Token)val_peek(1).obj).getLexema()).getUso().equals("Nombre de Coleccion"))
										this.addError("Error '"+((Token)val_peek(1).obj).getLexema() + "' es una coleccion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 68:
//#line 170 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(3).obj))){
										this.addError("Error coleccion '"+((Token)val_peek(3).obj).getLexema() + "' no declarada.",((Token)val_peek(3).obj).getNroLinea());
										tablaSimbolos.eliminarClave(((Token)val_peek(3).obj).getLexema());
									}
									else
										if (tablaSimbolos.getClave(((Token)val_peek(3).obj).getLexema()).getUso().equals("Variable"))
											this.addError("Error '"+((Token)val_peek(3).obj).getLexema() + "' es una variable.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 69:
//#line 177 "GramaticaCorreciones.y"
{	if (!estaDeclarada(((Token)val_peek(0).obj))){
						this.addError("Error variable '"+((Token)val_peek(0).obj).getLexema() + "' no declarada.",((Token)val_peek(0).obj).getNroLinea());
						tablaSimbolos.eliminarClave(((Token)val_peek(0).obj).getLexema());}
				}
break;
case 79:
//#line 199 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia until");}
break;
case 80:
//#line 200 "GramaticaCorreciones.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 81:
//#line 201 "GramaticaCorreciones.y"
{this.addError("Falta 'until'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 82:
//#line 202 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 83:
//#line 203 "GramaticaCorreciones.y"
{this.addError("Falta condicion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 84:
//#line 204 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 87:
//#line 212 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Sentencia PRINT");}
break;
case 88:
//#line 213 "GramaticaCorreciones.y"
{this.addError("Falta 'PRINT'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 89:
//#line 214 "GramaticaCorreciones.y"
{this.addError("Falta '('.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 90:
//#line 215 "GramaticaCorreciones.y"
{this.addError("Falta cadena .",((Token)val_peek(1).obj).getNroLinea());}
break;
case 91:
//#line 216 "GramaticaCorreciones.y"
{this.addError("Falta ')'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 92:
//#line 220 "GramaticaCorreciones.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Asignacion");
															
															if (!sonCompatibles(tablaSimbolos.getClave(((Token)val_peek(3).obj) .getLexema()).getTipo()))	
																this.addError("Error tipos incompatibles.",((Token)val_peek(1).obj).getNroLinea());
																
															listaCompatibilidad.clear();	
														}
break;
case 93:
//#line 227 "GramaticaCorreciones.y"
{this.addError("Falta variable.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 94:
//#line 228 "GramaticaCorreciones.y"
{this.addError("Falta ':='.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 95:
//#line 229 "GramaticaCorreciones.y"
{this.addError("Falta expresion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 96:
//#line 230 "GramaticaCorreciones.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 101:
//#line 239 "GramaticaCorreciones.y"
{this.addError("Falta '(.'",((Token)val_peek(1).obj).getNroLinea());}
break;
case 102:
//#line 240 "GramaticaCorreciones.y"
{this.addError("Falta metodo.",((Token)val_peek(2).obj).getNroLinea());}
break;
//#line 1147 "Parser.java"
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
