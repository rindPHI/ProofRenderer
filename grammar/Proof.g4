grammar Proof;

init : defop * proof defop *;

defop : LPAREN 'defop' OPID STRING INT oppos ? RPAREN;
oppos
	:	'infix'
	|	'prefix'
	|	'suffix'
	;

proof : LPAREN 'proof' LPAREN oplist RPAREN;

oplist
	:	operator
	|	LPAREN operator + RPAREN
	;

operator
	:	STRING
	|	LPAREN OPID operator * RPAREN
	;

OPID : [a-zA-Z]+;

LPAREN : '(';
RPAREN : ')';

INT : '-' ? [0-9]+;
STRING : '"' (~'"')* '"';
WHITESPACE : [ \t\n\r]+ -> skip;
COMMENT : ';' (~ '\n')* -> skip;
