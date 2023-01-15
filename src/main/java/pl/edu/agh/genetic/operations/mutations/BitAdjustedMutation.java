package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;

import java.util.BitSet;

public class BitAdjustedMutation implements Mutation {
    private final double fixedMutationRate;

    public BitAdjustedMutation(double fixedMutationRate) {
        this.fixedMutationRate = fixedMutationRate;
    }

    @Override
    public void performMutation(Population population) {
        population.getChromosomes().stream()
                .flatMap(chromosome -> chromosome.getCodedChromosome().stream())
                .forEach(this::flipMutationConditionFulfilled);
    }

    private void flipMutationConditionFulfilled(BitSet bitSet) {
        for (int i = 0; i < bitSet.size(); i++) {
            double random = Math.random();
            double bitSignificanceCoefficient = 1.0 / (i + 1.0);
            double mutationRate = (fixedMutationRate + (fixedMutationRate * bitSignificanceCoefficient)) * 0.5;
            if (random < mutationRate) {
                flip(bitSet, i);
            }
        }
    }
}
