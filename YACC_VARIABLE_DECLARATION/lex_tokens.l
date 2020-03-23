%{
    #include "y.tab.h";
    #include<stdio.h>
%}
DIGIT [0-9]
REAL {DIGIT}+["."]{DIGIT}+
LETTERS [a-zA-Z]
IDENTIFIER (_({LETTERS}|_)|{LETTERS})({LETTERS}|{DIGIT}|"_")*
BOOLEANVAL true|false
CHARVAL ['][a-zA-Z0-9][']
COMMA ,
EQUAL =
SEMICOLON ;
NEWLINE \n
%%
[ \t] ;
int {return INT;}
char {return CHAR;}
boolean {return BOOLEAN;}
float {return FLOAT;}
{DIGIT} {return INTVAL;}
{CHARVAL} {return CHARVAL;}
{BOOLEANVAL} {return BOOLEANVAL;}
{REAL} {return REALVAL;}
{IDENTIFIER} {return IDENTIFIER;}
{EQUAL} {return EQUAL;}
{COMMA} {return COMMA;}
{SEMICOLON} {return SEMICOLON;}
{NEWLINE} {return 0;}
. ;
%%
int yywrap() {
    return 1;
}