package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Constraint;

import java.util.stream.IntStream;

import static java.lang.Math.*;

public class Rastrigin3GradientFunction extends Function implements GradientFunction {

    public Rastrigin3GradientFunction() {
        NUMBER_OF_PARAMETERS = 3;
        IntStream.range(0, NUMBER_OF_PARAMETERS)
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
        double x = parameters[0];
        double y = parameters[1];
        double z = parameters[2];

        double functionValue =
                30
                        + pow(x, 2)
                        - 10 * cos(2 * PI * x)
                        + pow(y, 2)
                        - 10 * cos(2 * PI * y)
                        + pow(z, 2)
                        - 10 * cos(2 * PI * z);
        numberOfExecutions++;
        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double[] calculateGradient(Double... parameters) {
        double x = parameters[0];
        double y = parameters[1];
        double z = parameters[2];
        double dx = 2 * (x + 10 * PI * sin(2 * PI * x));
        double dy = 2 * (y + 10 * PI * sin(2 * PI * y));
        double dz = 2 * (z + 10 * sin(2 * PI * z));
        return new Double[]{dx, dy, dz};
    }

    @Override
    public Double getFitness(Double result) {
        return 1 / abs(result);
    }


}
