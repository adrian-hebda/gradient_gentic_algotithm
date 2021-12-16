package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.pow;

public class BealeFunction extends Function implements GradientFunction {

  public BealeFunction() {
    variablesConstraints = List.of(new Constraint(-4.5, 4.5), new Constraint(-4.5, 4.5));
    numberOfExecutions = 0;
    NUMBER_OF_PARAMETERS = 2;
  }

  @Override
  public double calculateResult(Double... parameters) {
    Double x = parameters[0];
    Double y = parameters[1];
    if (x > variablesConstraints.get(0).getUpperBound()
        || x < variablesConstraints.get(0).getLowerBound()
        || y > variablesConstraints.get(1).getUpperBound()
        || y < variablesConstraints.get(1).getLowerBound()) {
      result = Double.MAX_VALUE;
      return result;
    }
    double functionValue =
        pow(1.5 - x + x * y, 2)
            + pow(2.25 - x + x * pow(y, 2), 2)
            + pow(2.625 - x + x * pow(y, 3), 2);
    numberOfExecutions++;
    result = preventNotDefinedValues(functionValue);
    return result;
  }

  @Override
  public int getRequiredNumberOfParameters() {
    return NUMBER_OF_PARAMETERS;
  }

  @Override
  public int getNumberOfExecutions() {
    return numberOfExecutions;
  }

  @Override
  public Double getFitness() {
    return 1000 / result;
  }

  @Override
  public Double[] calculateGradient(Double... parameters) {
    double x = parameters[0];
    double y = parameters[1];
    double dx =
        preventNotDefinedValues(
            2 * x * (pow(y, 6) + pow(y, 4) - 2 * pow(y, 3) - pow(y, 2) - 2 * y + 3)
                + 5.25 * pow(y, 3)
                + 4.5 * pow(y, 2)
                + 3 * y
                - 12.75);
    double dy =
        preventNotDefinedValues(
            6
                * x
                * (x
                        * (pow(y, 5)
                            + (2.0 / 3.0) * pow(y, 3)
                            - pow(y, 2)
                            - (1.0 / 3.0) * y
                            - (1.0 / 3.0))
                    + 2.625 * pow(y, 2)
                    + 1.5 * y
                    + 0.5));

    return new Double[] {dx, dy};
  }
}
