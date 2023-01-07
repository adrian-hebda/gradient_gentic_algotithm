package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.BitSetUtils;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class MultiPointCrossover implements IMultiPointCrossover {

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

    List<Integer> crossoverPoints = new ArrayList<>();
    for (int i = 0; i < firstChromosome.getCodedChromosome().size(); i++) {
      crossoverPoints.add(RandomUtils.getRandomIntInRange(1, Double.SIZE + 1));
    }

    Chromosome firstNewChromosome =
        createChromosome(
            firstChromosome.getCodedChromosome(),
            secondChromosome.getCodedChromosome(),
                crossoverPoints);
    firstNewChromosome =
        isUndefined(firstNewChromosome.getCodedChromosome())
            ? firstChromosome
            : firstNewChromosome;

    Chromosome secondNewChromosome =
        createChromosome(
            secondChromosome.getCodedChromosome(),
            firstChromosome.getCodedChromosome(),
                crossoverPoints);
    secondNewChromosome =
        isUndefined(secondNewChromosome.getCodedChromosome())
            ? secondChromosome
            : secondNewChromosome;

    return List.of(firstNewChromosome, secondNewChromosome);
  }

  public static String toString(BitSet bs) {
    int len = bs.length();
    StringBuffer buf = new StringBuffer(len);
    for (int i = 0; i < len; i++) buf.append(bs.get(i) ? '1' : '0');
    return buf.toString();
  }
}
