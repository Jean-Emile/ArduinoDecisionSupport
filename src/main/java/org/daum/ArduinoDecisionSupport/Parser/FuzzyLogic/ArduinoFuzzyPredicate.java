package org.daum.ArduinoDecisionSupport.Parser.FuzzyLogic;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 10/01/12
 * Time: 14:54
 */
public class ArduinoFuzzyPredicate {
    private String domain;
    private String term;

    public ArduinoFuzzyPredicate(String domain,String term){
        this.domain = domain.toLowerCase();
        this.term = term.toLowerCase();
    }

    public String getDomain(){
        return domain;
    }
    public String getTerm(){
        return term;
    }
    public String toString(){
        return " D="+domain+" T="+term;
    }
}
