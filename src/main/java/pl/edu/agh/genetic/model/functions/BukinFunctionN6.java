package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

public class BukinFunctionN6 extends Function implements GradientFunction {
    public BukinFunctionN6() {
        variablesConstraints = List.of(new Constraint(-15.0, -5.0), new Constraint(-3.0, 3.0));
        numberOfExecutions = 0;
        NUMBER_OF_PARAMETERS = 2;
    }

    @Override
    public Double calculate(Double... parameters) {
        validateNumberOfParameters(parameters);
        Double result;

        Double x = parameters[0];
        Double y = parameters[1];
        if (x > variablesConstraints.get(0).getUpperBound()
                || x < variablesConstraints.get(0).getLowerBound()
                || y > variablesConstraints.get(1).getUpperBound()
                || y < variablesConstraints.get(1).getLowerBound()) {
            return Double.MAX_VALUE;
        }

        Double term1 = 100 * Math.sqrt(Math.abs(y - 0.01 * x * x));
        Double term2 = 0.01 * Math.abs(x + 10);
        Double functionValue = term1 + term2;
        numberOfExecutions++;
        result = preventNotDefinedValues(functionValue);
        return result;
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

    @Override
    public Double getFitness(Double result) {
        return 1 / result;
    }
}
