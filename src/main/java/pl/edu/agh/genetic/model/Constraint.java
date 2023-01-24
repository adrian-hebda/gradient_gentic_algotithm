package pl.edu.agh.genetic.model;

import lombok.Getter;
import pl.edu.agh.genetic.exceptions.LowerBoundGreaterThanUpperBoundException;

@Getter
public class Constraint {
    private final double upperBound;
    private final double lowerBound;

    public Constraint(double lowerBound, double upperBound) {
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
