package pl.edu.agh.genetic.model.stop_conditions;

import lombok.AllArgsConstructor;
import pl.edu.agh.genetic.model.AlgorithmMetadata;

@AllArgsConstructor
public class AssumedPrecisionReachedStopCondition implements StopCondition{

    private final double precision;

    @Override
    public boolean isStopConditionMet(AlgorithmMetadata algorithm) {
        if (algorithm.getBestChromosome().getFunctionValue() < precision){
            return true;
        }
        else return false;
    }
}
