package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.BitSetUtils;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class OnePointCrossover implements IOnePointCrossover {

  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {
    List<Chromosome> chromosomesAfterCrossover = new LinkedList<>();
    for (int i = 0; i < parents.size(); i++) {
      chromosomesAfterCrossover.addAll(
          createTwoNewChromosomes(chooseRandomParent(parents), chooseRandomParent(parents)));
    }
    return chromosomesAfterCrossover;
  }

  boolean isUndefined(List<BitSet> bitSetList) {
    return bitSetList.stream().anyMatch(BitSetUtils::isUndefined);
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
    firstNewChromosome =
        isUndefined(firstNewChromosome.getCodedChromosome())
            ? new Chromosome(firstChromosome.getNumberOfDoublesCoded(), firstChromosome.getCodedChromosome())
            : firstNewChromosome;

    Chromosome secondNewChromosome =
        createChromosome(
            secondChromosome.getCodedChromosome(),
            firstChromosome.getCodedChromosome(),
            crossoverDoubleNumber,
            crossoverPointInDouble);
    secondNewChromosome =
        isUndefined(secondNewChromosome.getCodedChromosome())
            ?  new Chromosome(secondChromosome.getNumberOfDoublesCoded(), secondChromosome.getCodedChromosome())
            : secondNewChromosome;

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
        newCodedChromosome.add(
            BitSetUtils.toFixedSizeBitset(firstPartChromosome.get(doubleNumber)));
      } else if (doubleNumber > crossoverDoubleNumber) {
        newCodedChromosome.add(
            BitSetUtils.toFixedSizeBitset(secondPartChromosome.get(doubleNumber)));
      }
    }
    return new Chromosome(firstPartChromosome.size(), newCodedChromosome);
  }
}
