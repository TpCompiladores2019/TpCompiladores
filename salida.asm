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
_11 DW 11
_a DW ?
_12 DW 12
_b DW ?
_c DW ?
_d DW ?
_e DW ?
_f DW ?
print1 DB "larda come travesti", 0
_1 DW 1
auxiliar@4 DW ?
_5 DW 5
_x DD ?
_z DW ?
_10 DW 10
_DividirCero DB "Error al dividir por cero!", 0
_LabelOverflowSuma DB "La suma ha generado un Overflow!", 0

.code
start:
MOV AX, _b
MOV _a, AX

MOV AX, _a
MOV _c, AX

Label3:

MOV AX, _a
ADD AX, _1
MOV auxiliar@4 ,AX
JO LabelOverflowSuma

MOV AX, auxiliar@4
MOV _a, AX

MOV AX, _a
CMP AX , _11

JNE Label3

MOV AX, _a
CMP AX , _12

JNE Label12

invoke MessageBox, NULL, addr , addr , MB_OK 

JMP Label13
Label12:

MOV AX, _5
MOV _b, AX

Label13:

invoke ExitProcess, 0
DividirCero:
invoke MessageBox, NULL, addr _DividirCero, addr _DividirCero, MB_OK
invoke ExitProcess, 0
LabelOverflowSuma:
invoke MessageBox, NULL, addr _LabelOverflowSuma, addr _LabelOverflowSuma, MB_OK
invoke ExitProcess, 0
end start