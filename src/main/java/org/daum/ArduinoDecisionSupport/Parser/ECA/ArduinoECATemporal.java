package org.daum.ArduinoDecisionSupport.Parser.ECA;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 12/01/12
 * Time: 16:02
 */
public class ArduinoECATemporal extends ArduinoECARule {


    private ArduinoECATemporalPredicate eventTemporal;
    private   ArduinoECARule rule;

    public ArduinoECATemporal(ArduinoECATemporalPredicate p, ArduinoECARule eca){
        super(eca.getEvents(),eca.getAction());
       this.eventTemporal = p;
        this.rule = eca;
    }

    public String toString(){
        return "event="+eventTemporal+" rule="+rule;
    }


    public ArduinoECATemporalPredicate getEventTemporal() {
        return eventTemporal;
    }

    public ArduinoECARule getRule() {
        return rule;
    }


}
