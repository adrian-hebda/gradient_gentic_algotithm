package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.*;

public class EasomFunction extends Function implements GradientFunction {

    public EasomFunction() {
        variablesConstraints = List.of(new Constraint(-100.0, 100.0), new Constraint(-100.0, 100.0));
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
        Double functionValue =
                -cos(x) * cos(y) * exp(-(pow(x - PI, 2) + pow(y - PI, 2)));
        numberOfExecutions++;

        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double getFitness(Double result) {
        return 10000 / (result + 1);
    }

    @Override
    public Double[] calculateGradient(Double... parameters) {
        Double x = parameters[0];
        Double y = parameters[1];
        Double precision = 0.01;
        Double difference = 2 * precision;
        Double xSubPrecision = x - precision;
        Double ySubPrecision = y - precision;
        Double xAddPrecision = x + precision;
        Double yAddPrecision = y + precision;

        Double dx = (calculate(xSubPrecision, y) - calculate(xAddPrecision, y)) / difference;
        Double dy = (calculate(x, ySubPrecision) - calculate(x, yAddPrecision)) / difference;

        return new Double[]{dx, dy};

    }
}
