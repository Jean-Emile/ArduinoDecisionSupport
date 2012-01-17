package org.daum.ArduinoDecisionSupport.Generator.FuzzyLogic;

import java.util.List;

import org.daum.ArduinoDecisionSupport.Generator.AbstractCodeGenerator;
import org.daum.ArduinoDecisionSupport.Parser.FuzzyLogic.ArduinoFuzzyRule;
import org.daum.ArduinoDecisionSupport.ParserFuzzyLogic;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoHelpers;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.*;

public class ArduinoFuzzyCodeGenerator implements AbstractCodeGenerator {

    private String file_template_header;
    private String file_template_domain;
    private String file_template_rules;
    private String file_template_framework;
    private ParserFuzzyLogic fuzzyDSL;
    private StringBuilder _stringRules;
    private ArduinoFuzzyDomainsGenerator domaines;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ArduinoFuzzyCodeGenerator(ArduinoFuzzyDomainsGenerator domaines) {
        this.domaines = domaines;

        // load files templates
        file_template_header = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoFuzzyLogic/ArduinoFuzzyLogicHeader.c").getPath());
        file_template_domain = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoFuzzyLogic/ArduinoFuzzyLogicDomain.c").getPath());
        file_template_rules = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoFuzzyLogic/ArduinoFuzzyLogicRules.c").getPath());
        file_template_framework = ArduinoHelpers.readFile(this.getClass().getClassLoader().getResource("Templates/ArduinoFuzzyLogic/ArduinoFuzzyLogicFramework.c").getPath());

        fuzzyDSL   = new ParserFuzzyLogic();
        _stringRules = new StringBuilder();

    }


    private String generateDomain(){
        StringBuilder code_domain = new StringBuilder();
        ST header_code;
        ST domain_code;
        header_code = new ST(file_template_header);
        domain_code = new ST(file_template_domain);
        domaines.generate();
        header_code.add("NUM_RULES",fuzzyDSL.parseRules(_stringRules.toString()).getRules().size()); //TODO improve avoid to call twice time
        header_code.add("NUM_INPUTS", domaines.getNum_inputs());
        header_code.add("NUM_OUTPUTS", domaines.getNum_outputs());
        domain_code.add("_innum_MemberShipFunction", domaines.get_innum_MemberShipFunction());
        domain_code.add("_outnum_MemberShipFunction",domaines.get_outnum_MemberShipFunction());
        domain_code.add("_invaluesMemberShipFunction",domaines.get_invaluesMemberShipFunction());
        domain_code.add("_outvaluesMemberShipFunction",domaines.get_outvaluesMemberShipFunction());
        code_domain.append(header_code.render());
        code_domain.append(domaines.getHelpsRules());
        code_domain.append(domain_code.render());
        return code_domain.toString();
    }


    public String generateRules() throws ArduinoException {

        List<ArduinoFuzzyRule> rules = fuzzyDSL.parseRules(_stringRules.toString()).getRules();
        StringBuilder code = new StringBuilder();
        StringBuilder _num_rule_antecedent = new StringBuilder();
        StringBuilder _num_rule_coutcome = new StringBuilder();

        ST rules_code;
        rules_code = new ST(file_template_rules);


        for(int i=0;i< rules.size();i++)
        {

            int numberAntecedent =    rules.get(i).getAntecedent().size();
            _num_rule_antecedent.append(numberAntecedent);
            if(i < rules.size()-1){
                _num_rule_antecedent.append(",");
            }

            code.append("{{");
            for(int j=0;j< numberAntecedent;     j++)
            {

                String domain = rules.get(i).getAntecedent().get(j).getDomain();
                String term =   rules.get(i).getAntecedent().get(j).getTerm();

                int domain_position =       getDomainPosition(domain,domaines.getInDomains());
                int term_position =    getDomain(domain,domaines.getInDomains()).getTermPosition(term);
                System.out.println("Rule #" + i + " Antecedent " + j + " " + domain + " " + domain_position + " " + term + " " + term_position + "");

                code.append(domain_position+","+term_position);
                if(numberAntecedent > 0)
                     code.append(",");
                //{ { 1,1},{ 1,1} },



            }
            code.append("},{");
            int numberoutcome =    rules.get(i).getoutcome().size();
            _num_rule_coutcome.append(numberoutcome);
            if(i < rules.size()-1){
                _num_rule_coutcome.append(",");
            }
            for(int j=0;j<  numberoutcome;j++)
            {
                String domain = rules.get(i).getoutcome().get(j).getDomain();
                String term =   rules.get(i).getoutcome().get(j).getTerm();

                int domain_position =       getDomainPosition(domain, domaines.getOutDomains());
                int term_position =    getDomain(domain, domaines.getOutDomains()).getTermPosition(term);

                code.append(domain_position+","+term_position);
                if(numberoutcome > 0)
                    code.append(",");

                System.out.println("Rule #" + i + " Consequence " + j + " " + domain + " " + domain_position + " " + term + " " + term_position + "");

            }
            code.append("}}");
            if(i < rules.size()-1){
                code.append(",");
            }
        }
        rules_code.add("num_rule_antecedent",_num_rule_antecedent);
        rules_code.add("num_rule_coutcome",_num_rule_coutcome);
        rules_code.add("loadrules",code);

        return rules_code.render();
    }



    public String getCodeGenerate() throws ArduinoException{

        StringBuilder code = new StringBuilder();
        code.append(generateDomain());
        code.append(generateRules());
        code.append(file_template_framework);
        code.append(ArduinoHelpers.createMainArduino());
        // code.append(ArduinoHelpers.createMainGCC());
        return code.toString();
    }


    public void addRule(String rule) {
        _stringRules.append(rule+"\n");
    }


    public  int getDomainPosition(String nameDomain,List<ArduinoFuzzyDomainGenerator> liste) throws ArduinoException {

        int count=0;
        for(ArduinoFuzzyDomainGenerator dom :liste) {
            if(dom.getNameDomain().endsWith(nameDomain)){
                return count;
            }
            count++;
        }
        throw new ArduinoException("Domain with name '"+nameDomain+"' is not found");
    }

    public ArduinoFuzzyDomainGenerator getDomain(String nameDomain,List<ArduinoFuzzyDomainGenerator> liste) throws ArduinoException {
        for(ArduinoFuzzyDomainGenerator dom :liste)
        {
            if(dom.getNameDomain().endsWith(nameDomain)){
                return dom;
            }
        }
        throw new ArduinoException("Domain with name '"+nameDomain+"' is not found");
    }





}
