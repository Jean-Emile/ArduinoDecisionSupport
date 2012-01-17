const unsigned char	num_rule_predicate[ECA_NUM_RULES] = {<_num_rule_predicate>};
const struct _ECA_Rule eca_rules[ECA_NUM_RULES] = {
	<eca_rules>
};


void (*eca_actions[ECA_NUM_ACTIONS])(void) = {<ECA_ACTIONS> };


void setup_eca()
{
       <ECA_SETUP>
}