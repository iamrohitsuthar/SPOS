%{
	int lines,words,chars;
%}

%%
" "	{words++;chars++;}
\n	{lines++; words++;chars++;}
.	{chars++;}
%%

int yywrap() {
	return 1;
}
int main(int argc, char* argv[]) {
	yyin = fopen(argv[1], "r");
	yylex();
	printf("No of lines %d\nNo of words %d\nNo of chars %d\n",lines+1,words+1,chars);
	return 0;
}