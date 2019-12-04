.386
.model flat, stdcall
.stack 200h
option casemap :none
include \masm32\include\masm32rt.inc
dll_dllcrt0 PROTO C
printf PROTO C : VARARG

.data
_a DD 10, 20, 30, 40
_b DD ?
auxiliar@1 DD ?
auxiliar DD ?
MayorNumInt DD 32767
MenorNumInt DD -32768
MayorNumFloatPos DQ 3.4028235E38
MenorNumFloatPos DQ 1.17549435E-38
numFLoat0@0 DQ 0.0
MayorNumFloatNeg DQ -1.17549435E-38
MenorNumFloatNeg DQ -3.4028235E38
_LabelDividirCero DB "Error al dividir por cero!", 0
_LabelOverflowSuma DB "La suma ha generado un Overflow!", 0
_LabelSubIndices DB "Subindice fuera de rango!", 0
auxiliarParametro DD 2
auxiliarReturn DD 3
aux DW -2
.code

FUNCION_LENGTH:
    MOV ECX,[EAX]
    MOV auxiliarReturn,ECX
    RET
FUNCION_FIRST: 
    MOV auxiliarReturn,ECX 
    RET 
FUNCION_LASTI: 
    MOV ECX,[EAX + ECX*4]  
    MOV auxiliar,ECX 
    RET 
start:


MOV ECX, offset _a
call FUNCION_FIRST
mov ecx,auxiliar
invoke printf , cfm$("%d \n"), auxiliarReturn

mov auxiliarParametro, auxiliarReturn
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