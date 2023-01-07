package pl.edu.agh.genetic.model.stop_conditions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.genetic.model.AlgorithmMetadata;

@RequiredArgsConstructor
public class FitnessFunctionIsInfinity implements StopCondition {
  @Override
  public boolean isStopConditionMet(AlgorithmMetadata algorithm) {
    if (algorithm.getBestChromosome() == null) {
      System.out.println("first iteration");
      return false;
    }
    return algorithm.getBestChromosome() != null
        && algorithm.getBestChromosome().getFitness() == Double.POSITIVE_INFINITY;
  }
}
