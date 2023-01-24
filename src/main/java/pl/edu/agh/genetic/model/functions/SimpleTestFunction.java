package pl.edu.agh.genetic.model.functions;

import lombok.Getter;
import pl.edu.agh.genetic.model.Constraint;

import java.util.List;

@Getter
public class SimpleTestFunction extends Function implements GradientFunction {

    public SimpleTestFunction() {
        variablesConstraints = List.of(new Constraint(-4.5, 4.5), new Constraint(-4.5, 4.5));
        numberOfExecutions = 0;
        NUMBER_OF_PARAMETERS = 2;
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
            result = Double.MAX_VALUE;
            return result;
        }

        double functionValue = x * x + y * y;
        numberOfExecutions++;
        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double[] calculateGradient(Double... parameters) {
        double dx = preventNotDefinedValues(2 * parameters[0]);
        double dy = preventNotDefinedValues(2 * parameters[1]);

        return new Double[]{dx, dy};
    }

    @Override
    public Double getFitness(Double result) {
        return 1 / result;
    }

}
