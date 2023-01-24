package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.pow;

public class CamelFunction extends Function implements GradientFunction {
    public CamelFunction() {
        variablesConstraints = List.of(new Constraint(-5.0, 5.0), new Constraint(-5.0, 5.0));
        numberOfExecutions = 0;
        NUMBER_OF_PARAMETERS = 2;
    }

    public static void main(String[] args) {
        System.out.println(
                new CamelFunction().calculate(0.0, 0.0));
    }

    @Override
    public double calculate(Double... parameters) {
        validateNumberOfParameters(parameters);
        double result;

        Double x = parameters[0];
        Double y = parameters[1];
        if (x > variablesConstraints.get(0).getUpperBound()
                || x < variablesConstraints.get(0).getLowerBound()
                || y > variablesConstraints.get(1).getUpperBound()
                || y < variablesConstraints.get(1).getLowerBound()) {
            return Double.MAX_VALUE;
        }

        double functionValue = (2.0 * pow(x, 2)) - (1.05 * pow(x, 4)) + (pow(x, 6) / 6.0) + (x * y) + pow(y, 2);
        numberOfExecutions++;
        result = preventNotDefinedValues(functionValue);
        return result;
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

    @Override
    public Double getFitness(Double result) {
        return 1 / result;
    }
}
