package org.daum.ArduinoDecisionSupport.Parser.ECA;

import org.daum.ArduinoDecisionSupport.Constants;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 12/01/12
 * Time: 15:25
 */
public class ArduinoECATemporalPredicate  {


    Constants.OPERATORS op;
    int timer;
    Constants.UNITS unite;
    public ArduinoECATemporalPredicate(Constants.OPERATORS op, int timer, Constants.UNITS unit) {
        this.timer = timer;
        this.unite = unit;
        this.op = op;
    }

    public String toString(){
        return " Operator="+op+" T='"+timer+"' U='"+unite+" ";
    }


    public Constants.OPERATORS getOp() {
        return op;
    }

    public int getTimer() {
        return timer;
    }

    public Constants.UNITS getUnite() {
        return unite;
    }

    public int getDureeeSecondes(){
        int _timer=this.timer;
        switch (getUnite()){

            case HEURES:
                _timer = (_timer * 3600);
                break;

               case MILLISECONDES:

                   _timer = (int)(_timer*0.001);
                   break;
             case MINUTES:
                      _timer = (_timer * 60);
                break;

        }

        return _timer;
    }
}
