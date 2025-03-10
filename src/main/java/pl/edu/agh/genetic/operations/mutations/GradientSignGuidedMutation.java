package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.model.enums.Sign;
import pl.edu.agh.genetic.utils.SignUtils;

import java.util.BitSet;
import java.util.List;

public class GradientSignGuidedMutation implements Mutation {
  private final Double fixedMutationRate;

  public GradientSignGuidedMutation(Double fixedMutationRate) {
    this.fixedMutationRate = fixedMutationRate;
  }

  @Override
  public void performMutation(Population population) {
    for (var chromosome : population.getChromosomes()) {
      List<BitSet> codedChromosome = chromosome.getCodedChromosome();
      for (int i = 0; i < codedChromosome.size(); i++) {
        BitSet number = codedChromosome.get(i);
        flipMutationConditionFulfilled(
            number, SignUtils.determine(chromosome.getGradient().getGradient()[i]));
      }
    }
  }

  private void flipMutationConditionFulfilled(BitSet bitSet, Sign gradientSign) {

    for (int i = 0; i < bitSet.size(); i++) {
      Double mutationRate = fixedMutationRate;

      boolean geneSet = bitSet.get(i);
      if ((geneSet && Sign.PLUS.equals(gradientSign))
          || (!geneSet && Sign.MINUS.equals(gradientSign))) {
        mutationRate += mutationRate * 0.3;
      } else if ((geneSet && Sign.MINUS.equals(gradientSign))
          || (!geneSet && Sign.PLUS.equals(gradientSign))) {
        mutationRate = mutationRate * 0.7;
      }

      Double random = Math.random();
      if (random < mutationRate) {
        flip(bitSet, i);
      }
    }
  }
}
