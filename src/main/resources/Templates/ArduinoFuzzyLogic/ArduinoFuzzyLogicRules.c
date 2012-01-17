typedef struct _Predicate {
	unsigned char domain;
	unsigned char term;
}Predicate;

typedef struct _Rule {
	Predicate             antecedent[NB_TERMS];
	Predicate             consequent[NB_TERMS];
}Rule;


PROGMEM const unsigned char	num_rule_antecedent[NUM_RULES] = { <num_rule_antecedent>};
PROGMEM const unsigned char	num_rule_coutcome[NUM_RULES] = { <num_rule_coutcome>};

const struct _Rule rules[NUM_RULES] = {
 <loadrules>

};
