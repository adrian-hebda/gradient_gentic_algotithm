package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;

import java.util.BitSet;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class GradientMutation implements Mutation {
    private final Double fixedMutationRate;

    public GradientMutation(Double fixedMutationRate) {
        this.fixedMutationRate = fixedMutationRate;
    }

    @Override
    public void performMutation(Population population) {
        for (var chromosome : population.getChromosomes()) {
            Double lenOfGradVector = chromosome.getGradient().getLenOfGradVector();
            for (var number : chromosome.getCodedChromosome()) {
                flipMutationConditionFulfilled(number, lenOfGradVector);
            }
        }
    }

    private void flipMutationConditionFulfilled(BitSet bitSet, Double gradientVectorLen) {
        Double gradCoefficient = exp(-pow(gradientVectorLen, 1.0/2.0)) + 0.4;
        for (int i = 0; i < bitSet.size(); i++) {
            Double random = Math.random();
            Double mutationRate = fixedMutationRate * gradCoefficient;
            if (random < mutationRate) {
                flip(bitSet, i);
            }
        }
    }
}
