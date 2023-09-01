lexer grammar Lexicon;


VAR: 'var';
TYPE: 'type';

INT: 'int';
FLOAT: 'float';

PUNTOS: ':';
COMA: ',';

NUM : [0-9]+;

IDENT : [a-zA-Z][a-zA-Z0-9_]*;

WS : [ \t\r\n]+ -> skip;
