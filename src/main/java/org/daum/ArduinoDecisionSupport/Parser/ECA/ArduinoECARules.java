package org.daum.ArduinoDecisionSupport.Parser.ECA;

import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 10:45
 */
public class ArduinoECARules {

    private List<ArduinoECARule> rules;


    public ArduinoECARules(List<ArduinoECARule> rules){
        this.rules = rules;
    }

    public String toString(){
        StringBuilder buffer = new StringBuilder();

        for(ArduinoECARule rule: rules){
            buffer.append(rule);
        }
        return buffer.toString();
    }

    public   List<ArduinoECARule> getRules(){
        return rules;
    }


}
