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
    public Double calculate(Double... parameters) {
        validateNumberOfParameters(parameters);
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

        Double functionValue = x * x + y * y;
        numberOfExecutions++;
        result = preventNotDefinedValues(functionValue);
        return result;
    }

    @Override
    public Double[] calculateGradient(Double... parameters) {
        Double dx = preventNotDefinedValues(2 * parameters[0]);
        Double dy = preventNotDefinedValues(2 * parameters[1]);

        return new Double[]{dx, dy};
    }

    @Override
    public Double getFitness(Double result) {
        return 1 / result;
    }

}
