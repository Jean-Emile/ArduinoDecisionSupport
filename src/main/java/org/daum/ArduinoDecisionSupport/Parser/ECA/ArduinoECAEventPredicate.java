package org.daum.ArduinoDecisionSupport.Parser.ECA;

import org.daum.ArduinoDecisionSupport.Constants;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 09:44
 */
public class ArduinoECAEventPredicate {
    private String inputID;
    private Constants.SIGNS sign;



    private float value;
    public ArduinoECAEventPredicate(String inputID, Constants.SIGNS sign, float value)
    {
        this.inputID = inputID;
        this.sign = sign;
        this.value =  value;
    }


    public String toString(){
        return "InputID="+inputID+" "+sign+" "+value;
    }


    public String getInputID() {
        return inputID;
    }

    public Constants.SIGNS getSign() {
        return sign;
    }

    public float getValue() {
        return value;
    }
}
