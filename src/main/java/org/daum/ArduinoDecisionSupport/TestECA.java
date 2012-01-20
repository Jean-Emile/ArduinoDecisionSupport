package org.daum.ArduinoDecisionSupport;

import org.daum.ArduinoDecisionSupport.Generator.ECA.ArduinoECACodeGenerator;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoHelpers;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 12/01/12
 * Time: 16:30
 */
public class TestECA {

    public static void main(String[] args) throws Exception  {



        ArduinoPorts inputs = new ArduinoPorts();
        ArduinoPort inputAlerte = new ArduinoPort("temperature");
        inputs.addPort(inputAlerte);
        ArduinoECACodeGenerator codeECAgen = new ArduinoECACodeGenerator(inputs);


        codeECAgen.addRule("EVERY 1 SECONDES CHECK IF temperature IS HIGHER 30 DO turnOnLed;");
        codeECAgen.addRule("EVERY 1 SECONDES CHECK IF temperature IS HIGHER 30 DO turnOnLed;");
        codeECAgen.addRule("EVERY 5 SECONDES CHECK IF temperature IS LESS 30 DO turnOffLed;");
        codeECAgen.addRule("EVERY 1 SECONDES CHECK IF temperature IS HIGHER 40 DO TurnOnbeep;");
        codeECAgen.addRule("EVERY 5 SECONDES CHECK IF temperature IS HIGHER 50 DO TurnOnbeep1;");

        ArduinoHelpers.createFile("eca.c", codeECAgen.getCodeGenerate()+ ArduinoHelpers.createBaliseHommeMortMain());

    }
}
