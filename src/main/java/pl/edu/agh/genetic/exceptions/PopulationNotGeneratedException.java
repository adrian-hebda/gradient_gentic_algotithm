package pl.edu.agh.genetic.exceptions;

public class PopulationNotGeneratedException extends RuntimeException{
    private static final String MESSAGE = "Initial population not generated";

    public PopulationNotGeneratedException() {
        super(MESSAGE);
    }
}
