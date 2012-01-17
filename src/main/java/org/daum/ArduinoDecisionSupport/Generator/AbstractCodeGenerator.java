package org.daum.ArduinoDecisionSupport.Generator;

import org.daum.ArduinoDecisionSupport.Utils.ArduinoException;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 11:19
 */
public interface AbstractCodeGenerator {

    public String getCodeGenerate() throws ArduinoException;
    public void addRule(String rule);
}
