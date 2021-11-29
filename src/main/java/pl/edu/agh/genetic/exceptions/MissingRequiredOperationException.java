package pl.edu.agh.genetic.exceptions;

import java.text.MessageFormat;

public class MissingRequiredOperationException extends RuntimeException {
  private static final String MESSAGE = "Missing required {0} operation.";

  public MissingRequiredOperationException(String missingOperation) {
    super(MessageFormat.format(MESSAGE, missingOperation));
  }
}
