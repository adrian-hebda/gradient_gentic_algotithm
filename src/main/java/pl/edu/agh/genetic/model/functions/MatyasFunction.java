package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.pow;

public class MatyasFunction extends Function implements GradientFunction {

    public MatyasFunction() {
        variablesConstraints = List.of(new Constraint(-10, 10), new Constraint(-10, 10));
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
                (0.26 * (pow(x, 2) + pow(y, 2))) - (0.48 * x * y);
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
