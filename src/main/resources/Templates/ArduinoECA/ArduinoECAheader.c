#include \<Time.h>
#include \<TimeAlarms.h>

#define ECA_NUM_RULES <ECA_NUM_RULES>
#define ECA_NUM_INPUTS <ECA_NUM_INPUTS>
#define ECA_NUM_ACTIONS <ECA_NUM_ACTIONS>
#define ECA_NUM_PREDICATE <ECA_NUM_PREDICATE>

#define HIGHER 0
#define LESS 1
#define EQUALS 2

#define AND 0
#define OR 1

static float inputs_values[ECA_NUM_RULES];

float get_input_value(unsigned char i);
void fire_rule(unsigned char i);
void fire_all();

typedef struct _eca {
	unsigned char id;
	unsigned char operation;
	float value;
}Predicate;

typedef struct _Rule {
	Predicate             predicate[ECA_NUM_PREDICATE];
}Rule;


void actionTest(){
	Serial.println("BEEP");
}
