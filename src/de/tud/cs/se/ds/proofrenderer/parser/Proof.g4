grammar Proof;

init
	:	defop * proof defop *;

defop
	:	LPAREN 'defop' opid opdef opprec oppos ? RPAREN;
opid
    :   ID
    ;
opdef
    :   STRING
    ;
opprec
    :   INT
    ;
oppos
	:	'infix'
	|	'prefix'
	|	'suffix'
	;

proof
	:	LPAREN 'proof' subtree RPAREN;

subtree
	:	operator + subtree *
	|	LPAREN operator + subtree * RPAREN
	;

operator
	:	STRING
	|	( operatorLabel ':' ) ? LPAREN opid operator * RPAREN
	;
	
operatorLabel
	:	STRING
	;

ID
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
