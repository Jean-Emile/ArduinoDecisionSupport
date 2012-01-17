package org.daum.ArduinoDecisionSupport

import Parser.FuzzyLogic.{ArduinoFuzzyRules, ArduinoFuzzyRule, ArduinoFuzzyPredicate}
import util.parsing.combinator.syntactical.StandardTokenParsers
import collection.JavaConversions._
import Utils.ArduinoException

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 13/01/12
 * Time: 10:10
 */

class ParserFuzzyLogic extends StandardTokenParsers{

  lexical.delimiters ++= List(";")
  lexical.reserved +=("IF", "THEN","IS","AND")

  def fuzzyDomain  = ident ^^ {
    case s => s
  }

  def fuzzyTerm  =ident ^^ {
    case s => s
  }

  // FUZZYDOMAIN IS FUZZY TERM
  def  parserArduinoFuzzyPredicate: Parser[ArduinoFuzzyPredicate]   = fuzzyDomain ~ "IS" ~ fuzzyTerm ^^  {
    case     d  ~ _ ~ t =>  new ArduinoFuzzyPredicate(d,t)
  }

  // FUZZY RULE
  def arduinoFuzzyRule: Parser[ArduinoFuzzyRule] =
    "IF" ~ rep1sep (parserArduinoFuzzyPredicate, "AND") ~ "THEN" ~ rep1sep (parserArduinoFuzzyPredicate, "AND")  ^^  {
      case   _ ~ antecedent  ~ _ ~ outcome =>    new ArduinoFuzzyRule(antecedent,outcome)
    }


  //  global
  def requestParse: Parser[ArduinoFuzzyRules] =
    rep1sep(arduinoFuzzyRule , ";") ^^ {
      case rules: List[ArduinoFuzzyRule]=> new  ArduinoFuzzyRules(rules)
    }

  def   parseRules(chaine : String) :ArduinoFuzzyRules = {

    requestParse(new lexical.Scanner(chaine)) match {
      case Success(_rules, _) => _rules
      case Failure(msg,_) => throw new ArduinoException("Bad syntax: "+msg)
      case Error(msg, _) =>  throw new ArduinoException("Bad syntax: "+msg)
    }


  }
}