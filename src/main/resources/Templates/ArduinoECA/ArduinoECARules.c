const unsigned char	num_rule_predicate[ECA_NUM_RULES] = {<_num_rule_predicate>};
const struct _Rule rules[ECA_NUM_RULES] = {
	<eca_rules>
};


void (*actions[ECA_NUM_ACTIONS])(void) = {<ECA_ACTIONS> };


void setup_eca()
{
       <ECA_SETUP>
}