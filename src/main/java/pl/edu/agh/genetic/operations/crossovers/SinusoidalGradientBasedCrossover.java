package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SinusoidalGradientBasedCrossover implements Crossover {
  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {
    List<Chromosome> offspring = new ArrayList<>();
    for (int i = 0; i < parents.size(); i++) {
      Chromosome parent1 = chooseRandomParent(parents);
      Chromosome parent2 = chooseRandomParent(parents);
      Chromosome offspring1 = createNewChromosome(parent1, parent2);
      Chromosome offspring2 = createNewChromosome(parent1, parent2);
      offspring.addAll(List.of(offspring1, offspring2));
    }
    return offspring;
  }

  private Chromosome createNewChromosome(Chromosome parent1, Chromosome parent2) {
    List<BitSet> offspringBitsetList = createCodedChromosome(parent1, parent2);
    return new Chromosome(parent1.getCodedChromosome().size(), offspringBitsetList);
  }

  private List<BitSet> createCodedChromosome(Chromosome parent1, Chromosome parent2) {
    List<BitSet> offspringBitsetList1 = new ArrayList<>();

    for (int i = 0; i < parent1.getCodedChromosome().size(); i++) {
      int sizeOfPartOfChromosome = parent1.getCodedChromosome().get(i).size();
      BitSet bitSet = new BitSet(sizeOfPartOfChromosome);
      for (int j = 0; j < sizeOfPartOfChromosome; j++) {
        Double random = Math.random();
        boolean bitValue = chooseBitValue(parent1, parent2, i, j, random);

        bitSet.set(j, bitValue);
      }
      offspringBitsetList1.add(bitSet);
    }
    return offspringBitsetList1;
  }

  private boolean chooseBitValue(
      Chromosome parent1, Chromosome parent2, int i, int j, Double random) {
    Chromosome greaterGradient =
        parent1.getGradient().getLenOfGradVector() > parent2.getGradient().getLenOfGradVector()
            ? parent1
            : parent2;
    Chromosome smallerGradient =
        parent1.getGradient().getLenOfGradVector() > parent2.getGradient().getLenOfGradVector()
            ? parent2
            : parent1;

    Double threshold =
        smallerGradient.getGradient().getLenOfGradVector()
            / (greaterGradient.getGradient().getLenOfGradVector()
                + smallerGradient.getGradient().getLenOfGradVector());

    return random > threshold
        ? smallerGradient.getCodedChromosome().get(i).get(j)
        : greaterGradient.getCodedChromosome().get(i).get(j);
  }
}
