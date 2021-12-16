package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Gradient;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class GradientBasedCrossover implements Crossover, Gradient {

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
    Chromosome greaterGradientLenChromosome;
    Chromosome smallerGradientLenChromosome;

    if (firstChromosome.getLenOfGradVector() > secondChromosome.getLenOfGradVector()) {
      greaterGradientLenChromosome = firstChromosome;
      smallerGradientLenChromosome = secondChromosome;
    } else if (firstChromosome.getLenOfGradVector() < secondChromosome.getLenOfGradVector()) {
      greaterGradientLenChromosome = secondChromosome;
      smallerGradientLenChromosome = firstChromosome;
    } else {
      return List.of(firstChromosome, secondChromosome);
    }

    int numberOfBits = Double.SIZE * firstChromosome.getNumberOfDoublesCoded();
    int crossoverPoint =
        (int)
            (numberOfBits
                * greaterGradientLenChromosome.getLenOfGradVector()
                / (firstChromosome.getLenOfGradVector() + secondChromosome.getLenOfGradVector()));
    int crossoverDoubleNumber = crossoverPoint / Double.SIZE;
    int crossoverPointInDouble = crossoverPoint % Double.SIZE;

    int reversedCrossoverPoint = numberOfBits - crossoverPoint;
    int reversedCrossoverDoubleNumber = reversedCrossoverPoint / Double.SIZE;
    int reversedCrossoverPointInDouble = reversedCrossoverPoint % Double.SIZE;

    Chromosome firstNewChromosome;
    Chromosome secondNewChromosome;
    if (crossoverPoint < numberOfBits / 2) {
      firstNewChromosome =
          createChromosome(
              greaterGradientLenChromosome.getCodedChromosome(),
              smallerGradientLenChromosome.getCodedChromosome(),
              crossoverDoubleNumber,
              crossoverPointInDouble);

      secondNewChromosome =
          createChromosome(
              smallerGradientLenChromosome.getCodedChromosome(),
              greaterGradientLenChromosome.getCodedChromosome(),
              reversedCrossoverDoubleNumber,
              reversedCrossoverPointInDouble);
    } else {
      firstNewChromosome =
          createChromosome(
              smallerGradientLenChromosome.getCodedChromosome(),
              greaterGradientLenChromosome.getCodedChromosome(),
              crossoverDoubleNumber,
              crossoverPointInDouble);

      secondNewChromosome =
          createChromosome(
              greaterGradientLenChromosome.getCodedChromosome(),
              smallerGradientLenChromosome.getCodedChromosome(),
              reversedCrossoverDoubleNumber,
              reversedCrossoverPointInDouble);
    }

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
      } else {
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
