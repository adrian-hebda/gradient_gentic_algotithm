package pl.edu.agh.genetic.model.stop_conditions;

import lombok.AllArgsConstructor;
import pl.edu.agh.genetic.model.AlgorithmMetadata;

@AllArgsConstructor
public class AssumedPrecisionReachedStopCondition implements StopCondition {

    private final Double precision;

    @Override
    public boolean isStopConditionMet(AlgorithmMetadata algorithm) {
        return algorithm.getBestChromosome().getFunctionValue() < precision;
    }
}
