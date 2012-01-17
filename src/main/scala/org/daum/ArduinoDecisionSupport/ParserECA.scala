package org.daum.ArduinoDecisionSupport
import Parser._
import ECA._
import FuzzyLogic._
import scala.util.parsing.combinator.syntactical._
import scala.collection.JavaConversions._
import Utils.ArduinoException

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 10:08
 */

class ParserECA  extends StandardTokenParsers{

  lexical.delimiters ++= List(";")
  lexical.reserved +=("IF", "THAN","IS", "AND","OR","DO","CHECK","EVERY","SINCE","AFTER","MILLISECONDES","MINUTES","SECONDES","HEURES","EQUALS","HIGHER","LESS")

  def value  =numericLit ^^ {
    case s => s
  }

  def parseOperator: Parser[Constants.OPERATORS] =
    ("EVERY" | "AFTER") ^^ {
      case "EVERY" => Constants.OPERATORS.EVERY
      case "AFTER" => Constants.OPERATORS.AFTER
    }


  def parseUnit: Parser[Constants.UNITS] =
    ("MILLISECONDES" | "SECONDES"| "MINUTES" | "HEURES" ) ^^ {
      case "MILLISECONDES" => Constants.UNITS.MILLISECONDES
      case "SECONDES" => Constants.UNITS.SECONDES
      case "MINUTES" => Constants.UNITS.MINUTES
      case "HEURES" => Constants.UNITS.HEURES
    }


  def parseSign: Parser[Constants.SIGNS] =
    ("HIGHER" | "LESS"| "EQUALS" | "CHANGE" ) ^^ {
      case "HIGHER" => Constants.SIGNS.HIGHER
      case "LESS" => Constants.SIGNS.LESS
      case "EQUALS" => Constants.SIGNS.EQUALS
      case "CHANGE" => Constants.SIGNS.CHANGE
    }


  def inputValue  = ident ^^ {
    case s => s
  }

  def  parserArduinoECAEvent: Parser[ArduinoECAEventPredicate]   = inputValue ~ "IS" ~ parseSign ~ value ^^  {
    case     inputValue  ~ _ ~ sign ~ v =>  new ArduinoECAEventPredicate(inputValue,sign,v.toFloat)
  }


  def action  =ident ^^ {
    case s => s
  }


  def arduinoECARule: Parser[ArduinoECARule] =
    "IF" ~ rep1sep (parserArduinoECAEvent , "AND") ~ "DO"  ~  action  ^^  {
      case   _ ~ events  ~ _ ~ action =>    new ArduinoECARule(events,action)
    }

  // TEMPORA + ECA RULE
  def parserArduinoFuzzyTemporalRule : Parser[ArduinoECATemporal] = parserarduinoFuzzyTemporalPredicate ~ arduinoECARule ^^{
    case t  ~ t2 =>   new ArduinoECATemporal(t,t2)
  }

  // SINCE 10 MINUTES CHECK
  def parserarduinoFuzzyTemporalPredicate :Parser[ArduinoECATemporalPredicate] =  parseOperator  ~ value ~ parseUnit ~ "CHECK"  ^^ {
    case t ~ d ~ u ~ _ =>   new ArduinoECATemporalPredicate(t,d.toInt,u)
  }


  //  global
  def requestParse: Parser[ArduinoECARules] =
    rep1sep(arduinoECARule | parserArduinoFuzzyTemporalRule, ";") ^^ {
      case rules: List[ArduinoECARule]=> new  ArduinoECARules(rules)
    }



  def   parseRules(chaine : String) :ArduinoECARules = {

    requestParse(new lexical.Scanner(chaine)) match {
      case Success(_rules, _) => _rules
      case Failure(msg,_) => throw new ArduinoException("Bad syntax: "+msg)
      case Error(msg, _) =>  throw new ArduinoException("Bad syntax: "+msg)
    }

  }

}