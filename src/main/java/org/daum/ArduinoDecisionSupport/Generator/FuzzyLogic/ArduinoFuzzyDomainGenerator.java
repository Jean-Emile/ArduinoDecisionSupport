package org.daum.ArduinoDecisionSupport.Generator.FuzzyLogic;

import org.daum.ArduinoDecisionSupport.ArduinoPort;
import org.daum.ArduinoDecisionSupport.Utils.ArduinoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArduinoFuzzyDomainGenerator extends ArduinoPort {


    private Map<String, Double[]> termsIN;
    private Map<String,  Double> termsOUT;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ArduinoFuzzyDomainGenerator(String name){
        super(name);
        termsIN = new LinkedHashMap<String, Double[]>();
        termsOUT = new LinkedHashMap<String, Double>();
    }

    public void addTerm(String name, Double[] values) throws ArduinoException
    {
        if(!termsOUT.isEmpty()){ throw new ArduinoException("The domain can't have intput Term and output Term simultaneously "); }
        termsIN.put(name.toLowerCase(),values);
    }

    public void addTerm(String name, Double values) throws ArduinoException
    {
        if(!termsIN.isEmpty()){ throw new ArduinoException("The domain can't have intput Term and output Term simultaneously ");}
        termsOUT.put(name.toLowerCase(),values);
    }

    public int getCountOut(){
        return termsOUT.size();
    }
    public int getCountIn(){
        return termsIN.size();
    }
    public Boolean isOut(){
        return !termsOUT.isEmpty();
    }
    public Boolean isIn(){
        return !termsIN.isEmpty();
    }

    public Map<String, Double[]> getTermsIN() {
        return termsIN;
    }

    public Map<String, Double> getTermsOUT() {
        return termsOUT;
    }


    public int getTermPosition(String name) throws ArduinoException {
        Iterator iterator;
        if(isIn())
        {
            iterator = termsIN.entrySet().iterator();
        }else
        {
            iterator = termsOUT.entrySet().iterator();
        }
        int count=0;
        while (iterator.hasNext())
        {
            Map.Entry ent = (Map.Entry) iterator.next();
            if(ent.getKey().equals(name)){
                return count;
            }
            count++;
        }
        throw new ArduinoException("The term '"+name+"' is not found");
    }



    public String genInNames(){
        StringBuffer buffer = new StringBuffer();
        if(logger.isDebugEnabled())
        {
            Iterator iter1 = termsIN.entrySet().iterator();
            int count=0;
            while (iter1.hasNext())
            {
                Map.Entry ent = (Map.Entry) iter1.next();

                buffer.append("#define \t IN_"+getNameDomain()+"_"+ent.getKey()+" "+count+"\n");
                count++;
            }
        }
        return buffer.toString();
    }

    public String genOutNames(){
        StringBuffer buffer = new StringBuffer();
        if(logger.isDebugEnabled()){
            Iterator iter1 = termsOUT.entrySet().iterator();
            int count=0;
            while (iter1.hasNext())
            {
                Map.Entry ent = (Map.Entry) iter1.next();

                buffer.append("#define \t OUT_"+getNameDomain()+"_"+ent.getKey()+" "+count+"\n");
                count++;
            }
        }
        return buffer.toString();
    }


    public String genInValues(){
        StringBuffer buffer = new StringBuffer();

        Iterator iter1 = termsIN.entrySet().iterator();
        while (iter1.hasNext())
        {
            Map.Entry ent = (Map.Entry) iter1.next();

            buffer.append("\t{");

            Double[] values = (Double[]) ent.getValue();
            for(int i=0;i<values.length;i++){
                buffer.append(values[i]);
                if(i < values.length-1)
                    buffer.append(",");
            }
            buffer.append("}\n");
            if(iter1.hasNext())
                buffer.append(",");
        }
        return buffer.toString();
    }


    public String genOutValues(){
        StringBuffer buffer = new StringBuffer();

        Iterator iter1 = termsOUT.entrySet().iterator();

        while (iter1.hasNext())
        {
            Map.Entry ent = (Map.Entry) iter1.next();
            buffer.append("{");
            buffer.append(ent.getValue());
            buffer.append("}\n");
            if(iter1.hasNext())
                buffer.append(",");

        }
        return buffer.toString();
    }
}


