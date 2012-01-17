unsigned char	in_num_MemberShipFunction[NUM_INPUTS] = { <_innum_MemberShipFunction> };
float inMemberShipFunction[NUM_INPUTS][NB_TERMS][PRECISION] =
{
		<_invaluesMemberShipFunction>
};
unsigned char	out_num_MemberShipFunction[NUM_INPUTS] = { <_outnum_MemberShipFunction> };
float	outMemberShipFunction[NUM_OUTPUTS][PRECISION][2] =
{
<_outvaluesMemberShipFunction>
};

float	crisp_inputs[NUM_OUTPUTS];    // values of inputs such as sensors
float	crisp_outputs[NUM_OUTPUTS];   // values after rules fire
// arrays use during the fire process of rules
float   fuzzy_outputs[NUM_OUTPUTS][NB_TERMS];
float   fuzzy_inputs[NUM_INPUTS][NB_TERMS];
float   rule_crispvalue[NUM_RULES];

