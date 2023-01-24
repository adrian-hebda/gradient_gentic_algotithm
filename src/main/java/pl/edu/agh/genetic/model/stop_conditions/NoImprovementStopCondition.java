package pl.edu.agh.genetic.model.stop_conditions;

import pl.edu.agh.genetic.model.AlgorithmMetadata;

public class NoImprovementStopCondition implements StopCondition {

    private final int maxNumberOfGenerationsWithoutImprovement;

    public NoImprovementStopCondition(int maxNumberOfGenerationsWithoutImprovement) {
        this.maxNumberOfGenerationsWithoutImprovement = maxNumberOfGenerationsWithoutImprovement;
    }

    @Override
    public boolean isStopConditionMet(AlgorithmMetadata algorithm) {
        return algorithm.getNumberOfGenerationsWithoutImprovement()
                >= maxNumberOfGenerationsWithoutImprovement;
    }
}
