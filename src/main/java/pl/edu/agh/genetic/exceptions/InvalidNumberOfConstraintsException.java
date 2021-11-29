package pl.edu.agh.genetic.exceptions;

import java.text.MessageFormat;

public class InvalidNumberOfConstraintsException extends RuntimeException {
  private static final String MESSAGE =
      "Amount of constraints ({0}) is different than amount of numbers ({1}) represented by binary sets.";

  public InvalidNumberOfConstraintsException(int numberOfConstraints, int numberOfDoublesCoded) {
    super(MessageFormat.format(MESSAGE, numberOfConstraints, numberOfDoublesCoded));
  }
}
