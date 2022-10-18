package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

import static java.lang.Math.*;

public class McCormicFunction extends Function implements GradientFunction {

    public McCormicFunction(){
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
                sin(x+y) + pow(x-y,2) -1.5*x + 2.5*y + 1;
        numberOfExecutions++;

        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double getFitness(Double result) {
        return pow(10e4/result + 1.9133,2);
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
