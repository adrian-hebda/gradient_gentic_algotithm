package pl.edu.agh.genetic.model.functions;


public abstract class Function {

    public static final int NUMBER_OF_PARAMETERS = -1;
    private int numberOfExecutions = -1;

    public double calculateResult(Double... parameters){
        return Double.NaN;
    }
    public int getRequiredNumberOfParameters(){
        return NUMBER_OF_PARAMETERS;
    }
    public int getNumberOfExecutions(){
        return numberOfExecutions;
    }

    double preventNotDefinedValues(double result) {
        if (Double.POSITIVE_INFINITY == result) {
            return Double.MAX_VALUE;
        } else if (Double.NEGATIVE_INFINITY == result) {
            return Double.MIN_VALUE;
        } else if (Double.isNaN(result)) {
            return Double.MAX_VALUE;
        } else {
            return result;
        }
    }

}
