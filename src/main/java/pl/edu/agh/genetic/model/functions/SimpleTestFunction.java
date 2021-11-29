package pl.edu.agh.genetic.model.functions;

import lombok.Getter;

@Getter
public class SimpleTestFunction extends Function {

  public static final int NUMBER_OF_PARAMETERS = 2;
  private int numberOfExecutions = 0;

  @Override
  public double calculateResult(Double... parameters) {
    validateNumberOfParameters(parameters);
    Double x = parameters[0];
    Double y = parameters[1];
    if (x > 4.5 || x < -4.5 || y > 4.5 || y < -4.5) {
      return Double.MAX_VALUE;
    }

    double result = x * x + y * y;
    numberOfExecutions++;

    return preventNotDefinedValues(result);
  }

  @Override
  public int getRequiredNumberOfParameters() {
    return NUMBER_OF_PARAMETERS;
  }

  private void validateNumberOfParameters(Double[] parameters) {
    if (parameters.length != NUMBER_OF_PARAMETERS) {
      // TODO exception
      System.out.println("Liczba przekazanych parametrow i okreslona liczba nie jest taka sama");
    }
  }
}
