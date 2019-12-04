.386
.model flat, stdcall
.stack 200h
option casemap :none
include \masm32\include\masm32rt.inc
dll_dllcrt0 PROTO C
printf PROTO C : VARARG

.data
_3 DD 3
_lala DB "lala", 0
_id DD ?
_@10 DD -10
_30 DD 30
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

.code
FUNCION_LENGTH:
    MOV auxiliarArreglo, EAX 
    MOV auxiliarReturn, LENGTHOF auxiliarArreglo 
    RET
FUNCION_FIRSTI: 
    MOV ECX,[EAX] 
    MOV auxiliar,ECX 
    RET 
FUNCION_LASTI: 
    MOV ECX,[EAX + ECX*4]  
    MOV auxiliar,ECX 
    RET 
start:
MOV EAX, _@10
CMP EAX , _30

JL Label5

MOV EAX, _3
MOV _id, EAX

invoke MessageBox, NULL, addr _lala, addr _lala, MB_OK 

Label5:

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