package pl.edu.agh.genetic.model.functions;

import static java.lang.Math.pow;

public class BealeFunction extends Function {

    public static final int NUMBER_OF_PARAMETERS = 2;
    private int numberOfExecutions = 0;

    @Override
    public double calculateResult(Double... parameters) {
        Double x = parameters[0];
        Double y = parameters[1];
        if(x > 4.5 || x < -4.5 || y > 4.5 || y < -4.5){
            return Double.MAX_VALUE;
        }
        double result = pow(1.5 - x + x*y, 2) + pow(2.25 - x + x*pow(y, 2), 2) + pow(2.625 - x + x*pow(y, 3), 2);
        numberOfExecutions++;
        return preventNotDefinedValues(result);
    }

    @Override
    public int getRequiredNumberOfParameters() {
        return NUMBER_OF_PARAMETERS;
    }

    @Override
    public int getNumberOfExecutions() {
        return numberOfExecutions;
    }
}
