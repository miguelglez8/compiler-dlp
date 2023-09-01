lexer grammar Lexicon
	;

LITENT
	: [0-9]+
	;

LITREAL
	: [0-9]+ '.' [0-9]+
	;

IDENT
	: [a-zA-Z_][a-zA-Z0-9_]+
	;

COMMENT
	: '/*' .*? '*/' -> skip
	;

WS
	: [ \t\r\n]+ -> skip
	;
