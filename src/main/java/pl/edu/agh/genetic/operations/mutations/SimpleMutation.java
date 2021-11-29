package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.BitSet;

public class SimpleMutation implements Mutation {

  private final double fixedMutationRate;

  public SimpleMutation(double fixedMutationRate) {
    this.fixedMutationRate = fixedMutationRate;
  }

  @Override
  public void performStep(Population population) {

    population.getPopulation().stream()
        .flatMap(chromosome -> chromosome.getCodedChromosome().stream())
        .forEach(this::flipMutationConditionFulfilled);
  }

  private void flipMutationConditionFulfilled(BitSet bitSet) {
    for (int i = 0; i < bitSet.length(); i++) {
      if (RandomUtils.getRandomDoubleInRange(0.0, 1.0) < fixedMutationRate) {
        bitSet.flip(i);
        if ((bitSet.get(52, 63).cardinality() == 11 && bitSet.get(0, 52).cardinality() == 0)
            || (bitSet.get(52, 63).cardinality() == 11 && bitSet.get(0, 52).cardinality() != 0)) {
          bitSet.flip(i);
        }
      }
    }
  }
}
