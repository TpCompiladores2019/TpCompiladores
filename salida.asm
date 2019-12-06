.386
.model flat, stdcall
.stack 200h
option casemap :none
include \masm32\include\masm32rt.inc
dll_dllcrt0 PROTO C
printf PROTO C : VARARG

.data
_A DQ 2, ?, ?
_3@0 DQ 3.0
_2@0 DQ 2.0
print1 DB "pepe", 0
auxiliarFloat DQ ?
auxiliarInt DW ?
MayorNumFloatPos DQ 3.4028235E38
MenorNumFloatNeg DQ -3.4028235E38
_LabelDividirCero DB "Error al dividir por cero!", 0
_LabelOverflowSuma DB "La suma ha generado un Overflow!", 0
_LabelSubIndices DB "Subindice fuera de rango!", 0

.code
FUNCION_LENGTH:
    MOV AX, [ECX] 
    MOV auxiliarInt, AX 
    RET
FUNCION_FIRSTINT: 
    MOV AX, [ECX + 2] 
    MOV auxiliarInt, AX 
    RET 
FUNCION_LASTINT: 
    MOV AX, [ECX]  
    IMUL AX, 2 
    MOVZX EAX, AX 
    MOV AX, [ECX + EAX] 
    MOV auxiliarInt, AX 
    RET 
FUNCION_FIRSTFLOAT: 
    FLD DWORD PTR [ECX + 8] 
    FST auxiliarFloat 
    RET 
FUNCION_LASTFLOAT: 
    MOV AX, [ECX] 
    IMUL AX, 8 
    MOVZX EAX, AX 
    FLD DWORD PTR [ECX + EAX] 
    FST auxiliarFloat 
    RET 
start:
FINIT
FLD _3@0
FLD _2@0
FCOM
FSTSW AX
SAHF

JBE Label4

invoke StdOut, ADDR print1

Label4:

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