%{
	#include <stdio.h>
%}

DATATYPE	int|float|char|double|long
VARIABLE 	^[a-zA-Z_][a-zA-Z0-9_]*
NOT_VARIABLE	^[^a-zA-Z_]
SEMICOLON 	;
COMMA 		,
%%
{DATATYPE}	{ printf("%s is a DATATYPE \n", yytext); }
{VARIABLE}	{ printf("%s is a Valid VARIABLE \n", yytext); }
{NOT_VARIABLE}	{ printf("%s is Invalid VARIABLE \n", yytext); }
{SEMICOLON}	{ printf("%s is a SEMICOLON \n", yytext); }
{COMMA}		{ printf("%s is a COMMA \n", yytext); }
" "			;
"\t"		;
"\n"		{ return 0; }
.			{ printf("LEX ERROR \n"); }
%%
int yywrap() {
	return 1;
}
int main() {
	yylex();
	return 0;
}