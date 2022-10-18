package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.BitSet;
import java.util.List;

public interface Crossover {
  List<Chromosome> performCrossover(List<Chromosome> parents);

  default Chromosome chooseRandomParent(List<Chromosome> parents) {
    return parents.get(RandomUtils.getRandomIntInRange(0, parents.size()));
  }

  default void setBitDependingOnOtherBitset(BitSet bitPattern, int index, BitSet newBitSet) {
    newBitSet.set(index, bitPattern.get(index));
  }
}
