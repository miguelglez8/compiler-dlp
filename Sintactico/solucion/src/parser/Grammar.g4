grammar Grammar
	;

import Lexicon
	;

start
	: 'DATA' definicion+ 'CODE' sentencia+ EOF
	;

definicion
	: tipo IDENT ';'
	;

tipo
	: 'int'
	| 'real'
	;

sentencia
	: 'print' expr ';'
	| 'read' IDENT ';'
	| IDENT '=' expr ';'
	| 'if' '(' expr ')' '{' sentencia+ '}'
	| 'if' '(' expr ')' '{' sentencia+ '}' 'else' '{' sentencia+ '}'
	| 'while' '(' expr ')' '{' sentencia+ '}'
	;

expr
	: LITENT
	| LITREAL
	| IDENT
	| expr '+' expr
	| expr '!=' expr
	| '(' expr ')'
	| '<' tipo '>' '(' expr ')'
	;
