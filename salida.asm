.386
.model flat, stdcall
.stack 200h
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.inc

.data
_a DW ?
_b DW ?
_c DW ?
_d DW ?
_e DW ?
_f DW ?
_lardapide@boton DB "lardapide boton", 0
_hola DB "hola", 0
_larda@come@travesti DB "larda come travesti", 0
_0 DW 0
_1 DW 1
_4 DW 4
_5 DW 5
_x DD ?
_z DW ?
_hola2 DB "hola2", 0
auxiliar@8 DW ?
_DividirCero DB "Error al dividir por cero!", 0
_LabelOverflowSuma DB "La suma ha generado un Overflow!", 0

.code
start:
MOV AX, _0
MOV _a, AX

MOV AX, _a
CMP AX , _0

JE Label6

invoke MessageBox, NULL, addr _hola, addr _hola, MB_OK 

JMP Label7
Label6:

invoke MessageBox, NULL, addr _hola2, addr _hola2, MB_OK 

Label7:
Label7:

MOV AX, _a
ADD AX, _1
MOV auxiliar@8 ,AX
JO LabelOverflowSuma

MOV AX, auxiliar@8
MOV _a, AX

invoke MessageBox, NULL, addr _lardapide@boton, addr _lardapide@boton, MB_OK 

MOV AX, _a
CMP AX , _4

JBE Label7

MOV AX, _a
CMP AX , _4

JNE Label17

invoke MessageBox, NULL, addr _larda@come@travesti, addr _larda@come@travesti, MB_OK 

JMP Label18
Label17:

MOV AX, _5
MOV _b, AX

Label18:

invoke ExitProcess, 0
DividirCero:
invoke MessageBox, NULL, addr _DividirCero, addr _DividirCero, MB_OK
invoke ExitProcess, 0
LabelOverflowSuma:
invoke MessageBox, NULL, addr _LabelOverflowSuma, addr _LabelOverflowSuma, MB_OK
invoke ExitProcess, 0
end start