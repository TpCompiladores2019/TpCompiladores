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






//#line 2 "Gramatica1.y"
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
import java.util.List;

import Lexico.Error;
//#line 25 "Parser.java"




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
public final static short end_if=268;
public final static short print=269;
public final static short INT=270;
public final static short begin=271;
public final static short end=272;
public final static short DO=273;
public final static short until=274;
public final static short FLOAT=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    3,    3,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    6,
    6,    7,    7,    5,    5,    5,    5,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,   12,   12,   12,   12,   14,
   14,   14,   16,   16,   16,   17,   17,   17,   17,   17,
   17,   18,   18,   18,   15,   15,   15,   15,   15,   15,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,    9,    9,    9,    9,    9,    9,    9,    9,
   10,   10,   10,   10,   10,   10,   11,   11,   11,   11,
   11,   19,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    1,    1,    4,    3,    2,    1,    2,    1,    3,    6,
    5,    2,    5,    2,    5,    5,    2,    5,    5,    1,
    1,    3,    1,    1,    1,    1,    1,    9,    7,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    6,    6,
    6,    6,    6,    6,    6,    3,    2,    2,    2,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    2,    2,
    1,    4,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    4,    3,    3,    3,    3,    3,    3,
    3,    3,    7,    6,    6,    6,    6,    6,    6,    6,
    5,    4,    4,    5,    4,    4,    4,    3,    3,    3,
    3,    5,    2,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   20,    0,   21,    0,    1,    0,    6,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   71,
    0,   24,   25,   26,   27,    0,    0,    0,    0,    5,
    0,   17,    0,    0,   12,    0,    0,    0,    0,   57,
   58,    0,    0,    0,   55,   56,   61,   68,   66,   69,
   70,    0,   65,   67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   72,    0,    0,    0,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    9,   22,
    0,    0,    0,    0,    0,   59,   60,    0,    0,   98,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   80,   76,   77,    0,    0,    0,    0,    0,
    0,    0,    0,  100,    0,   99,   79,   75,    3,    0,
    0,    0,    0,   11,   62,    0,    0,    0,    0,    0,
   53,   54,    0,    0,    0,    0,    0,    0,    0,   93,
    0,   95,    0,   74,   73,    0,    0,    0,    0,   92,
    0,    0,   97,   16,   15,    0,   19,   18,   63,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   94,
   91,    0,    0,    0,    0,    0,    0,    0,    0,   10,
  102,    0,   41,    0,   43,    0,    0,   44,    0,    0,
   42,    0,   40,   85,   88,   89,    0,   87,   86,    0,
   39,   84,    0,    0,    0,    0,   29,    0,    0,    0,
   83,    0,   36,   34,   33,    0,   31,   32,   35,   37,
   38,   28,
};
final static short yydgoto[] = {                          5,
    6,    7,   19,   20,   64,    9,   10,   22,   23,   24,
   25,   55,   26,   56,   57,   44,   45,   27,   47,   28,
};
final static short yysindex[] = {                      -206,
  -76,    0,  630,    0,    0,    0, -194,    0,  -48,   13,
 -230,  -53,   41,  661,  -21,  630,  442,  750,  461,    0,
    0,    0,    0,    0,    0, -227,  -43,  630,  630,    0,
  -82,    0, -202,   54,    0, -191,  -18, -103,  -32,    0,
    0,  -63,    3,   32,    0,    0,    0,    0,    0,    0,
    0,  702,    0,    0,   43,  738,   41,   62, -164, -155,
 -142,  480,   68,    0,  -33,   94,   97,    0,    0,  122,
   46,   28, -106,  -95,  499,   91,  -80,   93,    0,    0,
  133,  104,  112,  -57,  -51,    0,    0,   41,   41,    0,
   41,   41,  630,  518,  630,  102,  -16,   41,  -16,  145,
  166,  -23,    0,    0,    0,  -64,  -62,  783,  680,  783,
  152,  630,  783,    0,   50,    0,    0,    0,    0,  153,
  -59,  158,  160,    0,    0,  133,  147,  204,   32,   32,
    0,    0, -135,  318, -131, -115,    0,    0,  -16,    0,
  186,    0,  187,    0,    0,  209,  716,  216,  225,    0,
  -85,  236,    0,    0,    0,  221,    0,    0,    0,  240,
  630,  227,  630,  260,  139,  630,  277,  630,  279,    0,
    0,  283,  306,  -15,  323,  324,  630,  325,  329,    0,
    0, -148,    0, -140,    0,  533,  336,    0, -117,  -94,
    0,  -83,    0,    0,    0,    0,  345,    0,    0,  -75,
    0,    0,  357,  358,  359,   11,    0,  360,  367,  373,
    0,  374,    0,    0,    0,  375,    0,    0,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
   72,    0,    0,    0,    0,    0,  284,    0,    0,    0,
    0,  386,    0,    0,    0,    0,    0,    0,    0,    0,
  341,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,   21,    0,    0,    0,    0,  -37,    0,
    0,    0,    0,   59,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  550,
  561,    0,    0,    0,    0,    0,    0,    0,  362,    0,
    0,    0,  580,  599,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  396,  615,  407,    0,
    0,    0,    0,    0,    0,  154,  183,    0,    0,    0,
    0,    0,    0,    0,  203,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  292,    0,    0,   82,  105,
    0,    0,    0,    0,    0,    0,  -10,  128,  427,    0,
    0,    0,  224,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   40,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  243,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  262,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  295,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  406,   78,   39,    0,  429,    0,    0,    0,
    0,    2,   -5,   26,  383,  -72,  111,  972,    0,  425,
};
final static int YYTABLESIZE=1119;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        155,
   23,   42,   64,   64,   64,   64,  110,   64,   77,   64,
   32,   65,  122,   85,   11,  129,  130,  143,   59,   67,
   14,   64,   64,  130,   64,  197,   88,   37,   89,   59,
   59,   57,   57,  156,   57,  142,   57,   38,   43,   13,
   23,   21,   33,  196,   23,   88,   70,   89,   59,   57,
    1,   57,   72,   94,   61,   78,   36,   69,   84,   23,
   14,   90,    1,    2,    3,   80,   74,   21,    4,  217,
   88,   35,   89,   91,   81,    2,   29,    8,   92,   13,
    4,   97,   99,   95,   30,   42,  116,  133,  135,  136,
   42,  101,   88,   60,   89,  102,  115,   36,   52,   52,
  107,   52,  100,   52,  114,   73,  151,  108,  153,  146,
  148,  149,   79,   69,  152,   23,  103,   52,   52,  203,
   52,   50,   50,  139,   50,   70,   50,  204,  165,  104,
   23,  161,  162,   70,  111,  166,  167,  112,   70,  106,
   50,   50,   70,   50,   51,   51,   42,   51,  174,   51,
  208,  168,  169,   82,   83,  182,   70,  184,   70,  189,
  190,  113,  192,   51,   51,  117,   51,   60,   60,   58,
   58,  200,   58,  209,   58,   76,  118,  121,   18,   70,
  206,  177,  178,  120,  210,  123,   60,   58,   70,   58,
   70,  124,  212,   82,   86,   87,  125,  188,   70,   82,
  127,  131,  132,  140,  126,  128,  141,  144,   31,  145,
  150,  154,   82,   39,   40,   41,  157,   71,  158,   64,
   64,   64,   78,   64,   64,   64,   64,   64,   64,   64,
   64,   64,   64,   64,   64,   64,   64,   64,   58,  159,
  109,   78,  101,  160,  170,  171,   57,   57,   57,  172,
   59,   57,   57,   57,   57,   59,  175,   23,   59,   59,
   59,   23,   59,   96,   59,  176,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,  179,   14,  216,  180,
  181,   14,   45,    2,   70,  183,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   13,   39,   40,   41,
   13,   90,   39,   40,   41,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   52,   52,   52,  185,   52,
   52,   52,   52,   52,   52,   52,   52,   52,   52,   52,
   52,   52,   52,   52,   30,  191,   63,  193,   50,   50,
   50,  194,   50,   50,   50,   50,   50,   50,   50,   50,
   50,   50,   50,   50,   50,   50,   50,   18,   39,  137,
  138,   51,   51,   51,  195,   51,   51,   51,   51,   51,
   51,   51,   51,   51,   51,   51,   51,   51,   51,   51,
    8,  198,  199,  201,   58,   58,   58,  202,   60,   58,
   58,   58,   58,   60,  207,   12,   60,   60,   60,   13,
   60,    7,   60,  211,   14,  186,  187,   15,    2,   16,
   82,   17,   70,    4,   82,  213,  214,  215,  218,   82,
   82,   82,   82,   82,   82,  219,   82,   71,   82,   23,
   64,  220,  221,  222,   75,   48,   48,   34,   98,   78,
   62,    0,    0,   78,   23,    0,   47,   47,   78,   78,
   78,   78,   78,   78,   48,   78,   72,   78,    0,  101,
    0,    0,    0,  101,    0,   47,   46,   46,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,    0,    0,
   96,   18,    0,    0,   96,   46,    0,    0,    0,   96,
   96,   96,   96,   96,   96,   96,   96,   96,   96,   45,
   18,    0,    0,   45,    0,    0,    0,    0,   45,   45,
   45,   45,   45,   45,   45,   45,   45,   45,   90,   18,
    0,    0,   90,    0,    0,    0,    0,   90,   90,   90,
   90,   90,   90,   90,   90,   90,   90,    0,   18,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   63,   63,
   63,   30,   63,    0,    0,   30,    0,   18,  134,    0,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
    0,    0,   18,    0,   12,    0,    0,    0,   13,    0,
    0,    0,    0,   14,  163,  164,   15,    2,   16,  105,
   17,    0,    4,    0,    0,    0,    0,    8,    0,    0,
  106,    8,    0,    0,    0,    0,    8,    0,    0,    8,
    8,    8,    8,    8,   72,    8,    0,    0,    7,  103,
    0,    0,    7,    0,    0,    0,    0,    7,    0,    0,
    7,    7,    7,    7,    7,   72,    7,    0,  104,    0,
    0,    0,   64,   64,   64,    0,   64,    0,    0,    0,
    0,    0,   48,    0,   49,   49,   48,    0,    0,    0,
    0,   48,    0,   47,   48,   48,   48,   47,   48,   18,
   48,    0,   47,   49,    0,   47,   47,   47,    0,   47,
    0,   47,    0,   46,    0,    0,    0,   46,    0,    0,
    0,    0,   46,    0,    0,   46,   46,   46,   12,   46,
   52,   46,   13,    0,    0,   42,    0,   14,    0,    0,
   15,    2,   16,    0,   17,   63,    4,   12,    0,  147,
   53,   13,   54,    0,   42,    0,   14,    0,    0,   15,
    2,   16,   68,   17,    0,    4,   12,    0,    0,   53,
   13,   54,   93,    0,    0,   14,   42,    0,   15,    2,
   16,  105,   17,    0,    4,   12,  173,    0,    0,   13,
   42,   53,    0,   54,   14,    0,    0,   15,    2,   16,
  119,   17,    0,    4,   12,   53,    0,   54,   13,    0,
   88,    0,   96,   14,    0,    0,   15,    2,   16,   12,
   17,    0,    4,   13,   42,    0,    0,   53,   14,   54,
  205,   15,    2,   16,    0,   17,  105,    4,    0,   53,
  105,   54,    0,    0,    0,  105,    0,  106,  105,  105,
  105,  106,  105,   71,  105,    0,  106,   42,    0,  106,
  106,  106,    0,  106,   72,  106,  103,    0,    0,    0,
  103,    0,   53,    0,   54,  103,    0,    0,  103,  103,
  103,    0,  103,   71,  103,  104,    0,    0,    0,  104,
    0,    0,    0,    0,  104,    0,    0,  104,  104,  104,
    0,  104,   72,  104,    0,   49,    0,    0,    0,    0,
   49,    0,    0,   49,   49,   49,   12,   49,    0,   49,
   13,    0,    0,    0,    0,   14,    0,    0,   15,    2,
   16,    0,   17,    0,    4,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,   40,   41,
    0,    0,   48,   49,   50,   51,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,   40,   41,    0,
    0,   48,   49,   50,   51,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,   40,
   41,    0,    0,   48,   49,   50,   51,    0,    0,    0,
    0,    0,   39,   40,   41,    0,    0,   48,   49,   50,
   51,    0,    0,    0,   46,   46,    0,    0,    0,   46,
    0,    0,    0,    0,   39,   40,   41,    0,   46,   48,
   49,   50,   51,    0,    0,    0,   39,   40,   41,   66,
    0,   48,   49,   50,   51,    0,    0,    0,    0,    0,
    0,    0,    0,   46,    0,    0,    0,   46,   46,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   39,
   40,   41,   46,    0,   48,   49,   50,   51,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
   46,    0,   46,   46,    0,    0,    0,   46,    0,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
   46,   46,    0,    0,   46,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   46,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   45,   40,   41,   42,   43,   40,   45,   91,   47,
   59,   17,   93,   46,   91,   88,   89,   41,   40,   18,
    0,   59,   60,   96,   62,   41,   43,  258,   45,   40,
   41,   42,   43,   93,   45,   59,   47,   91,   13,    0,
   40,    3,   91,   59,   44,   43,  274,   45,   59,   60,
  257,   62,   27,   52,   16,  258,   44,   19,   91,   59,
   40,   59,  257,  270,  271,  257,   28,   29,  275,   59,
   43,   59,   45,   42,   93,  270,  271,    0,   47,   40,
  275,   56,   57,   41,    7,   45,   59,   93,   94,   95,
   45,  256,   43,   16,   45,  260,   71,   44,   40,   41,
   62,   43,   41,   45,   59,   28,  112,   40,   59,  108,
  109,  110,   59,   75,  113,   44,  272,   59,   60,  268,
   62,   40,   41,   98,   43,  274,   45,  268,  134,  272,
   59,  267,  268,  274,   41,  267,  268,   41,  274,   62,
   59,   60,  274,   62,   40,   41,   45,   43,  147,   45,
  268,  267,  268,  257,  258,  161,  274,  163,  274,  165,
  166,   40,  168,   59,   60,  272,   62,   40,   41,   42,
   43,  177,   45,  268,   47,  258,  272,  258,   40,  274,
  186,  267,  268,   93,  268,   93,   59,   60,  274,   62,
  274,   59,  268,   40,  258,  259,   93,   59,  274,  257,
  258,   91,   92,   59,   93,  257,   41,  272,  257,  272,
   59,   59,   59,  257,  258,  259,   59,  261,   59,  257,
  258,  259,   40,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,  274,  275,  260,   93,
  274,   59,   40,   40,   59,   59,  257,  258,  259,   41,
  261,  262,  263,  264,  265,  266,   41,  257,  269,  270,
  271,  261,  273,   40,  275,   41,  266,  267,  268,  269,
  270,  271,  272,  273,  274,  275,   41,  257,  268,   59,
   41,  261,   40,    0,  274,   59,  266,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  257,  257,  258,  259,
  261,   40,  257,  258,  259,  266,  267,  268,  269,  270,
  271,  272,  273,  274,  275,  257,  258,  259,   59,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,  274,  275,   40,   59,   45,   59,  257,  258,
  259,   59,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  272,  273,  274,  275,   40,  257,  258,
  259,  257,  258,  259,   59,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  273,  274,  275,
   40,   59,   59,   59,  257,  258,  259,   59,  261,  262,
  263,  264,  265,  266,   59,  257,  269,  270,  271,  261,
  273,   40,  275,   59,  266,  267,  268,  269,  270,  271,
  257,  273,  274,  275,  261,   59,   59,   59,   59,  266,
  267,  268,  269,  270,  271,   59,  273,  274,  275,   44,
   45,   59,   59,   59,   29,   40,   41,    9,   56,  257,
   16,   -1,   -1,  261,   59,   -1,   40,   41,  266,  267,
  268,  269,  270,  271,   59,  273,  274,  275,   -1,  257,
   -1,   -1,   -1,  261,   -1,   59,   40,   41,  266,  267,
  268,  269,  270,  271,  272,  273,  274,  275,   -1,   -1,
  257,   40,   -1,   -1,  261,   59,   -1,   -1,   -1,  266,
  267,  268,  269,  270,  271,  272,  273,  274,  275,  257,
   40,   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,  267,
  268,  269,  270,  271,  272,  273,  274,  275,  257,   40,
   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,  267,  268,
  269,  270,  271,  272,  273,  274,  275,   -1,   40,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  257,  261,   -1,   -1,  261,   -1,   40,   41,   -1,
  266,  267,  268,  269,  270,  271,  272,  273,  274,  275,
   -1,   -1,   40,   -1,  257,   -1,   -1,   -1,  261,   -1,
   -1,   -1,   -1,  266,  267,  268,  269,  270,  271,   40,
  273,   -1,  275,   -1,   -1,   -1,   -1,  257,   -1,   -1,
   40,  261,   -1,   -1,   -1,   -1,  266,   -1,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,  257,   40,
   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,   -1,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   40,   -1,
   -1,   -1,  257,  258,  259,   -1,  261,   -1,   -1,   -1,
   -1,   -1,  257,   -1,   40,   41,  261,   -1,   -1,   -1,
   -1,  266,   -1,  257,  269,  270,  271,  261,  273,   40,
  275,   -1,  266,   59,   -1,  269,  270,  271,   -1,  273,
   -1,  275,   -1,  257,   -1,   -1,   -1,  261,   -1,   -1,
   -1,   -1,  266,   -1,   -1,  269,  270,  271,  257,  273,
   40,  275,  261,   -1,   -1,   45,   -1,  266,   -1,   -1,
  269,  270,  271,   -1,  273,  274,  275,  257,   -1,   40,
   60,  261,   62,   -1,   45,   -1,  266,   -1,   -1,  269,
  270,  271,  272,  273,   -1,  275,  257,   -1,   -1,   60,
  261,   62,   41,   -1,   -1,  266,   45,   -1,  269,  270,
  271,  272,  273,   -1,  275,  257,   41,   -1,   -1,  261,
   45,   60,   -1,   62,  266,   -1,   -1,  269,  270,  271,
  272,  273,   -1,  275,  257,   60,   -1,   62,  261,   -1,
   43,   -1,   45,  266,   -1,   -1,  269,  270,  271,  257,
  273,   -1,  275,  261,   45,   -1,   -1,   60,  266,   62,
  268,  269,  270,  271,   -1,  273,  257,  275,   -1,   60,
  261,   62,   -1,   -1,   -1,  266,   -1,  257,  269,  270,
  271,  261,  273,  274,  275,   -1,  266,   45,   -1,  269,
  270,  271,   -1,  273,  274,  275,  257,   -1,   -1,   -1,
  261,   -1,   60,   -1,   62,  266,   -1,   -1,  269,  270,
  271,   -1,  273,  274,  275,  257,   -1,   -1,   -1,  261,
   -1,   -1,   -1,   -1,  266,   -1,   -1,  269,  270,  271,
   -1,  273,  274,  275,   -1,  261,   -1,   -1,   -1,   -1,
  266,   -1,   -1,  269,  270,  271,  257,  273,   -1,  275,
  261,   -1,   -1,   -1,   -1,  266,   -1,   -1,  269,  270,
  271,   -1,  273,   -1,  275,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
   -1,   -1,  262,  263,  264,  265,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,
   -1,  262,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,   -1,   -1,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,
  265,   -1,   -1,   -1,   13,   14,   -1,   -1,   -1,   18,
   -1,   -1,   -1,   -1,  257,  258,  259,   -1,   27,  262,
  263,  264,  265,   -1,   -1,   -1,  257,  258,  259,  260,
   -1,  262,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   52,   -1,   -1,   -1,   56,   57,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,   71,   -1,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   88,
   89,   -1,   91,   92,   -1,   -1,   -1,   96,   -1,   98,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  108,
  109,  110,   -1,   -1,  113,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  147,
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
"MAYORIGUAL","MENORIGUAL","DESTINTO","IGUAL","if","else","end_if","print","int",
"begin","end","do","until","float",
};
final static String yyrule[] = {
"$accept : programa",
"programa : conjunto_sentencias",
"conjunto_sentencias : sentencias_declarativas",
"conjunto_sentencias : sentencias_declarativas begin sentencias_ejecutables end",
"conjunto_sentencias : begin sentencias_ejecutables end",
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
"sentencia_declarativa : tipo ID '[' ']' ';'",
"tipo : int",
"tipo : float",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"sentencia_ejecutable : selecion_ejecutable",
"sentencia_ejecutable : control_ejecutable",
"sentencia_ejecutable : salida_ejecutable",
"sentencia_ejecutable : asign",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias else bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias else bloque_sentencias end_if",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias else bloque_sentencias ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias else end_if ';'",
"selecion_ejecutable : if '(' condicion ')' else bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' condicion bloque_sentencias else bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' ')' bloque_sentencias else bloque_sentencias end_if ';'",
"selecion_ejecutable : if condicion ')' bloque_sentencias else bloque_sentencias end_if ';'",
"selecion_ejecutable : '(' condicion ')' bloque_sentencias else bloque_sentencias end_if ';'",
"selecion_ejecutable : '(' condicion ')' bloque_sentencias end_if ';'",
"selecion_ejecutable : if condicion ')' bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' ')' bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' condicion bloque_sentencias end_if ';'",
"selecion_ejecutable : if '(' condicion ')' end_if ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias end_if",
"condicion : expresion_aritmetica operador expresion_aritmetica",
"condicion : operador expresion_aritmetica",
"condicion : expresion_aritmetica expresion_aritmetica",
"condicion : expresion_aritmetica operador",
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
"bloque_sentencias : sentencia_declarativa",
"bloque_sentencias : sentencia_ejecutable",
"bloque_sentencias : begin sentencias sentencia_ejecutable end",
"bloque_sentencias : begin sentencias sentencia_declarativa end",
"bloque_sentencias : sentencias sentencia_ejecutable end",
"bloque_sentencias : begin sentencia_ejecutable end",
"bloque_sentencias : begin sentencias end",
"bloque_sentencias : begin sentencias sentencia_ejecutable",
"bloque_sentencias : sentencias sentencia_declarativa end",
"bloque_sentencias : begin sentencia_declarativa end",
"bloque_sentencias : begin sentencias end",
"bloque_sentencias : begin sentencias sentencia_declarativa",
"control_ejecutable : do bloque_sentencias until '(' condicion ')' ';'",
"control_ejecutable : bloque_sentencias until '(' condicion ')' ';'",
"control_ejecutable : do until '(' condicion ')' ';'",
"control_ejecutable : do bloque_sentencias '(' condicion ')' ';'",
"control_ejecutable : do bloque_sentencias until condicion ')' ';'",
"control_ejecutable : do bloque_sentencias until '(' ')' ';'",
"control_ejecutable : do bloque_sentencias until '(' condicion ';'",
"control_ejecutable : do bloque_sentencias until '(' condicion ')'",
"salida_ejecutable : print '(' CADENA ')' ';'",
"salida_ejecutable : '(' CADENA ')' ';'",
"salida_ejecutable : print CADENA ')' ';'",
"salida_ejecutable : print '(' error ')' ';'",
"salida_ejecutable : print '(' CADENA ';'",
"salida_ejecutable : print '(' CADENA ')'",
"asign : variable ASIGNACION expresion_aritmetica ';'",
"asign : ASIGNACION expresion_aritmetica ';'",
"asign : variable expresion_aritmetica ';'",
"asign : variable ASIGNACION ';'",
"asign : variable ASIGNACION expresion_aritmetica",
"invocacion_metodo : ID '.' ID '(' ')'",
"sentencias : sentencias sentencia_declarativa",
"sentencias : sentencias sentencia_ejecutable",
"sentencias : sentencia_declarativa",
"sentencias : sentencia_ejecutable",
};

