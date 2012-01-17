package org.daum.ArduinoDecisionSupport.Generator.FuzzyLogic;

import org.daum.ArduinoDecisionSupport.Utils.ArduinoException;

import java.util.ArrayList;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 10/01/12
 * Time: 18:07
 */
public class ArduinoFuzzyDomainsGenerator {

    private ArrayList<ArduinoFuzzyDomainGenerator> inDomains;
    private ArrayList<ArduinoFuzzyDomainGenerator> OutDomains;
    private int num_inputs=0;
    private int num_outputs=0;
    private StringBuffer helpsRules;
    private StringBuilder _invaluesMemberShipFunction;
    private StringBuilder _outvaluesMemberShipFunction;
    private StringBuilder _innum_MemberShipFunction;
    private StringBuilder _outnum_MemberShipFunction;


    public ArduinoFuzzyDomainsGenerator(){
        inDomains = new ArrayList<ArduinoFuzzyDomainGenerator>();
        OutDomains = new ArrayList<ArduinoFuzzyDomainGenerator>();
    }


    public void addFuzzyDomain(ArduinoFuzzyDomainGenerator domain){

         if(domain.getCountIn() == 0 && domain.getCountOut() ==0){ throw new ArduinoException("You have to define at least one Term on your domain '"+domain.getNameDomain()+"'"); }
        if(domain.isIn())
        {
            inDomains.add(domain);
        }
        else {

            OutDomains.add(domain);
        }

    }


    public void generate(){
        helpsRules = new StringBuffer();
        _invaluesMemberShipFunction = new StringBuilder();
        _outvaluesMemberShipFunction = new StringBuilder();
        _innum_MemberShipFunction = new StringBuilder();
        _outnum_MemberShipFunction = new StringBuilder();

        num_inputs=0;
        num_outputs=0;

        for (int i=0;i < inDomains.size();i++){

            if(inDomains.get(i).isIn())
            {
                helpsRules.append("#define IN_"+inDomains.get(i).getNameDomain()+" "+i+"\n");
                helpsRules.append(inDomains.get(i).genInNames());


                _invaluesMemberShipFunction.append("\n{\n");
                _invaluesMemberShipFunction.append(inDomains.get(i).genInValues());
                _invaluesMemberShipFunction.append("}");

                if(num_inputs < inDomains.size()-1)
                    _invaluesMemberShipFunction.append(",");

                // GEN in_num_MemberShipFunction
                _innum_MemberShipFunction.append(inDomains.get(i).getCountIn());
                if(num_inputs < inDomains.size()-1)
                    _innum_MemberShipFunction.append(",");
                num_inputs++;

            }
        }
        for (int i=0;i < OutDomains.size();i++){
            if(OutDomains.get(i).isOut())
            {
                helpsRules.append("#define OUT_"+OutDomains.get(i).getNameDomain()+" "+i+"\n");
                helpsRules.append(OutDomains.get(i).genOutNames());

                _outvaluesMemberShipFunction.append("{");
                _outvaluesMemberShipFunction.append("\n"+OutDomains.get(i).genOutValues());
                _outvaluesMemberShipFunction.append("}");

                if(num_outputs < OutDomains.size()-1)
                    _outvaluesMemberShipFunction.append(",");

                //out_num_MemberShipFunction
                _outnum_MemberShipFunction.append(OutDomains.get(i).getCountOut());
                if(num_outputs < OutDomains.size()-1)
                    _outnum_MemberShipFunction.append(",");

                num_outputs++;
            }
        }

    }

    public StringBuilder get_outnum_MemberShipFunction() {
        return _outnum_MemberShipFunction;
    }

    public int getCountIn() {
        return inDomains.size();
    }

    public int getCoutOut() {
       return OutDomains.size();
    }

    public int getNum_inputs() {
        return num_inputs;
    }

    public int getNum_outputs() {
        return num_outputs;
    }

    public String getHelpsRules() {
        return helpsRules.toString();
    }

    public StringBuilder get_invaluesMemberShipFunction() {
        return _invaluesMemberShipFunction;
    }

    public StringBuilder get_outvaluesMemberShipFunction() {
        return _outvaluesMemberShipFunction;
    }

    public StringBuilder get_innum_MemberShipFunction() {
        return _innum_MemberShipFunction;
    }

    public ArrayList<ArduinoFuzzyDomainGenerator> getInDomains() {
        return inDomains;
    }

    public ArrayList<ArduinoFuzzyDomainGenerator> getOutDomains() {
        return OutDomains;
    }


}
