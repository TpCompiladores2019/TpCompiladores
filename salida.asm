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
_x DD ?
_z DW ?
_20 DW 20
auxiliar@1 DD ?
auxiliar@2 DD ?
auxiliar@3 DD ?
auxiliar@4 DD ?
auxiliar@5 DD ?
auxiliar@6 DD ?
_DividirCero db "Error al dividir por cero!", 0
_DividirCero db "La suma ha generado un Overflow!", 0

.code
start:
invoke ExitProcess, 0