//#line 193 "Gramatica1.y"



private AnalizadorLexico lexico;
private TablaSimbolos tablaSimbolos;
private ArrayList<Error> listaErrores = new ArrayList<Error>();
private ArrayList<String> listaCorrectas = new ArrayList<String>();

public Parser(AnalizadorLexico lexico, TablaSimbolos tablaSimbolos) {
	this.lexico = lexico;
	this.tablaSimbolos = tablaSimbolos;
}

public int yylex() {
	int idToken = lexico.yylex();
	yylval = new ParserVal(lexico.token);
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

public void imprimirInformacion() {
	if (!listaCorrectas.isEmpty()){
		System.out.println("Estructuras Sintacticas: ");
		for (String info : listaCorrectas) {
			System.out.println(info);
		}
	}
}

public void imprimirError() {
	if (!listaErrores.isEmpty()){
		System.out.println("Errores Sintacticos: ");
		for (Error e : listaErrores) {
			System.out.println(e);
		}
	}
}

public void agregarConstante (String constante, String tipo, boolean esNegativa) {
	System.out.println("Constante que entra: " + constante);
	if (esNegativa) 
		constante = "-" + constante;
	if (!tablaSimbolos.existeClave(constante))		
		tablaSimbolos.agregar(constante, tipo);
	System.out.println("Entro a agregarConstante, valor:" + constante);	
}

private List<Error> erroresSemanticos = new ArrayList<Error>();
public void agregarVariable (String variable, String tipo) {
	
	if (!tablaSimbolos.existeClave(variable)) {
		tablaSimbolos.agregar(variable, tipo);
	}
	else{
		Error e = new Error ("La variable ya fue declarada.",AnalizadorLexico.nroLinea,"","Error");
		erroresSemanticos.add(e);
	}		
}		
//private List<Error> erroresSemanticos = new ArrayList<Error>()	;	
public void actualizarTabla(String id,String tipo, String valor) {

	if (tipo.equals("int")) {	
		if (Integer.parseInt(valor) < -32768) {
			Error nuevoError = new Error("El numero excede el menor valor posible",AnalizadorLexico.nroLinea," ","Error");
			erroresSemanticos.add(nuevoError);		
		}
		else if (Integer.parseInt(valor) > 32767) {
			Error nuevoError = new Error("El numero excede el mayor valor posible",AnalizadorLexico.nroLinea," ","Error");
			erroresSemanticos.add(nuevoError);	
		}
		else tablaSimbolos.agregar(id, tipo);
	}
	else {
		if ((Float.parseFloat(valor)> Float.MIN_NORMAL && Float.parseFloat(valor) < Float.MAX_VALUE) || (Float.parseFloat(valor)< -Float.MIN_NORMAL && Float.parseFloat(valor) > -Float.MAX_VALUE)
				|| (Float.parseFloat(valor) == 0.0)) {
			tablaSimbolos.agregar(id, tipo);
		}else {Error nuevoError = new Error("El numero excede los rangos",AnalizadorLexico.nroLinea," ","Error");
		erroresSemanticos.add(nuevoError);}
	}
	
}


//#line 714 "Parser.java"
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
//#line 59 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(2).obj).getNroLinea() + ": Sentencia declarativa");}
break;
case 10:
//#line 60 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(5).obj).getNroLinea() + ": Sentencia declarativa");}
break;
case 11:
//#line 61 "Gramatica1.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 12:
//#line 62 "Gramatica1.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 13:
//#line 63 "Gramatica1.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 14:
//#line 64 "Gramatica1.y"
{this.addError("Error en declaracion, falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 15:
//#line 65 "Gramatica1.y"
{this.addError("Error en declaracion, falta ']'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 16:
//#line 66 "Gramatica1.y"
{this.addError("Error en declaracion, falta '['.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 17:
//#line 67 "Gramatica1.y"
{this.addError("Error en declaracion, falta variables.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 18:
//#line 68 "Gramatica1.y"
{this.addError("Error en declaracion, falta identificador.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 19:
//#line 69 "Gramatica1.y"
{this.addError("Error en declaracion, falta constante.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 28:
//#line 86 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(8).obj).getNroLinea() + ": Sentencia if-else");}
break;
case 29:
//#line 87 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(6).obj).getNroLinea() + ": Sentencia if");}
break;
case 30:
//#line 88 "Gramatica1.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 31:
//#line 89 "Gramatica1.y"
{this.addError("Falta 'end_if'.",((Token) val_peek(1).obj).getNroLinea());}
break;
case 32:
//#line 90 "Gramatica1.y"
{this.addError("Falta 'else'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 33:
//#line 91 "Gramatica1.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 34:
//#line 92 "Gramatica1.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 35:
//#line 93 "Gramatica1.y"
{this.addError( "Falta ')'.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 36:
//#line 94 "Gramatica1.y"
{this.addError("Falta condicion.",((Token)val_peek(6).obj).getNroLinea());}
break;
case 37:
//#line 95 "Gramatica1.y"
{this.addError("Falta ')'.",((Token)val_peek(7).obj).getNroLinea());}
break;
case 38:
//#line 96 "Gramatica1.y"
{this.addError("Falta 'if'.",((Token)val_peek(7).obj).getNroLinea());}
break;
case 39:
//#line 97 "Gramatica1.y"
{this.addError("Falta 'if'.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 40:
//#line 98 "Gramatica1.y"
{this.addError("Falta '('.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 41:
//#line 99 "Gramatica1.y"
{this.addError("Falta condicion.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 42:
//#line 100 "Gramatica1.y"
{this.addError("Falta ')'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 43:
//#line 101 "Gramatica1.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 44:
//#line 102 "Gramatica1.y"
{this.addError("Falta 'end_if'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 45:
//#line 103 "Gramatica1.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 47:
//#line 107 "Gramatica1.y"
{this.addError("Falta expresion del lado izquierdo.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 48:
//#line 108 "Gramatica1.y"
{this.addError("Falta operador.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 49:
//#line 109 "Gramatica1.y"
{this.addError("Falta expresion del lado derecho.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 57:
//#line 123 "Gramatica1.y"
{this.agregarConstante(((Token)val_peek(0).obj).getLexema(),"int",false);}
break;
case 58:
//#line 124 "Gramatica1.y"
{this.agregarConstante(((Token)val_peek(0).obj).getLexema(),"float",false);}
break;
case 59:
//#line 125 "Gramatica1.y"
{this.agregarConstante(((Token)val_peek(0).obj).getLexema(),"int",true);}
break;
case 60:
//#line 126 "Gramatica1.y"
{this.agregarConstante(((Token)val_peek(0).obj).getLexema(),"float",true);}
break;
case 75:
//#line 147 "Gramatica1.y"
{this.addError("Falta 'begin'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 76:
//#line 148 "Gramatica1.y"
{/*Error sentencias*/}
break;
case 77:
//#line 149 "Gramatica1.y"
{/*Error sencia_ejecutable*/}
break;
case 78:
//#line 150 "Gramatica1.y"
{this.addError("Falta 'end'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 79:
//#line 151 "Gramatica1.y"
{this.addError("Falta 'begin'.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 80:
//#line 152 "Gramatica1.y"
{/*Error sentencias*/}
break;
case 81:
//#line 153 "Gramatica1.y"
{/*Errorsentencia_declarativa*/}
break;
case 82:
//#line 154 "Gramatica1.y"
{this.addError("Falta 'end'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 83:
//#line 158 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(6).obj).getNroLinea() + ": Sentencia until");}
break;
case 84:
//#line 159 "Gramatica1.y"
{this.addError("Falta 'do'.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 85:
//#line 160 "Gramatica1.y"
{this.addError("Falta bloque de sentencias.",((Token)val_peek(5).obj).getNroLinea());}
break;
case 86:
//#line 161 "Gramatica1.y"
{this.addError("Falta 'until'.",((Token)val_peek(4).obj).getNroLinea());}
break;
case 87:
//#line 162 "Gramatica1.y"
{this.addError("Falta '('.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 88:
//#line 163 "Gramatica1.y"
{this.addError("Falta condicion.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 89:
//#line 164 "Gramatica1.y"
{this.addError("Falta ')'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 90:
//#line 165 "Gramatica1.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 91:
//#line 168 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(4).obj).getNroLinea() + ": Sentencia print");}
break;
case 92:
//#line 169 "Gramatica1.y"
{this.addError("Falta 'print'.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 93:
//#line 170 "Gramatica1.y"
{this.addError("Falta '('.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 94:
//#line 171 "Gramatica1.y"
{this.addError("Solo se puede definir una cadena.",((Token)val_peek(3).obj).getNroLinea());}
break;
case 95:
//#line 172 "Gramatica1.y"
{this.addError("Falta ')'.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 96:
//#line 173 "Gramatica1.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
case 97:
//#line 176 "Gramatica1.y"
{listaCorrectas.add("Linea " + ((Token)val_peek(3).obj).getNroLinea() + ": Asignacion");}
break;
case 98:
//#line 177 "Gramatica1.y"
{this.addError("Falta variable.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 99:
//#line 178 "Gramatica1.y"
{this.addError("Falta ':='.",((Token)val_peek(2).obj).getNroLinea());}
break;
case 100:
//#line 179 "Gramatica1.y"
{this.addError("Falta expresion.",((Token)val_peek(1).obj).getNroLinea());}
break;
case 101:
//#line 180 "Gramatica1.y"
{this.addError("Falta ';'.",((Token)val_peek(0).obj).getNroLinea());}
break;
//#line 1115 "Parser.java"
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
