package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class HimmelblauFunction extends Function implements GradientFunction {

  public HimmelblauFunction() {
    variablesConstraints = List.of(new Constraint(-5.0, 5.0), new Constraint(-5.0, 5.0));
    numberOfExecutions = 0;
    NUMBER_OF_PARAMETERS = 2;
  }

  @Override
  public double calculateResult(Double... parameters) {
    validateNumberOfParameters(parameters);
    Double x = parameters[0];
    Double y = parameters[1];
    if (x > variablesConstraints.get(0).getUpperBound()
        || x < variablesConstraints.get(0).getLowerBound()
        || y > variablesConstraints.get(1).getUpperBound()
        || y < variablesConstraints.get(1).getLowerBound()) {
      result = Double.MAX_VALUE;
      return abs(result);
    }

    double functionValue = pow((x * x) + y - 11, 2) + pow(x + (y * y) - 7, 2);
    numberOfExecutions++;
    result = preventNotDefinedValues(functionValue);
    return result;
  }

  @Override
  public Double[] calculateGradient(Double... parameters) {
    double x = parameters[0];
    double y = parameters[1];
    double dx =  preventNotDefinedValues(2 * (-7 + x + pow(y, 2) + 2 * x * (-11 + pow(x, 2) + y)));
    double dy =  preventNotDefinedValues(2 * (-11 + pow(x, 2) + y + 2 * y * (-7 + x + pow(y, 2))));
    return new Double[] {dx, dy};
  }

  @Override
  public Double getFitness() {
    return 1000 / abs(result);
  }

}
