package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.stream.IntStream;

import static java.lang.Math.*;

public class RastriginFunction extends Function {

    public RastriginFunction(int numberOfParameters) {
        NUMBER_OF_PARAMETERS = numberOfParameters;
        IntStream.range(0, numberOfParameters)
                .forEach(i -> variablesConstraints.add(new Constraint(-5.12, 5.12)));
        numberOfExecutions = 0;
    }

    @Override
    public double calculate(Double... parameters) {
        validateNumberOfParameters(parameters);
        double result;

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] > variablesConstraints.get(i).getUpperBound()
                    || parameters[i] < variablesConstraints.get(i).getLowerBound()) {
                result = Double.MAX_VALUE;
                return result;
            }
        }

        double functionValue = parameters.length * 10.0 + calculateSum(parameters);
        numberOfExecutions++;
        result = preventNotDefinedValues(functionValue);
        return result;
    }

    private double calculateSum(Double[] parameters) {
        double sum = 0;
        for (var parameter : parameters) {
            sum += pow(parameter, 2) - 10 * cos(2 * Math.PI * parameter);
        }
        return sum;
    }

    @Override
    public Double getFitness(Double result) {
        return 1000 / abs(result);
    }

}
