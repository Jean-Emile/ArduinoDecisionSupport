package org.daum.ArduinoDecisionSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 16/01/12
 * Time: 15:03
 */
public class ArduinoPorts {

    private List<ArduinoPort> ports;

    public ArduinoPorts(){
        ports = new ArrayList<ArduinoPort>();
    }


    public List<ArduinoPort> getPorts() {
        return ports;
    }

    public void addPort(ArduinoPort inputs) {
        this.ports.add(inputs);
    }
}
