package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.*;

public class EasomFunction extends Function implements GradientFunction {

    public EasomFunction(){
        variablesConstraints = List.of(new Constraint(-100, 100), new Constraint(-100, 100));
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
                -cos(x) * cos(y) * exp(-(pow(x - PI, 2) + pow(y - PI,2)));
        numberOfExecutions++;

        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double getFitness(Double result) {
        return 10000/(result + 1);
    }

    @Override
    public Double[] calculateGradient(Double... parameters) {
        double x = parameters[0];
        double y = parameters[1];
        double precision = 0.01;
        double difference = 2*precision;
        double xSubPrecision = x - precision;
        double ySubPrecision = y - precision;
        double xAddPrecision = x + precision;
        double yAddPrecision = y + precision;

        double dx = (calculate(xSubPrecision, y) - calculate(xAddPrecision, y)) / difference;
        double dy = (calculate(x, ySubPrecision) - calculate(x, yAddPrecision)) / difference;

        return new Double[]{dx, dy};

    }
}
