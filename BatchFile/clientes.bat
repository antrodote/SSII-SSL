@echo off
SET /A clientes = 10
:LanzarClientes

if %clientes% == 0 (goto finPrograma) else (goto lanzarCliente)




:lanzarCliente

echo Lanzando cliente
start cmd /k java -jar cliente.jar
Set /A clientes = %clientes% - 1
goto LanzarClientes


:finPrograma

echo Finalizado el programa
PAUSE