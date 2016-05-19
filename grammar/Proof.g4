grammar Proof;

init
	:	defop * proof defop *;

defop
	:	LPAREN 'defop' OPID STRING INT oppos ? RPAREN;
oppos
	:	'infix'
	|	'prefix'
	|	'suffix'
	;

proof
	:	LPAREN 'proof' LPAREN subtree RPAREN RPAREN;

subtree
	:	( operator | labeledOperator ) + subtree ?
	|	LPAREN (operator | labeledOperator ) + subtree ? RPAREN
	;

labeledOperator
	:	STRING ':' operator
	;

operator
	:	STRING
	|	LPAREN OPID operator * RPAREN
	;

OPID
	:	[a-zA-Z]+;

LPAREN
	:	'(';
RPAREN
	:	')';

INT
	:	'-' ? [0-9]+;
STRING
	:	'"' (~'"')* '"';
WHITESPACE
	:	[ \t\n\r]+ -> skip;
COMMENT
	:	';' (~ '\n')* -> skip;
