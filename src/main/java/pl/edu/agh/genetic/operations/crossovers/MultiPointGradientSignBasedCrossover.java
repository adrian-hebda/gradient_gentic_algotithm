package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MultiPointGradientSignBasedCrossover implements IMultipointGradientSignBasedCrossover {

  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {
    List<Chromosome> offsprings = new ArrayList<>();
    for (int i = 0; i < parents.size() * 2; i++) {
      Chromosome firstParent = chooseRandomParent(parents);
      List<BitSet> bitSetsToCrossover = chooseBitsets(firstParent, parents);
      offsprings.add(createNewChromosome(firstParent, bitSetsToCrossover));
    }
    return offsprings;
  }

  private Chromosome createNewChromosome(
      Chromosome firstParent, List<BitSet> secondPartChromosome) {
    List<Integer> crossoverPoints = new ArrayList<>();
    for (int i = 0; i < firstParent.getCodedChromosome().size(); i++) {
      crossoverPoints.add(RandomUtils.getRandomIntInRange(1, Double.SIZE + 1));
    }
    return createChromosome(
        firstParent.getCodedChromosome(), secondPartChromosome, crossoverPoints);
  }
}
