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
public class PocBaliseHommeMort {


    public static void main(String[] args) throws Exception  {


        /*

 pré-alarme : pas de mouvement détecté pendant 18 à 23 sec :
affichage des led jaunes interrompu par 4 led rouges clignotantes, accompagné d'un signal sonore crescendo.-
alarme : pas de mouvement détecté pendant 30 à 35 sec : affichage rapide des 4 led rouges clignotantes, avec signal sonore fort (98 dba)

         */

        ArduinoFuzzyDomainGenerator temperature = new ArduinoFuzzyDomainGenerator("temperature");
        temperature.addTerm("cold", new Double[]{-50.000000, -50.000000, -50.000000, 0.000000});
        temperature.addTerm("warm", new Double[]{0.000000, 11.000000, 17.000000, 25.000000});
        temperature.addTerm("cool", new Double[]{20.000000, 24.000000, 28.000000, 30.000000});
        temperature.addTerm("hot", new Double[]{28.000000, 100.000000, 100.000000, 100.000000});

        ArduinoFuzzyDomainGenerator mouvement = new ArduinoFuzzyDomainGenerator("mouvement");
        mouvement.addTerm("aucun", new Double[]{0.000000, 0.000000, 0.000000, 0.000000});
        mouvement.addTerm("leger", new Double[]{-10.000000, -10.000000, -5.000000, 0.000000});
        mouvement.addTerm("course", new Double[]{-10.000000, -10.000000, -5.000000, 0.000000});
        mouvement.addTerm("chute", new Double[]{-10.000000, -10.000000, -5.000000, 0.000000});


        ArduinoFuzzyDomainGenerator danger = new ArduinoFuzzyDomainGenerator("danger");
        danger.addTerm("minimum", 5.0);
        danger.addTerm("fort", 63.0);
        danger.addTerm("important", 100.0);


        ArduinoFuzzyDomainsGenerator domaines = new ArduinoFuzzyDomainsGenerator();
        domaines.addFuzzyDomain(temperature);
        domaines.addFuzzyDomain(danger);
        domaines.addFuzzyDomain(mouvement);


        ArduinoFuzzyCodeGenerator generator = new ArduinoFuzzyCodeGenerator(domaines);


        generator.addRule("SI temperature IS warm AND mouvement IS course THEN danger IS minimum;");
        generator.addRule("SI temperature IS warm AND mouvement IS leger THEN danger IS minimum;");

        generator.addRule("SI temperature IS HOT mouvement IS leger THEN danger IS fort;");
        generator.addRule("SI temperature IS HOT mouvement IS leger THEN danger IS fort;");



        generator.addRule("IF temperature IS COOL AND temperature IS HOT accelerometre IS leger THEN levelalert IS alerte;");



        generator.addRule("IF temperature IS warm AND accelerometre IS course THEN levelalert IS veille;");
        generator.addRule("IF accelerometre IS aucun SINCE AND accelerometre IS course THEN levelalert IS veille;");




        //   ArduinoECACodeGenerator ecaCodeGenerator = new ArduinoECACodeGenerator();
        // ecaCodeGenerator.addRule("EVERY 1 SECONDES CHECK IF levelalert IS HIGHER 30 AND  levelalert IS LESS 40 DO BEEP;");




        ArduinoHelpers.createFile("fuzzy.c", generator.getCodeGenerate());


    }
}
