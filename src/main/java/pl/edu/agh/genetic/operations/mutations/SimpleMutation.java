package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;

import java.util.BitSet;
import java.util.Random;

public class SimpleMutation implements Mutation {

  private final double fixedMutationRate;

  public SimpleMutation(double fixedMutationRate) {
    this.fixedMutationRate = fixedMutationRate;
  }

  @Override
  public void performMutation(Population population) {

    population.getPopulation().stream()
        .flatMap(chromosome -> chromosome.getCodedChromosome().stream())
        .forEach(this::flipMutationConditionFulfilled);
  }

  private void flipMutationConditionFulfilled(BitSet bitSet) {
    for (int i = 0; i < bitSet.length(); i++) {
      if (Math.random() < fixedMutationRate) {
        bitSet.flip(i);
        if ((bitSet.get(52, 63).cardinality() == 11 && bitSet.get(0, 52).cardinality() == 0)
            || (bitSet.get(52, 63).cardinality() == 11 && bitSet.get(0, 52).cardinality() != 0)) {
          bitSet.flip(i);
        }
      }
    }
  }
}
