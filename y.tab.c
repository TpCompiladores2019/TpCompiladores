#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "Gramatica.y"
	package Sintactico;

	import Lexico.AnalizadorLexico;
	import Lexico.TablaSimbolos;
	import Lexico.Token;
	import java.util.ArrayList;
	import Lexico.Error;
#line 14 "y.tab.c"
#define ID 257
#define CTE_E 258
#define CTE_F 259
#define CADENA 260
#define ASIGNACION 261
#define MAYORIGUAL 262
#define MENORIGUAL 263
#define DESTINTO 264
#define IGUAL 265
#define if 266
#define else 267
#define end_if 268
#define print 269
#define int 270
#define begin 271
#define end 272
#define do 273
#define until 274
#define float 275
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    1,    1,    2,    2,    3,    3,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    6,
    6,    7,    7,    5,    5,    5,    5,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,   12,   12,   12,   12,   14,   14,   14,   16,   16,
   16,   17,   17,   17,   17,   18,   18,   18,   15,   15,
   15,   15,   15,   15,   13,   13,   13,   13,    9,    9,
    9,    9,    9,    9,    9,   10,   10,   10,   10,   10,
   11,   11,   11,   11,   19,   20,   20,   20,   20,
};
short yylen[] = {                                         2,
    1,    1,    4,    3,    2,    1,    2,    1,    3,    6,
    5,    2,    5,    2,    5,    5,    2,    5,    5,    1,
    1,    3,    1,    1,    1,    1,    1,    9,    7,    8,
    8,    8,    8,    8,    8,    6,    6,    6,    6,    6,
    6,    3,    2,    2,    2,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    4,    4,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    4,    4,    7,    6,
    6,    6,    6,    6,    6,    5,    4,    4,    4,    4,
    4,    3,    3,    3,    5,    2,    2,    1,    1,
};
short yydefred[] = {                                      0,
    0,   20,    0,   21,    0,    1,    0,    6,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
    0,   24,   25,   26,   27,    0,    0,    0,    5,    0,
   17,    0,    0,   12,    0,    0,    0,    0,   53,   54,
    0,    0,   51,   52,   55,   62,   60,   63,   64,    0,
   59,   61,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   66,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    0,    0,    9,   22,    0,    0,    0,    0,
    0,    0,    0,   82,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   84,    0,   83,    3,    0,    0,    0,
    0,   11,   56,    0,    0,    0,    0,    0,   49,   50,
    0,    0,    0,    0,    0,   78,   80,    0,   79,   68,
   67,    0,    0,    0,    0,   77,    0,    0,   81,   16,
   15,    0,   19,   18,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   76,    0,    0,    0,    0,    0,
    0,    0,    0,   10,   85,    0,   38,   40,    0,    0,
   41,    0,    0,   39,    0,   37,   71,   74,   75,    0,
   73,   72,    0,   36,   70,    0,    0,   29,    0,    0,
    0,   69,    0,   33,    0,   30,   31,   32,   34,   35,
   28,
};
short yydgoto[] = {                                       5,
    6,    7,   19,   20,   62,    9,   10,   22,   23,   24,
   25,   53,   26,   54,   55,   42,   43,   27,   45,   60,
};
short yysindex[] = {                                   -203,
  -75,    0,  364,    0,    0,    0, -194,    0,  -47,   31,
 -220,  -48, -211,  420,  -25,  364,  183,  388,  202,    0,
    0,    0,    0,    0,    0, -218,  -87,  364,    0,  -80,
    0, -192,   34,    0, -178,   -5, -231,  -29,    0,    0,
   42,   -8,    0,    0,    0,    0,    0,    0,    0,  405,
    0,    0,   57,  463, -211,   62,  -41,    0,    0,  364,
   67,    0,  -33,   70,   75,    0,    0,   80,  -50,   46,
  224,   39,  -79,   50,    0,    0,   92,   66,   68, -122,
  -93, -211, -211,    0, -211, -211,  364,  243,  364,  110,
 -211,  110,  114,  -23,  116,  -89,  -82,  472,  429,  472,
  126,  364,  472,    0,   65,    0,    0,  132,  -40,  134,
  135,    0,    0,   92,  102,  156,   -8,   -8,    0,    0,
 -137,  258, -129, -118,  110,    0,    0,  140,    0,    0,
    0,  159,  443,  160,  161,    0, -105,  162,    0,    0,
    0,  145,    0,    0,    0,  165,  364,  155,  171,   93,
  364,  178,  364,  180,    0,  181,  184,   -4,  185,  186,
  364,  187,  191,    0,    0, -217,    0,    0,  364,  198,
    0, -209, -182,    0, -140,    0,    0,    0,    0,  207,
    0,    0, -128,    0,    0,  218,  -57,    0,  220,  221,
  222,    0,  226,    0,  227,    0,    0,    0,    0,    0,
    0,
};
short yyrindex[] = {                                      0,
   38,    0,    0,    0,    0,    0,  215,    0,    0,    0,
    0,  133,    0,    0,    0,    0,    0,    0,    0,    0,
  108,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,   21,    0,    0,    0,    0,  -37,    0,    0,
    0,  -10,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  279,  298,    0,
    0,    0,    0,    0,    0,    0,  127,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  146,
  330,  157,    0,    0,    0,  309,  345,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -77,    0,    0,   59,   82,    0,    0,
    0,    0,    0,    0,  172,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
short yygindex[] = {                                      0,
    0,    0,  190,   13,   55,    0,  290,    0,    0,    0,
    0,   14,    7,   15,  246,   83,  103,  696,    0,    0,
};
#define YYTABLESIZE 829
short yytable[] = {                                      95,
   23,  196,   58,   58,   58,   58,  100,   58,  104,   58,
   73,   31,    8,  110,   57,   11,   81,  128,  141,   29,
   14,   58,   58,   63,   58,   78,   79,   41,   58,   48,
   48,   65,   48,   85,   48,  127,  180,   36,   86,   13,
   23,   70,   37,   32,   23,   38,   39,   40,   48,   48,
  186,   48,  142,    1,  179,   68,   68,   21,  189,   23,
   14,   80,    1,   88,   68,   74,    2,    3,   90,   92,
   59,    4,   96,   67,   35,    2,   28,   35,   76,   13,
    4,   23,   21,  105,   82,  190,   83,   77,   82,   34,
   83,   68,   75,  121,  123,  124,   23,   89,   46,   46,
   84,   46,   93,   46,  106,  125,   98,   82,  137,   83,
  101,  132,  134,  135,   97,  102,  138,   46,   46,  103,
   46,   47,   47,  139,   47,   67,   47,  191,  150,  147,
  148,  108,   18,   68,   78,  115,   68,  151,  152,  193,
   47,   47,  111,   47,   68,   68,  158,    8,  153,  154,
  112,  171,   82,  166,   83,   68,  172,  173,  113,  175,
  114,  161,  162,  116,  117,  118,    7,  183,   68,   38,
   39,   40,  126,   69,  129,  187,   23,   72,  109,   57,
   57,   57,  130,   57,  136,   44,   44,  119,  120,  131,
  140,   23,  143,  144,  145,  146,   43,   43,  155,  156,
  159,  160,  163,  164,   44,  165,   38,   39,   40,   30,
  195,   42,   42,  167,    2,   43,   68,   71,   94,   58,
   58,   58,   18,   58,   58,   58,   58,   58,   58,  168,
   42,   58,   58,   58,   56,   58,  174,   58,  176,  177,
   99,   18,  178,  181,  182,  184,   48,   48,   48,  185,
   48,   48,   48,   48,   48,   48,  188,   23,   48,   48,
   48,   23,   48,   18,   48,  192,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,  194,   14,  197,  198,
  199,   14,   18,  122,  200,  201,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   13,   18,   33,   91,
   13,    0,    0,    0,    0,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   46,   46,   46,   88,   46,
   46,   46,   46,   46,   46,    0,    0,   46,   46,   46,
    0,   46,    0,   46,    0,    0,    0,   89,   47,   47,
   47,    0,   47,   47,   47,   47,   47,   47,   86,   12,
   47,   47,   47,   13,   47,    0,   47,    0,   14,  169,
  170,   15,    2,   16,    8,   17,   68,    4,    8,   45,
   45,    0,    0,    8,    0,    0,    8,    8,    8,    8,
    8,   66,    8,    7,   87,    0,    0,    7,   45,   58,
   58,   58,    7,   58,    0,    7,    7,    7,    7,    7,
   66,    7,   44,   18,    0,    0,   44,    0,    0,    0,
    0,   44,    0,   43,   44,   44,   44,   43,   44,    0,
   44,    0,   43,    0,    0,   43,   43,   43,   42,   43,
    0,   43,   42,    0,    0,    0,    0,   42,    0,   12,
   42,   42,   42,   13,   42,   87,   42,   51,   14,   52,
    0,   15,    2,   16,    0,   17,   61,    4,   12,   50,
    0,    0,   13,    0,   51,    0,   52,   14,  133,    0,
   15,    2,   16,   66,   17,    0,    4,    0,    0,   51,
   12,   52,    0,  157,   13,    0,    0,    0,   51,   14,
   52,    0,   15,    2,   16,  107,   17,    0,    4,   12,
    0,    0,   51,   13,   52,   82,    0,   83,   14,    0,
    0,   15,    2,   16,   12,   17,    0,    4,   13,    0,
    0,    0,   51,   14,   52,  149,   15,    2,   16,    0,
   17,   51,    4,   52,    0,   88,    0,    0,    0,   88,
    0,    0,    0,    0,   88,    0,    0,   88,   88,   88,
    0,   88,   65,   88,   89,    0,    0,    0,   89,    0,
    0,    0,    0,   89,    0,   86,   89,   89,   89,   86,
   89,   66,   89,    0,   86,    0,    0,   86,   86,   86,
    0,   86,   65,   86,    0,    0,    0,    0,    0,    0,
   45,    0,    0,    0,    0,   45,    0,    0,   45,   45,
   45,   87,   45,    0,   45,   87,    0,    0,    0,    0,
   87,    0,    0,   87,   87,   87,    0,   87,   66,   87,
   12,    0,    0,    0,   13,    0,    0,    0,    0,   14,
    0,    0,   15,    2,   16,    0,   17,    0,    4,    0,
    0,    0,    0,    0,   38,   39,   40,   64,    0,   46,
   47,   48,   49,    0,    0,    0,    0,    0,    0,    0,
    0,   38,   39,   40,    0,    0,   46,   47,   48,   49,
    0,    0,    0,    0,    0,    0,   38,   39,   40,    0,
    0,   46,   47,   48,   49,   38,   39,   40,    0,    0,
   46,   47,   48,   49,    0,    0,    0,    0,    0,   38,
   39,   40,    0,    0,   46,   47,   48,   49,   44,   44,
    0,    0,    0,   44,    0,    0,    0,    0,    0,   38,
   39,   40,   44,    0,   46,   47,   48,   49,   38,   39,
   40,    0,    0,   46,   47,   48,   49,    0,    0,    0,
    0,    0,    0,    0,    0,   44,    0,    0,    0,   44,
   44,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   44,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   44,   44,    0,
   44,   44,    0,    0,    0,    0,   44,    0,    0,    0,
    0,    0,    0,   44,   44,   44,    0,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   44,
};
short yycheck[] = {                                      41,
    0,   59,   40,   41,   42,   43,   40,   45,   59,   47,
   91,   59,    0,   93,   40,   91,   46,   41,   59,    7,
    0,   59,   60,   17,   62,  257,  258,   13,   16,   40,
   41,   18,   43,   42,   45,   59,   41,  258,   47,    0,
   40,   27,   91,   91,   44,  257,  258,  259,   59,   60,
  268,   62,   93,  257,   59,  274,  274,    3,  268,   59,
   40,   91,  257,   50,  274,  258,  270,  271,   54,   55,
   16,  275,   60,   19,   44,  270,  271,   44,  257,   40,
  275,   44,   28,   69,   43,  268,   45,   93,   43,   59,
   45,  274,   59,   87,   88,   89,   59,   41,   40,   41,
   59,   43,   41,   45,   59,   91,   40,   43,  102,   45,
   41,   98,   99,  100,   60,   41,  103,   59,   60,   40,
   62,   40,   41,   59,   43,   71,   45,  268,  122,  267,
  268,   93,   40,  274,  257,  258,  274,  267,  268,  268,
   59,   60,   93,   62,  274,  274,  133,   40,  267,  268,
   59,   59,   43,  147,   45,  274,  150,  151,   93,  153,
   93,  267,  268,  257,   82,   83,   40,  161,  274,  257,
  258,  259,   59,  261,   59,  169,   44,  258,  258,  257,
  258,  259,  272,  261,   59,   40,   41,   85,   86,  272,
   59,   59,   59,   59,   93,   40,   40,   41,   59,   41,
   41,   41,   41,   59,   59,   41,  257,  258,  259,  257,
  268,   40,   41,   59,    0,   59,  274,   28,  260,  257,
  258,  259,   40,  261,  262,  263,  264,  265,  266,   59,
   59,  269,  270,  271,  260,  273,   59,  275,   59,   59,
  274,   40,   59,   59,   59,   59,  257,  258,  259,   59,
  261,  262,  263,  264,  265,  266,   59,  257,  269,  270,
  271,  261,  273,   40,  275,   59,  266,  267,  268,  269,
  270,  271,  272,  273,  274,  275,   59,  257,   59,   59,
   59,  261,   40,   41,   59,   59,  266,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  257,   40,    9,   54,
  261,   -1,   -1,   -1,   -1,  266,  267,  268,  269,  270,
  271,  272,  273,  274,  275,  257,  258,  259,   40,  261,
  262,  263,  264,  265,  266,   -1,   -1,  269,  270,  271,
   -1,  273,   -1,  275,   -1,   -1,   -1,   40,  257,  258,
  259,   -1,  261,  262,  263,  264,  265,  266,   40,  257,
  269,  270,  271,  261,  273,   -1,  275,   -1,  266,  267,
  268,  269,  270,  271,  257,  273,  274,  275,  261,   40,
   41,   -1,   -1,  266,   -1,   -1,  269,  270,  271,  272,
  273,  274,  275,  257,   40,   -1,   -1,  261,   59,  257,
  258,  259,  266,  261,   -1,  269,  270,  271,  272,  273,
  274,  275,  257,   40,   -1,   -1,  261,   -1,   -1,   -1,
   -1,  266,   -1,  257,  269,  270,  271,  261,  273,   -1,
  275,   -1,  266,   -1,   -1,  269,  270,  271,  257,  273,
   -1,  275,  261,   -1,   -1,   -1,   -1,  266,   -1,  257,
  269,  270,  271,  261,  273,   41,  275,   60,  266,   62,
   -1,  269,  270,  271,   -1,  273,  274,  275,  257,   40,
   -1,   -1,  261,   -1,   60,   -1,   62,  266,   40,   -1,
  269,  270,  271,  272,  273,   -1,  275,   -1,   -1,   60,
  257,   62,   -1,   41,  261,   -1,   -1,   -1,   60,  266,
   62,   -1,  269,  270,  271,  272,  273,   -1,  275,  257,
   -1,   -1,   60,  261,   62,   43,   -1,   45,  266,   -1,
   -1,  269,  270,  271,  257,  273,   -1,  275,  261,   -1,
   -1,   -1,   60,  266,   62,  268,  269,  270,  271,   -1,
  273,   60,  275,   62,   -1,  257,   -1,   -1,   -1,  261,
   -1,   -1,   -1,   -1,  266,   -1,   -1,  269,  270,  271,
   -1,  273,  274,  275,  257,   -1,   -1,   -1,  261,   -1,
   -1,   -1,   -1,  266,   -1,  257,  269,  270,  271,  261,
  273,  274,  275,   -1,  266,   -1,   -1,  269,  270,  271,
   -1,  273,  274,  275,   -1,   -1,   -1,   -1,   -1,   -1,
  261,   -1,   -1,   -1,   -1,  266,   -1,   -1,  269,  270,
  271,  257,  273,   -1,  275,  261,   -1,   -1,   -1,   -1,
  266,   -1,   -1,  269,  270,  271,   -1,  273,  274,  275,
  257,   -1,   -1,   -1,  261,   -1,   -1,   -1,   -1,  266,
   -1,   -1,  269,  270,  271,   -1,  273,   -1,  275,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,   -1,  262,
  263,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,
   -1,  262,  263,  264,  265,  257,  258,  259,   -1,   -1,
  262,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,   -1,   -1,  262,  263,  264,  265,   13,   14,
   -1,   -1,   -1,   18,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,   27,   -1,  262,  263,  264,  265,  257,  258,
  259,   -1,   -1,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   50,   -1,   -1,   -1,   54,
   55,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   69,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   82,   83,   -1,
   85,   86,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   98,   99,  100,   -1,   -1,  103,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  133,
};
#define YYFINAL 5
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 275
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'","'.'","'/'",0,0,0,0,0,0,0,0,0,0,
0,"';'","'<'",0,"'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
"'['",0,"']'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,"ID","CTE_E","CTE_F","CADENA","ASIGNACION","MAYORIGUAL",
"MENORIGUAL","DESTINTO","IGUAL","if","else","end_if","print","int","begin",
"end","do","until","float",
};
char *yyrule[] = {
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
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias else bloque_sentencias ';'",
"selecion_ejecutable : if '(' condicion ')' bloque_sentencias bloque_sentencias end_if ';'",
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
"control_ejecutable : do bloque_sentencias until '(' condicion ')' ';'",
"control_ejecutable : bloque_sentencias until '(' condicion ')' ';'",
"control_ejecutable : do until '(' condicion ')' ';'",
"control_ejecutable : do bloque_sentencias '(' condicion ')' ';'",
"control_ejecutable : do bloque_sentencias until condicion ')' ';'",
"control_ejecutable : do bloque_sentencias until '(' ')' ';'",
"control_ejecutable : do bloque_sentencias until '(' condicion ';'",
"salida_ejecutable : print '(' CADENA ')' ';'",
"salida_ejecutable : '(' CADENA ')' ';'",
"salida_ejecutable : print CADENA ')' ';'",
"salida_ejecutable : print '(' ')' ';'",
"salida_ejecutable : print '(' CADENA ';'",
"asign : variable ASIGNACION expresion_aritmetica ';'",
"asign : ASIGNACION expresion_aritmetica ';'",
"asign : variable expresion_aritmetica ';'",
"asign : variable ASIGNACION ';'",
"invocacion_metodo : ID '.' ID '(' ')'",
"sentencias : sentencias sentencia_declarativa",
"sentencias : sentencias sentencia_ejecutable",
"sentencias : sentencia_declarativa",
"sentencias : sentencia_ejecutable",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 191 "Gramatica.y"



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
	for (String info : listaCorrectas) {
		System.out.println(info);
	}
}

