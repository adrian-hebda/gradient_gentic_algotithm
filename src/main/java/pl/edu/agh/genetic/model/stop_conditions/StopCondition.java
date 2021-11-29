package pl.edu.agh.genetic.model.stop_conditions;

import pl.edu.agh.genetic.model.AlgorithmMetadata;

public interface StopCondition {
  boolean isStopConditionMet(AlgorithmMetadata algorithm);
}
