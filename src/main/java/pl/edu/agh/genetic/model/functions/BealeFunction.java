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
    public Double calculate(Double... parameters) {
        Double result;
        Double x = parameters[0];
        Double y = parameters[1];
        if (x > variablesConstraints.get(0).getUpperBound()
                || x < variablesConstraints.get(0).getLowerBound()
                || y > variablesConstraints.get(1).getUpperBound()
                || y < variablesConstraints.get(1).getLowerBound()) {
            return Double.MAX_VALUE;
        }
        Double functionValue =
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
    public Double getFitness(Double result) {
        return 1 / result;
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

        Double dx = (calculate(xAddPrecision, y) - calculate(xSubPrecision, y)) / xAddPrecision - xSubPrecision;
        Double dy = (calculate(x, yAddPrecision) - calculate(x, ySubPrecision)) / yAddPrecision - ySubPrecision;

        return new Double[]{dx, dy};
    }
}