public void imprimirError() {
	for (Error e : listaErrores) {
		System.out.println(e);
	}
}
/*
public void agregarATablaSimbolos(Token[] tokensSentencia) {
	//en 0 esta el tipo , en 1 el primer id, en 2 la coma, en 3 id, etc
	ParserVal aux;
	int indice =0;
	String tipoVariables;
	if (valstk[indice] != null) {
		tipoVariables = ((Token) valstk[indice].obj).getLexema();
		indice++;
		}
	else
		return;
	int nroLinea = ((Token) valstk[indice].obj).getNroLinea();
	while (indice < YYSTACKSIZE) {
		String id = ((Token) valstk[indice].obj).getLexema();
		if (!tablaSimbolos.existeClave(id))
			tablaSimbolos.agregar(id, tipoVariables);
		else {
			Lexico.Error e = new Lexico.Error("Variable ya declarada",nroLinea," ","Error");		
			errores.add(e);
		}
			
		indice = indice +2;
		}
	}
}*/


#line 516 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 9:
#line 59 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-2].obj).getNroLinea() + ": Sentencia declarativa");}
break;
case 10:
#line 60 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-5].obj).getNroLinea() + ": Sentencia declarativa");}
break;
case 11:
#line 61 "Gramatica.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 12:
#line 62 "Gramatica.y"
{this.addError("Error en declaracion, falta definir el tipo.",((Token)yyvsp[-1].obj).getNroLinea());}
break;
case 13:
#line 63 "Gramatica.y"
{this.addError("Error en declaracion, falta ';'.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 14:
#line 64 "Gramatica.y"
{this.addError("Error en declaracion, falta ';'.",((Token)yyvsp[0].obj).getNroLinea());}
break;
case 15:
#line 65 "Gramatica.y"
{this.addError("Error en declaracion, falta ']'.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 16:
#line 66 "Gramatica.y"
{this.addError("Error en declaracion, falta '['.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 17:
#line 67 "Gramatica.y"
{this.addError("Error en declaracion, falta variables.",((Token)yyvsp[0].obj).getNroLinea());}
break;
case 18:
#line 68 "Gramatica.y"
{this.addError("Error en declaracion, falta identificador.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 19:
#line 69 "Gramatica.y"
{this.addError("Error en declaracion, falta constante.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 28:
#line 86 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-8].obj).getNroLinea() + ": Sentencia if");}
break;
case 29:
#line 87 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-6].obj).getNroLinea() + ": Sentencia if");}
break;
case 30:
#line 89 "Gramatica.y"
{this.addError("Falta 'end_if'.",((Token) yyvsp[-1].obj).getNroLinea());}
break;
case 31:
#line 90 "Gramatica.y"
{this.addError("Falta 'else'.",((Token)yyvsp[-2].obj).getNroLinea());}
break;
case 32:
#line 93 "Gramatica.y"
{this.addError( "Falta ')'.",((Token)yyvsp[-5].obj).getNroLinea());}
break;
case 33:
#line 94 "Gramatica.y"
{this.addError("Falta condicion.",((Token)yyvsp[-6].obj).getNroLinea());}
break;
case 34:
#line 95 "Gramatica.y"
{this.addError("Falta ')'.",((Token)yyvsp[-7].obj).getNroLinea());}
break;
case 35:
#line 96 "Gramatica.y"
{this.addError("Falta 'if'.",((Token)yyvsp[-7].obj).getNroLinea());}
break;
case 36:
#line 97 "Gramatica.y"
{this.addError("Falta 'if'.",((Token)yyvsp[-5].obj).getNroLinea());}
break;
case 37:
#line 98 "Gramatica.y"
{this.addError("Falta '('.",((Token)yyvsp[-5].obj).getNroLinea());}
break;
case 38:
#line 99 "Gramatica.y"
{this.addError("Falta condicion.",((Token)yyvsp[-4].obj).getNroLinea());}
break;
case 39:
#line 100 "Gramatica.y"
{this.addError("Falta ')'.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 40:
#line 101 "Gramatica.y"
{this.addError("Falta bloque de sentencias.",((Token)yyvsp[-2].obj).getNroLinea());}
break;
case 41:
#line 102 "Gramatica.y"
{this.addError("Falta 'end_if'.",((Token)yyvsp[-1].obj).getNroLinea());}
break;
case 42:
#line 106 "Gramatica.y"
{/*Agregar*/}
break;
case 43:
#line 107 "Gramatica.y"
{/*Error expresion_aritmetica*/}
break;
case 44:
#line 108 "Gramatica.y"
{/*Error comparador*/}
break;
case 45:
#line 109 "Gramatica.y"
{/*Error expresion_aritmetica*/}
break;
case 65:
#line 141 "Gramatica.y"
{/*Agregar*/}
break;
case 66:
#line 142 "Gramatica.y"
{/*Agregar*/}
break;
case 67:
#line 143 "Gramatica.y"
{/*Agregar */}
break;
case 68:
#line 144 "Gramatica.y"
{/*Agregar*/}
break;
case 69:
#line 156 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-6].obj).getNroLinea() + ": Sentencia until");}
break;
case 70:
#line 157 "Gramatica.y"
{this.addError("Falta 'do'.",((Token)yyvsp[-5].obj).getNroLinea());}
break;
case 71:
#line 158 "Gramatica.y"
{this.addError("Falta bloque de sentencias.",((Token)yyvsp[-5].obj).getNroLinea());}
break;
case 72:
#line 159 "Gramatica.y"
{this.addError("Falta 'until'.",((Token)yyvsp[-4].obj).getNroLinea());}
break;
case 73:
#line 160 "Gramatica.y"
{this.addError("Falta '('.",((Token)yyvsp[-3].obj).getNroLinea());}
break;
case 74:
#line 161 "Gramatica.y"
{this.addError("Falta condicion.",((Token)yyvsp[-2].obj).getNroLinea());}
break;
case 75:
#line 162 "Gramatica.y"
{this.addError("Falta ')'.",((Token)yyvsp[-1].obj).getNroLinea());}
break;
case 76:
#line 166 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-4].obj).getNroLinea() + ": Sentencia print");}
break;
case 77:
#line 167 "Gramatica.y"
{/*Error print*/}
break;
case 78:
#line 168 "Gramatica.y"
{/*Error ( */}
break;
case 79:
#line 169 "Gramatica.y"
{/*Error cadena */}
break;
case 80:
#line 170 "Gramatica.y"
{/*Error ) */}
break;
case 81:
#line 174 "Gramatica.y"
{listaCorrectas.add("Linea " + ((Token)yyvsp[-3].obj).getNroLinea() + ": Asignacion");}
break;
case 82:
#line 175 "Gramatica.y"
{/*Error variable */}
break;
case 83:
#line 176 "Gramatica.y"
{/*Error Asignacion */}
break;
case 84:
#line 177 "Gramatica.y"
{/*Error expresio_aritmetica */}
break;
#line 852 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
