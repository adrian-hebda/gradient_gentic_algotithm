package pl.edu.agh.genetic.model;

import lombok.Getter;
import pl.edu.agh.genetic.exceptions.LowerBoundGreaterThanUpperBoundException;

@Getter
public class Constraint {
    private final Double upperBound;
    private final Double lowerBound;

    public Constraint(Double lowerBound, Double upperBound) {
        validateConstraintValues(lowerBound, upperBound);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    private void validateConstraintValues(Double lowerBound, Double upperBound) {
        if (lowerBound > upperBound) {
            throw new LowerBoundGreaterThanUpperBoundException(lowerBound, upperBound);
        }
    }
}
