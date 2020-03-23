%{
    #include <stdio.h>
    FILE* yyin;
%}
%token NOUN PRONOUN VERB ADVERB ADJECTIVE ARTICLE PREPOSITION CONJUCTION
%%
    sentance: simple | compound | sentance compound | sentance simple;

    simple: subject VERB object {printf("Simple Sentence \n");}
    compound: subject VERB object CONJUCTION subject VERB object {printf("Compound Sentence \n");}
    subject: NOUN | PRONOUN;
    object: NOUN | ADJECTIVE NOUN | ADVERB ADJECTIVE NOUN | PREPOSITION NOUN | ARTICLE NOUN | ARTICLE ADJECTIVE NOUN | ARTICLE ADVERB ADJECTIVE NOUN;
%%
void yyerror(const char* s) {
    printf("ERROR: ",s);
}
int main(int argc, char** argv) {
    yyin = fopen(argv[1],"r");
    yyparse();
    fclose(yyin);
    return 0;
}