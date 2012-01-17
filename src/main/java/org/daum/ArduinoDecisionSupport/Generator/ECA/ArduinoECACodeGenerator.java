package org.daum.ArduinoDecisionSupport.Generator.ECA;

import org.daum.ArduinoDecisionSupport.ArduinoPort;
import org.daum.ArduinoDecisionSupport.ArduinoPorts;
import org.daum.ArduinoDecisionSupport.Generator.AbstractCodeGenerator;
import org.daum.ArduinoDecisionSupport.Parser.ECA.ArduinoECARule;
import org.daum.ArduinoDecisionSupport.Parser.ECA.ArduinoECARules;
import org.daum.ArduinoDecisionSupport.Parser.ECA.ArduinoECATemporal;
import org.daum.ArduinoDecisionSupport.ParserECA;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoException;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoHelpers;
import org.stringtemplate.v4.ST;

import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 11:14
 */
public class ArduinoECACodeGenerator implements AbstractCodeGenerator {

    private ParserECA ecaDSL;
    private StringBuilder _stringRules;
    private String file_template_header;
    private String file_template_rules;
    private String file_template_framework;
    ArduinoPorts inputs;


    public ArduinoECACodeGenerator(ArduinoPorts inputs){
        ecaDSL   = new ParserECA();
        _stringRules = new StringBuilder();

        file_template_header = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoECA/ArduinoECAheader.c").getPath());
        file_template_rules = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoECA/ArduinoECARules.c").getPath());
        file_template_framework = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoECA/ArduinoECAFramework.c").getPath());
        this.inputs = inputs;
    }

    public String getCodeGenerate() throws ArduinoException {

        StringBuilder code = new StringBuilder();
        ArduinoECARules ecaRules=   ecaDSL.parseRules(_stringRules.toString());
        List<ArduinoECARule> rules = ecaRules.getRules();


        StringBuilder codeInputs = new StringBuilder();
        int count=0;
        for(ArduinoPort input : inputs.getPorts()){
            codeInputs.append("#define ECA_"+input.getNameDomain()+" "+count+"\n");
            count++;
        }

        ST header_code;
        header_code = new ST(file_template_header);

        header_code.add("ECA_NUM_RULES", rules.size());
        header_code.add("ECA_NUM_ACTIONS", rules.size());
        header_code.add("ECA_NUM_PREDICATE",2);
        header_code.add("ECA_NUM_INPUTS", 1);


        StringBuilder eca_rules = new StringBuilder();
        StringBuilder num_rule_predicate  = new StringBuilder();
        StringBuilder actions  = new StringBuilder();

        StringBuilder eca_temporal = new StringBuilder();

        for(int i=0;i< rules.size();i++)
        {

            System.out.println("RULE #"+i);

            if( rules.get(i) instanceof ArduinoECATemporal)
            {


                ArduinoECATemporal temporal =(ArduinoECATemporal) rules.get(i);
                switch (temporal.getEventTemporal().getOp()){

                    case AFTER :

                        break;

                    case EVERY :

                        eca_temporal.append("OnTick_t onTick"+i+";");
                        eca_temporal.append(" onTick"+i+".fnc = fire_rule;");
                        eca_temporal.append("onTick"+i+".id ="+i+";");


                        eca_temporal.append("Alarm.timerRepeat("+temporal.getEventTemporal().getDureeeSecondes()+",onTick"+i+");\n");
                        break;

                }
            }

            // EVENTS    { { 0,2,10.0} }
            eca_rules.append("{ {");

            for(int j=0;j< rules.get(i).getEvents().size();j++)
            {
                eca_rules.append("ECA_"+rules.get(i).getEvents().get(j).getInputID()+","+rules.get(i).getEvents().get(j).getSign().ordinal()+","+rules.get(i).getEvents().get(j).getValue());
                if(j < rules.get(i).getEvents().size()-1)
                    eca_rules.append(",");
            }

            eca_rules.append("} }\n");
            if(i < rules.size()-1)
                eca_rules.append(",");


            num_rule_predicate.append(rules.get(i).getEvents().size());
            if(i < rules.size()-1)
                num_rule_predicate.append(",");

            // DO
            actions.append(  rules.get(i).getAction());
            if(i < rules.size()-1)
                actions.append(",");
        }


        ST rules_code;
        rules_code = new ST(file_template_rules);
        rules_code.add("ECA_ACTIONS", "actionTest");
        rules_code.add("_num_rule_predicate", num_rule_predicate);
        rules_code.add("eca_rules", eca_rules);
        rules_code.add("ECA_SETUP", eca_temporal);

        code.append(codeInputs);
        code.append(header_code.render());
        code.append(rules_code.render());
        code.append(file_template_framework);
        code.append(ArduinoHelpers.createMainArduinoECA());




        return code.toString();
    }

    public void addRule(String rule) {
        _stringRules.append(rule);
    }
}
