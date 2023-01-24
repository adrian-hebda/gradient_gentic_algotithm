package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class McCormicFunction extends Function implements GradientFunction {

    public McCormicFunction() {
        variablesConstraints = List.of(new Constraint(-1.5, 4), new Constraint(-3, 4));
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
        double functionValue =
                sin(x + y) + pow(x - y, 2) - 1.5 * x + 2.5 * y + 1;
        numberOfExecutions++;

        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double getFitness(Double result) {
        return 1 / result;
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

        double dx = (calculate(xAddPrecision, y) - calculate(xSubPrecision, y)) / xAddPrecision - xSubPrecision;
        double dy = (calculate(x, yAddPrecision) - calculate(x, ySubPrecision)) / yAddPrecision - ySubPrecision;

        return new Double[]{dx, dy};

    }
}
