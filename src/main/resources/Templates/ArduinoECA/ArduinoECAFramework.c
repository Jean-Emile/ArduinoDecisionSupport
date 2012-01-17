float get_input_value(unsigned char i)
{
	return inputs_values[i];
}

void fire_rule(unsigned char i)
{
	unsigned char j=0;
    boolean condition = true;
	Serial.print("fire rule #");
     	Serial.println(i);

     while(j<num_rule_predicate[i] && condition== true)
     {
		switch(rules[i].predicate[j].operation)
		{
		case EQUALS :

		    Serial.print(get_input_value(rules[i].predicate[j].id));
			Serial.print("=");
			Serial.println(rules[i].predicate[j].value);
			if(get_input_value(rules[i].predicate[j].id) == rules[i].predicate[j].value)
			{
				(*actions[i])();
			}else { condition = false;}
			break;

		case HIGHER :
		    Serial.print(get_input_value(rules[i].predicate[j].id));
			Serial.print(">");
			Serial.println(rules[i].predicate[j].value);
			if(get_input_value(rules[i].predicate[j].id) > rules[i].predicate[j].value)
			{
				(*actions[i])();
				}else { condition = false;}

			break;

		case LESS :
		    Serial.print(get_input_value(rules[i].predicate[j].id));
			Serial.print("=");
			Serial.println(rules[i].predicate[j].value);
			if(get_input_value(rules[i].predicate[j].id) < rules[i].predicate[j].value)
			{
				(*actions[i])();
					}else { condition = false;}

			break;
		default:
			Serial.println("ERROR");
			break;
		}
		j++;
	}
}

void fire_all(){
	unsigned int i=0;
	for(i=0;i< ECA_NUM_RULES;i++)
	{
		fire_rule(i);
	}
}

