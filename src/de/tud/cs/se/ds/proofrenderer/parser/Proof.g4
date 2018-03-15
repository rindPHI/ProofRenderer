grammar Proof;

init
	:	(defop | macro | usepkg | input) * proof (defop | macro | usepkg) *;

usepkg
	:	LPAREN 'usepackage' pkgname pkgargs ? RPAREN
	;
pkgname
	:	STRING
	;
pkgargs
	:	STRING
	;

input
	:	LPAREN 'input' latexfile RPAREN
	;
latexfile
	:	STRING
	;

macro
	:	LPAREN 'macro' macroid numparams macrodef RPAREN;
macroid
	:	ID
	;
numparams
	:	INT
	;
macrodef
	:	STRING
	;

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
	|	'param'
	;

proof
	:	LPAREN 'proof' subtree RPAREN;

subtree
	:	operator + subtree *
	|	LPAREN operator + subtree * RPAREN
	;

operator
	:	STRING
	|	( leftLabel ) ? LPAREN opid operator * RPAREN ( rightLabel ) ?
	;

leftLabel
	:	operatorLabel '->'
	;

rightLabel
	:	'<-' operatorLabel
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
	:	'"' ('""'|~'"')* '"';

WHITESPACE
	:	[ \t\n\r]+ -> skip;
COMMENT
	:	';' (~ '\n')* -> skip;
