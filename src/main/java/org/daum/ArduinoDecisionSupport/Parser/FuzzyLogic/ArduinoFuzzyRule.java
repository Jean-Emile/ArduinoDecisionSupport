package org.daum.ArduinoDecisionSupport.Parser.FuzzyLogic;

import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 10/01/12
 * Time: 13:51
 */
public class ArduinoFuzzyRule {

    private List<ArduinoFuzzyPredicate> antecedent;
    private List<ArduinoFuzzyPredicate> outcome;

    public ArduinoFuzzyRule(List<ArduinoFuzzyPredicate> antecedent,List<ArduinoFuzzyPredicate> outcome){
        this.antecedent = antecedent;
        this.outcome = outcome;
    }


     public String toString(){
        StringBuilder buffer = new StringBuilder();

        for(ArduinoFuzzyPredicate rule: antecedent){
            buffer.append(" "+rule);
        }

        for(ArduinoFuzzyPredicate rule: outcome){
           buffer.append(" "+rule);
        }
        return buffer.toString();
    }

    public   List<ArduinoFuzzyPredicate> getAntecedent(){
        return antecedent;
    }

        public   List<ArduinoFuzzyPredicate> getoutcome(){
        return outcome;
    }

    public void setAntecedent(List<ArduinoFuzzyPredicate> antecedent) {
        this.antecedent = antecedent;
    }

    public void setOutcome(List<ArduinoFuzzyPredicate> outcome) {
        this.outcome = outcome;
    }
}

