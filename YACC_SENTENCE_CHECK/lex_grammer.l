%{
    #include <stdio.h>
    #include "y.tab.h";
%}
PRONOUNS    "it"|"i"|"you"|"he"|"they"|"we"|"she"|"who"|"them"|"me"|"him"|"one"|"her"|"us"|"something"|"nothing"|"anything"|"himself"|"everything"|"someone"|"themselves"|"everyone"|"itself"|"anyone"|"myself"|"nobody"
VERBS       "be"|"have"|"do"|"say"|"get"|"make"|"go"|"know"|"take"|"see"|"come"|"think"|"look"|"want"|"give"|"use"|"find"|"tell"|"ask"|"work"|"seem"|"feel"|"try"|"leave"|"call"|"cracked"|"could"
ADVERBS     "up"|"so"|"out"|"just"|"now"|"how"|"then"|"more"|"also"|"here"|"well"|"only"|"very"|"even"|"back"|"there"|"down"|"still"|"in"|"as"|"too"|"when"|"never"|"really"|"most"
ADJECTIVES  "good"|"new"|"first"|"last"|"long"|"great"|"little"|"own"|"other"|"old"|"right"|"big"|"high"|"different"|"small"|"large"|"next"|"early"|"young"|"important"|"few"|"public"|"bad"|"same"|"able"
PREPOSITIONS "behind"|"between"|"Among"|"in"front"of"|"beside"|"near"|"on"|"above"|"to"
ARTICLES    "a"|"an"|"the"
CONJUCTIONS "and"|"that"|"but"|"or"|"as"|"if"|"when"|"than"|"because"|"while"|"where"|"after"|"so"|"though"|"since"|"until"|"whether"|"before"|"although"|"nor"|"like"|"once"|"unless"|"now"|"except"
NOUN        [a-zA-Z]*
%%
(?i:{PRONOUNS})  {return PRONOUN;}
(?i:{VERBS}) {return VERB;}
(?i:{ADVERBS}) {return ADVERB;}
(?i:{ADJECTIVES}) {return ADJECTIVE;}
(?i:{PREPOSITIONS}) {return PREPOSITION;}
(?i:{ARTICLES}) {return ARTICLE;}
(?i:{CONJUCTIONS}) {return CONJUCTION;}
{NOUN} {return NOUN;}
\n ;
.   ;
%%
int yywrap() {
    return 1;
}