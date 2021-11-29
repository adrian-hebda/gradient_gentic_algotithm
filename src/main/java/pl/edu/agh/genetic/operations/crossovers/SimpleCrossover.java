package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class SimpleCrossover implements Crossover {

  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {
    List<Chromosome> chromosomesAfterCrossover = new LinkedList<>();
    for (int i = 0; i < parents.size(); i++) {
      chromosomesAfterCrossover.addAll(
          createTwoNewChromosomes(chooseRandomParent(parents), chooseRandomParent(parents)));
    }
    return chromosomesAfterCrossover;
  }

  private Chromosome chooseRandomParent(List<Chromosome> parents) {
    return parents.get(RandomUtils.getRandomIntInRange(0, parents.size()));
  }

  private List<Chromosome> createTwoNewChromosomes(
      Chromosome firstChromosome, Chromosome secondChromosome) {
    int numberOfBits = Double.SIZE * firstChromosome.getNumberOfDoublesCoded();
    int crossoverPoint = RandomUtils.getRandomIntInRange(1, numberOfBits - 1);
    int crossoverDoubleNumber = crossoverPoint / Double.SIZE;
    int crossoverPointInDouble = crossoverPoint % Double.SIZE;

    Chromosome firstNewChromosome =
        createChromosome(
            firstChromosome.getCodedChromosome(),
            secondChromosome.getCodedChromosome(),
            crossoverDoubleNumber,
            crossoverPointInDouble);

    Chromosome secondNewChromosome =
        createChromosome(
            secondChromosome.getCodedChromosome(),
            firstChromosome.getCodedChromosome(),
            crossoverDoubleNumber,
            crossoverPointInDouble);

    return List.of(firstNewChromosome, secondNewChromosome);
  }

  public static String toString(BitSet bs) {
    int len = bs.length();
    StringBuffer buf = new StringBuffer(len);
    for (int i = 0; i < len; i++) buf.append(bs.get(i) ? '1' : '0');
    return buf.toString();
  }

  private Chromosome createChromosome(
      List<BitSet> firstPartChromosome,
      List<BitSet> secondPartChromosome,
      int crossoverDoubleNumber,
      int crossoverPointInDouble) {

    List<BitSet> newCodedChromosome = new ArrayList<>(firstPartChromosome.size());
    for (int doubleNumber = 0; doubleNumber < firstPartChromosome.size(); doubleNumber++) {
      if (doubleNumber == crossoverDoubleNumber) {
        BitSet mixedBitset =
            createMixedBitset(
                firstPartChromosome.get(doubleNumber),
                secondPartChromosome.get(doubleNumber),
                crossoverPointInDouble);
        newCodedChromosome.add(mixedBitset);
      } else if (doubleNumber < crossoverDoubleNumber) {
        newCodedChromosome.add(firstPartChromosome.get(doubleNumber));
      } else if (doubleNumber > crossoverDoubleNumber) {
        newCodedChromosome.add(secondPartChromosome.get(doubleNumber));
      }
    }
    Chromosome newChromosome = new Chromosome(firstPartChromosome.size());
    newChromosome.setCodedChromosome(newCodedChromosome);
    return newChromosome;
  }

  private BitSet createMixedBitset(
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

  private void setBitDependingOnOtherBitset(BitSet bitPattern, int index, BitSet newBitSet) {
    if (bitPattern.get(index)) {
      newBitSet.set(index);
    } else {
      newBitSet.clear(index);
    }
  }
}
