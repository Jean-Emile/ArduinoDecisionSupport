package org.daum.ArduinoDecisionSupport.Parser.ECA;



import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 10:19
 */
public class ArduinoECARule {

    private List<ArduinoECAEventPredicate> events;

    private String action;

    public ArduinoECARule(List<ArduinoECAEventPredicate> events,String action)
    {
        this.events = events;

        this.action = action;
    }

    public List<ArduinoECAEventPredicate> getEvents() {
        return events;
    }

    public String getAction() {
        return action;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for(ArduinoECAEventPredicate e : events){
                 s.append(e);
        }
        s.append(" =>"+action);

        return s.toString();
    }
}
