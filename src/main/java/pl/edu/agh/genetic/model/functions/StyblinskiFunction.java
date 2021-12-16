package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class StyblinskiFunction extends Function implements GradientFunction {

  public StyblinskiFunction() {
    variablesConstraints =
        List.of(
            new Constraint(-5, 5),
            new Constraint(-5, 5),
            new Constraint(-5, 5),
            new Constraint(-5, 5));
    numberOfExecutions = 0;
    NUMBER_OF_PARAMETERS = 4;
  }

  @Override
  public double calculateResult(Double... parameters) {
    validateNumberOfParameters(parameters);
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i] > variablesConstraints.get(i).getUpperBound()
          || parameters[i] < variablesConstraints.get(i).getLowerBound()) {
        result = Double.MAX_VALUE;
        return result;
      }
    }

    double x = parameters[0];
    double y = parameters[1];
    double z = parameters[2];
    double q = parameters[3];

    double functionValue =
        ((pow(x, 4) - (16 * pow(x, 2)) + (5 * x))
                + (pow(y, 4) - (16 * pow(y, 2)) + (5 * y))
                + (pow(z, 4) - (16 * pow(z, 2)) + (5 * z))
                + (pow(q, 4) - (16 * pow(q, 2)) + (5 * q)))
            / 2;
    numberOfExecutions++;
    result = preventNotDefinedValues(functionValue);
    return result;
  }

  @Override
  public Double[] calculateGradient(Double... parameters) {
    double x = parameters[0];
    double y = parameters[1];
    double z = parameters[2];
    double q = parameters[3];
    double dx = preventNotDefinedValues((5.0 / 2.0) - (16 * x) + 2 * pow(x, 3));
    double dy = preventNotDefinedValues((5.0 / 2.0) - (16 * y) + 2 * pow(y, 3));
    double dz = preventNotDefinedValues((5.0 / 2.0) - (16 * z) + 2 * pow(z, 3));
    double dq = preventNotDefinedValues((5.0 / 2.0) - (16 * q) + 2 * pow(q, 3));
    return new Double[] {dx, dy, dz, dq};
  }

  @Override
  public Double getFitness() {
    return 1000 / abs( (-1 * result) - (NUMBER_OF_PARAMETERS * 39.166165));
  }

}
