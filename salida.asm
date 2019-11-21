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
_b DD ?
auxiliar@1 DD ?
_w DW 10, 20, 30, 40
_x DD ?, ?, ?, ?
_3 DW 3
_LabelDividirCero DB "Error al dividir por cero!", 0
_LabelOverflowSuma DB "La suma ha generado un Overflow!", 0
_LabelSubIndices DB "Subindice fuera de rango!", 0

.code
start:
MOV CX, _3
CMP CX, 0
JL LabelSubIndices
CMP CX,4
JGE LabelSubIndices
MOV esi, OFFSET _w
MOV AX, _3
IMUL AX, 2
MOVZX EAX,AX
ADD esi,EAX
MOV auxiliar@1, esi

MOV EAX,[auxiliar@1]
CMP AX,40
JE LabelOverflowSuma




invoke ExitProcess, 0
DividirCero:
invoke MessageBox, NULL, addr _LabelDividirCero, addr _LabelDividirCero, MB_OK
invoke ExitProcess, 0
LabelOverflowSuma:
invoke MessageBox, NULL, addr _LabelOverflowSuma, addr _LabelOverflowSuma, MB_OK
invoke ExitProcess, 0
LabelSubIndices:
invoke MessageBox, NULL, addr _LabelSubIndices, addr _LabelSubIndices, MB_OK
invoke ExitProcess, 0
end start