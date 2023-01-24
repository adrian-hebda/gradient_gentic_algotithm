package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.pow;

public class GoldsteinFunction extends Function implements GradientFunction {

    public GoldsteinFunction() {
        variablesConstraints = List.of(new Constraint(-2, 2), new Constraint(-2, 2));
        numberOfExecutions = 0;
        NUMBER_OF_PARAMETERS = 2;
    }

    @Override
    public double calculate(Double... parameters) {
        double result;
        Double x = parameters[0];
        Double y = parameters[1];
        if (x > variablesConstraints.get(0).getUpperBound()
                || x < variablesConstraints.get(0).getLowerBound()
                || y > variablesConstraints.get(1).getUpperBound()
                || y < variablesConstraints.get(1).getLowerBound()) {
            result = Double.MAX_VALUE;
            return result;
        }
        double a =
                1
                        + (pow((x + y + 1), 2)
                        * (19 - (14 * x) + (3 * pow(x, 2)) - (14 * y) + (6 * x * y) + (3 * pow(y, 2))));
        double b =
                30
                        + (pow(((2 * x) - (3 * y)), 2)
                        * (18 - (32 * x) + (12 * pow(x, 2)) + (48 * y) - (36 * x * y) + (27 * pow(y, 2))));
        double functionValue = a * b;

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
        double x = parameters[0];
        double y = parameters[1];
        double precision = 0.00001;
        double xSubPrecision = x - precision;
        double ySubPrecision = y - precision;
        double xAddPrecision = x + precision;
        double yAddPrecision = y + precision;

        double dx =
                (calculate(xAddPrecision, y) - calculate(xSubPrecision, y)) / xAddPrecision - xSubPrecision;
        double dy =
                (calculate(x, yAddPrecision) - calculate(x, ySubPrecision)) / yAddPrecision - ySubPrecision;

        return new Double[]{dx, dy};
    }
}
