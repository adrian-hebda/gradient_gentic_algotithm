package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;

import java.util.BitSet;

public class SimpleMutation implements Mutation {

    private final double fixedMutationRate;

    public SimpleMutation(double fixedMutationRate) {
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
            if (Math.random() < fixedMutationRate) {
                flip(bitSet, i);
            }
        }
    }
}
