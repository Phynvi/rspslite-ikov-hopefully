package net.rspslite.rsps.hooks;

import java.util.Map;
import java.util.HashMap;
import javassist.CtClass;
import javassist.CtField;

public class Hook {

  private String name;
  private Matching matching;
  private FieldFinder[] fieldFinders;
  private Injections injections;

  public String getName() {
    return name;
  }

  public Matching getMatching() {
    return matching;
  }

  public FieldFinder[] getFieldFinders() {
    return fieldFinders;
  }

  public Injections getInjections() {
    return injections;
  }

  public boolean isMatch(CtClass cc) {
    return matching.isMatch(cc);
  }

  public void applyTo(CtClass cc) { // assumes the class is a match
    for (String interfaceName : getInjections().getInterfaces()) {
      System.out.print(interfaceName + " ");
    }
    System.out.println("-> " + cc.getName());

    Map<String, CtField> fieldMap = new HashMap<>();

    for (FieldFinder finder : getFieldFinders()) {
      finder.find(cc, getMatching().getFields(), fieldMap);
    }
  }

}
