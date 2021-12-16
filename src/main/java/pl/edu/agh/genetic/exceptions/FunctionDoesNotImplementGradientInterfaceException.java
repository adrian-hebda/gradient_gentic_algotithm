package pl.edu.agh.genetic.exceptions;

public class FunctionDoesNotImplementGradientInterfaceException extends RuntimeException{
    private static final String MESSAGE = "Function does not implement Gradient interface";

    public FunctionDoesNotImplementGradientInterfaceException() {
        super(MESSAGE);
    }
}
