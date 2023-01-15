package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;

import java.util.BitSet;

import static java.lang.Math.*;

public class GradientMutation implements Mutation {
    private final double fixedMutationRate;

    public GradientMutation(double fixedMutationRate) {
        this.fixedMutationRate = fixedMutationRate;
    }

    @Override
    public void performMutation(Population population) {
        for (var chromosome : population.getChromosomes()) {
            double lenOfGradVector = chromosome.getGradient().getLenOfGradVector();
            for (var number : chromosome.getCodedChromosome()) {
                flipMutationConditionFulfilled(number, lenOfGradVector);
            }
        }
    }

    private void flipMutationConditionFulfilled(BitSet bitSet, double gradientVectorLen) {
        double gradCoefficient = exp(-pow(gradientVectorLen, 1.0/2.0)) + 0.4;
        for (int i = 0; i < bitSet.size(); i++) {
            double random = Math.random();
            double mutationRate = fixedMutationRate * gradCoefficient;
            if (random < mutationRate) {
                flip(bitSet, i);
            }
        }
    }
}
