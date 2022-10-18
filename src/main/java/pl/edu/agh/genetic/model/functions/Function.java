package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.ArrayList;
import java.util.List;

public abstract class Function {

  public static int NUMBER_OF_PARAMETERS = -1;
  protected int numberOfExecutions = -1;
  protected List<Constraint> variablesConstraints = new ArrayList<>();

  public abstract double calculate(Double... parameters);

  public List<Constraint> getVariablesConstraints() {
    return variablesConstraints;
  }

  public int getRequiredNumberOfParameters() {
    return NUMBER_OF_PARAMETERS;
  }

  public int getNumberOfExecutions() {
    return numberOfExecutions;
  }

  public abstract Double getFitness(Double result);

  double preventNotDefinedValues(double result) {
    if (Double.POSITIVE_INFINITY == result) {
      return Double.MAX_VALUE;
    } else if (Double.NEGATIVE_INFINITY == result) {
      return Double.MAX_VALUE;
    } else if (Double.isNaN(result)) {
      return Double.MAX_VALUE;
    } else {
      return result;
    }
  }

  protected void validateNumberOfParameters(Double... parameters) {
    if (parameters.length != NUMBER_OF_PARAMETERS) {
      // TODO exception
      System.out.println("Liczba przekazanych parametrow i okreslona liczba nie jest taka sama");
    }
  }
}
