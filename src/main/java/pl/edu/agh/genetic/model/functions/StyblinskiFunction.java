package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class StyblinskiFunction extends Function implements GradientFunction {

    public StyblinskiFunction() {
        variablesConstraints =
                List.of(
                        new Constraint(-5.0, 5.0),
                        new Constraint(-5.0, 5.0),
                        new Constraint(-5.0, 5.0),
                        new Constraint(-5.0, 5.0));
        numberOfExecutions = 0;
        NUMBER_OF_PARAMETERS = 4;
    }

    @Override
    public Double calculate(Double... parameters) {
        validateNumberOfParameters(parameters);
        Double result;

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] > variablesConstraints.get(i).getUpperBound()
                    || parameters[i] < variablesConstraints.get(i).getLowerBound()) {
                result = Double.MAX_VALUE;
                return result;
            }
        }

        Double x = parameters[0];
        Double y = parameters[1];
        Double z = parameters[2];
        Double q = parameters[3];

        Double functionValue =
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
        Double x = parameters[0];
        Double y = parameters[1];
        Double z = parameters[2];
        Double q = parameters[3];
        Double dx = preventNotDefinedValues((5.0 / 2.0) - (16 * x) + 2 * pow(x, 3));
        Double dy = preventNotDefinedValues((5.0 / 2.0) - (16 * y) + 2 * pow(y, 3));
        Double dz = preventNotDefinedValues((5.0 / 2.0) - (16 * z) + 2 * pow(z, 3));
        Double dq = preventNotDefinedValues((5.0 / 2.0) - (16 * q) + 2 * pow(q, 3));
        return new Double[]{dx, dy, dz, dq};
    }

    @Override
    public Double getFitness(Double result) {
        return pow(1e5 / abs((-1.0 * result) - (NUMBER_OF_PARAMETERS * 39.166165)), 3);
    }

}
