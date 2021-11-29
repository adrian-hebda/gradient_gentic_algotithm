package pl.edu.agh.genetic.exceptions;

import java.text.MessageFormat;

public class LowerBoundGreaterThanUpperBoundException extends RuntimeException {
  private static final String MESSAGE = "Lower bound ({0}) is greater than upper bound ({1}).";

  public LowerBoundGreaterThanUpperBoundException(Double lowerBound, Double upperBound) {
    super(MessageFormat.format(MESSAGE, lowerBound, upperBound));
  }
}
