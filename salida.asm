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
_2 DW 2
_c DW ?
_d DW ?
_e DW ?
_5 DW 5
_f DW ?
_6 DW 6
_x DD ?
_z DW ?
_DividirCero db "Error al dividir por cero!", 0
_LabelOverflowSuma db "La suma ha generado un Overflow!", 0

.code
start:
MOV AX, _a
CMP AX , _b

JBE Label5

MOV AX, _2
MOV _a, AX

JMP Label6
Label5
MOV AX, _5
MOV _b, AX

Label6:
MOV AX, _6
MOV _e, AX


invoke ExitProcess, 0
