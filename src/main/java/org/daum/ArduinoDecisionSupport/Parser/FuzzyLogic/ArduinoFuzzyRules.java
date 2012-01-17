package org.daum.ArduinoDecisionSupport.Parser.FuzzyLogic;

import java.util.List;

public class ArduinoFuzzyRules {

    private  List<ArduinoFuzzyRule> rules;


    public ArduinoFuzzyRules(List<ArduinoFuzzyRule> rules){
        this.rules = rules;
    }

    public String toString(){
        StringBuilder buffer = new StringBuilder();

        for(ArduinoFuzzyRule rule: rules){
            buffer.append(rule);
        }
        return buffer.toString();
    }

    public   List<ArduinoFuzzyRule> getRules(){
        return rules;
    }



}
