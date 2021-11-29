package pl.edu.agh.genetic.model.stop_conditions;

import pl.edu.agh.genetic.model.AlgorithmMetadata;

public class FitnessFunctionIsInfinity implements StopCondition {
  @Override
  public boolean isStopConditionMet(AlgorithmMetadata algorithm) {
    return algorithm.getBestChromosome() != null
        && algorithm.getBestChromosome().getFitness() == Double.POSITIVE_INFINITY;
  }
}
