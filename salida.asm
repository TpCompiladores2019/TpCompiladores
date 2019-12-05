.386
.model flat, stdcall
.stack 200h
option casemap :none
include \masm32\include\masm32rt.inc
dll_dllcrt0 PROTO C
printf PROTO C : VARARG

.data
_a DW 2, 5, 6 
_b DQ 4, 5.0 , 6.0
print1 DB "hola", 0
print2 DB "pepe", 0
auxiliar DW ?
auxiliar2 DQ ?
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
    mov ax,[ecx]
    mov auxiliar,ax
    RET

FUNCION_FIRSTI:
    MOV AX,[ECX + 2]
    MOV auxiliar,AX
    ret

FUNCION_FIRSTF:
    FLD DWORD PTR [ECX + 8]
    FST auxiliar2
    RET

FUNCION_LASTI: 
    MOV AX,[ECX]
    IMUL AX,2
    MOVZX EAX,AX
    MOV AX,[ECX + EAX]
    MOV auxiliar,AX
    RET

FUNCION_LASTF:
    MOV AX,[ECX]
    IMUL AX,8
    MOVZX EAX,AX
    FLD DWORD PTR [ECX + EAX]
    FST auxiliar2
    ret
start:

mov ecx, offset _b
call FUNCION_LENGTH



MOV ecx, offset _a
CALL FUNCION_LASTI
    
invoke printf , cfm$("%.d \n"), auxiliar



invoke StdOut, ADDR print1

invoke StdOut, ADDR print2


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