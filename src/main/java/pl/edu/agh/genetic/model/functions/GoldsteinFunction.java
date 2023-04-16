package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.pow;

public class GoldsteinFunction extends Function implements GradientFunction {

    public GoldsteinFunction() {
        variablesConstraints = List.of(new Constraint(-2.0, 2.0), new Constraint(-2.0, 2.0));
        numberOfExecutions = 0;
        NUMBER_OF_PARAMETERS = 2;
    }

    @Override
    public Double calculate(Double... parameters) {
        Double result;
        Double x = parameters[0];
        Double y = parameters[1];
        if (x > variablesConstraints.get(0).getUpperBound()
                || x < variablesConstraints.get(0).getLowerBound()
                || y > variablesConstraints.get(1).getUpperBound()
                || y < variablesConstraints.get(1).getLowerBound()) {
            result = Double.MAX_VALUE;
            return result;
        }
        Double a =
                1
                        + (pow((x + y + 1), 2)
                        * (19 - (14 * x) + (3 * pow(x, 2)) - (14 * y) + (6 * x * y) + (3 * pow(y, 2))));
        Double b =
                30
                        + (pow(((2 * x) - (3 * y)), 2)
                        * (18 - (32 * x) + (12 * pow(x, 2)) + (48 * y) - (36 * x * y) + (27 * pow(y, 2))));
        Double functionValue = a * b;

        numberOfExecutions++;

        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double getFitness(Double result) {
        return 1 / (result - 3);
    }

    @Override
    public Double[] calculateGradient(Double... parameters) {
        Double x = parameters[0];
        Double y = parameters[1];
        Double precision = 0.00001;
        Double xSubPrecision = x - precision;
        Double ySubPrecision = y - precision;
        Double xAddPrecision = x + precision;
        Double yAddPrecision = y + precision;

        Double dx =
                (calculate(xAddPrecision, y) - calculate(xSubPrecision, y)) / xAddPrecision - xSubPrecision;
        Double dy =
                (calculate(x, yAddPrecision) - calculate(x, ySubPrecision)) / yAddPrecision - ySubPrecision;

        return new Double[]{dx, dy};
    }
}
