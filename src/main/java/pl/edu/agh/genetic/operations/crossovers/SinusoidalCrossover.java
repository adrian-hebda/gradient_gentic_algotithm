package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;

import java.time.chrono.ChronoPeriod;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SinusoidalCrossover implements Crossover {
  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {
    List<Chromosome> offsprings = new ArrayList<>();
    for (int i = 0; i < parents.size() * 2; i++) {
      offsprings.add(createNewChromosome(chooseRandomParent(parents), chooseRandomParent(parents)));
    }
    return offsprings;
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
        double random = Math.random();
        boolean bitValue =
            random > 0.5
                ? parent1.getCodedChromosome().get(i).get(j)
                : parent2.getCodedChromosome().get(i).get(j);

        bitSet.set(j, bitValue);
      }
      offspringBitsetList1.add(bitSet);
    }
    return offspringBitsetList1;
  }
}
