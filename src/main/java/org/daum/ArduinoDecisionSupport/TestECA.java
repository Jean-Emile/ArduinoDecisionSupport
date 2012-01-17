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

        ArduinoPort domain = new ArduinoPort("FAN");

        inputs.addPort(domain);


        ArduinoECACodeGenerator codeECAgen = new ArduinoECACodeGenerator(inputs);


        codeECAgen.addRule("EVERY 2 SECONDES CHECK IF FAN IS EQUALS 22 AND FAN IS LESS 20 DO actionTest;");
        codeECAgen.addRule("EVERY 15 SECONDES CHECK IF FAN IS HIGHER 22 DO actionTest;");




        ArduinoHelpers.createFile("eca.c",         codeECAgen.getCodeGenerate());




    }
}
