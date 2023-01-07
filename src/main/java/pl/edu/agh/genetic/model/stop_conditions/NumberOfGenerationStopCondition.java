package pl.edu.agh.genetic.model.stop_conditions;

import pl.edu.agh.genetic.model.AlgorithmMetadata;

public class NumberOfGenerationStopCondition implements StopCondition {

  private int maxNumberOfGenerations;

  public NumberOfGenerationStopCondition(int maxNumberOfGenerations) {
    this.maxNumberOfGenerations = maxNumberOfGenerations;
  }

  @Override
  public boolean isStopConditionMet(AlgorithmMetadata metadata) {
    return metadata.getNumberOfGenerations() >= maxNumberOfGenerations;
  }
}
