%{
    #include<stdio.h>
%}
%token INT CHAR BOOLEAN FLOAT EQUAL IDENTIFIER SEMICOLON COMMA INTVAL CHARVAL BOOLEANVAL REALVAL;
%%
    Declaration : int_declaration | char_declaration | boolean_declaration | float_declaration;

    int_declaration : INT int_declaration_ext SEMICOLON {printf("Valid integer type variable declaration \n");} 
    char_declaration : CHAR char_declaration_ext SEMICOLON {printf("Valid char type variable declaration \n");} 
    boolean_declaration : BOOLEAN boolean_declaration_ext SEMICOLON {printf("Valid boolean type variable declaration \n");} 
    float_declaration : FLOAT float_declaration_ext SEMICOLON {printf("Valid float type variable declaration \n");} 

    int_declaration_ext : IDENTIFIER | IDENTIFIER COMMA int_declaration_ext | IDENTIFIER EQUAL INTVAL |IDENTIFIER EQUAL INTVAL COMMA int_declaration_ext;
    char_declaration_ext : IDENTIFIER | IDENTIFIER COMMA char_declaration_ext | IDENTIFIER EQUAL CHARVAL |IDENTIFIER EQUAL CHARVAL COMMA char_declaration_ext;
    boolean_declaration_ext : IDENTIFIER | IDENTIFIER COMMA boolean_declaration_ext | IDENTIFIER EQUAL BOOLEANVAL |IDENTIFIER EQUAL BOOLEANVAL COMMA boolean_declaration_ext;
    float_declaration_ext : IDENTIFIER | IDENTIFIER COMMA float_declaration_ext | IDENTIFIER EQUAL REALVAL |IDENTIFIER EQUAL REALVAL COMMA float_declaration_ext;
%%

void yyerror(char* s) {
    printf("ERROR %s",s);
}
int main(int argc, char** argv) {
    yyparse();   
    return 0;
}