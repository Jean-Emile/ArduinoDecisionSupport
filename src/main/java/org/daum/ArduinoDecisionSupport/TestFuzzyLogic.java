package org.daum.ArduinoDecisionSupport;

import org.daum.ArduinoDecisionSupport.Generator.FuzzyLogic.ArduinoFuzzyDomainsGenerator;
import org.daum.ArduinoDecisionSupport.Generator.FuzzyLogic.ArduinoFuzzyCodeGenerator;
import org.daum.ArduinoDecisionSupport.Generator.FuzzyLogic.ArduinoFuzzyDomainGenerator;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoHelpers;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 11/01/12
 * Time: 9:00
 */
public class TestFuzzyLogic {


    public static void main(String[] args) throws Exception  {


        ArduinoFuzzyDomainGenerator temperature = new ArduinoFuzzyDomainGenerator("temperature");
        temperature.addTerm("cold", new Double[]{-10.000000, -10.000000, -5.000000, 0.000000});
        temperature.addTerm("warm", new Double[]{0.000000, 11.000000, 17.000000, 25.000000});
        temperature.addTerm("cool", new Double[]{24.000000, 24.000000, 28.000000, 30.000000});
        temperature.addTerm("hot", new Double[]{28.000000, 60.000000, 60.000000, 60.000000});

        ArduinoFuzzyDomainGenerator fan = new ArduinoFuzzyDomainGenerator("fan");
        fan.addTerm("stop", 0.0);
        fan.addTerm("slow", 13.0);
        fan.addTerm("speed", 63.0);
        fan.addTerm("fast", 100.0);


        ArduinoFuzzyDomainsGenerator domaines = new ArduinoFuzzyDomainsGenerator();
        domaines.addFuzzyDomain(temperature);
        domaines.addFuzzyDomain(fan);


        ArduinoFuzzyCodeGenerator fuzzycodegenerator = new ArduinoFuzzyCodeGenerator(domaines);

        fuzzycodegenerator.addRule("IF temperature IS cold THEN fan IS stop;");
        fuzzycodegenerator.addRule("IF temperature IS warm THEN fan IS slow;");
          fuzzycodegenerator.addRule("IF temperature IS HOT THEN fan IS fast;");
        fuzzycodegenerator.addRule("IF temperature IS warm AND temperature IS COOL THEN fan IS fast;");
        fuzzycodegenerator.addRule("IF temperature IS cool AND temperature IS warm THEN fan IS speed;");


        ArduinoHelpers.createFile("fuzzy.c", fuzzycodegenerator.getCodeGenerate());


    }
}
