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

  default BitSet createMixedBitset(
          BitSet firstChromosomeBitSet, BitSet secondChromosomeBitSet, int crossoverPointInDouble) {
    BitSet newBitSet = new BitSet(Double.SIZE);
    for (int bitIndex = 0; bitIndex < Double.SIZE; bitIndex++) {
      if (bitIndex < crossoverPointInDouble) {
        setBitDependingOnOtherBitset(firstChromosomeBitSet, bitIndex, newBitSet);
      } else {
        setBitDependingOnOtherBitset(secondChromosomeBitSet, bitIndex, newBitSet);
      }
    }
    return newBitSet;
  }
}
