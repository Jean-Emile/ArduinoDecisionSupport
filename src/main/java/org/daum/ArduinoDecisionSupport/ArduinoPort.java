package org.daum.ArduinoDecisionSupport;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 17/01/12
 * Time: 10:38
 */
public class ArduinoPort {

    private String nameDomain;

    public ArduinoPort(String nameDomaine){
        this.nameDomain = nameDomaine.toLowerCase();
    }

    public String getNameDomain() {
        return nameDomain;
    }

    public void setNameDomain(String nameDomain) {
        this.nameDomain = nameDomain;
    }
}
