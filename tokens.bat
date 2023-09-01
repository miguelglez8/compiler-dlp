@echo off

IF [%1]==[] (
GOTO error_msg
) ELSE (
java -cp "bin;antlr\antlr-4.7.2-complete.jar" org.antlr.v4.gui.TestRig parser.Grammar start -tokens -diagnostics %1
)
GOTO eof

:error_msg
echo Falta el nombre del fichero de entrada (por ejemplo, "source.txt").

:eof

