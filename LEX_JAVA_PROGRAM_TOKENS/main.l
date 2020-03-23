%{

%}

DIGIT [0-9]
NUMBER {DIGIT}+
REAL {DIGIT}+["."]{DIGIT}+
ALPHABATES [a-zA-Z]
IDENTIFIER	{ALPHABATES}({DIGIT}|{ALPHABATES}|"_")*
DATATYPE "int"|"double"|"char"|"bool"|"float"|"void"|"String"
KEYWORD "main"|"static"|"class"
ACCESS_SPECIFIER "private"|"protected"|"public"
LOOPS "for"|"while"|"do"
CONDITIONAL "if"|"switch"|"else"|"else if"
ASSIGNMENT	"="
RELATIONAL_OPERATORS "<"|"<="|">"|">="|"=="|"!="
ARITHMATIC_OPERATORS "+"|"-"|"*"|"/"|"%"
LOGICAL_OPERATORS "&&"|"||"|"!"
STRING_LIT ["\""][a-zA-Z" "]*["\""]
SINGLE_LINE_COMMENT ["//"].({ALPHABATES}|{DIGIT}|" ")*
MULTI_LINE_COMMENT	["/*"].(\n|{ALPHABATES}|{DIGIT}|" "|\t|\s)*.["*/"]
ARRAY {IDENTIFIER}."[]"
%%

"import".*";"	{printf("%s ----> Preprocessor \n", yytext);}
{ACCESS_SPECIFIER}	{printf("%s ----> Access Specifier \n", yytext);}
{DATATYPE}	{printf("%s ----> Datatype \n", yytext);}
{KEYWORD}	{printf("%s ----> keyword \n", yytext);}
{NUMBER}	{printf("%s ----> Number \n", yytext);}
{REAL} {printf("%s ----> Real Number \n", yytext);}
{STRING_LIT} {printf("%s ----> String Literal \n", yytext);}
{MULTI_LINE_COMMENT} {printf("%s ----> is Multi line comment \n", yytext);}
{SINGLE_LINE_COMMENT} {printf("%s ----> is single line comment \n", yytext);}
{ARRAY} {printf("%s ----> is array \n", yytext);}
{IDENTIFIER} {printf("%s ----> Identifier \n", yytext);}
{ASSIGNMENT}	{printf("%s ----> Assignement \n", yytext);}
{RELATIONAL_OPERATORS} {printf("%s ----> Relational Operators\n", yytext);}
{ARITHMATIC_OPERATORS} {printf("%s ----> Arithmatic Operators \n", yytext);}
{LOGICAL_OPERATORS}	{printf("%s ----> Logical Operators \n", yytext);}
"("	{printf("%s ----> Opening Parenthesis \n", yytext);}
")"	{printf("%s ----> Closing Parenthesis \n", yytext);}
"["	{printf("%s ----> Opening Square Bracket \n", yytext);}
"]"	{printf("%s ----> Closing Square Bracket \n", yytext);}
"{"	{printf("%s ----> Opening Curly Brace \n", yytext);}
"}"	{printf("%s ----> Closing Curly Brace \n", yytext);}
";"	{printf("%s ----> Semicolon \n", yytext);}
"\n" ;
.	;
%%

int yywrap() {
	return 1;
}

int main(int argc, char* argv[]) {
	yyin = fopen(argv[1],"r");
	yylex();
	return 0;
}