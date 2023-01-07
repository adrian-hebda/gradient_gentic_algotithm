package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.enums.Sign;
import pl.edu.agh.genetic.utils.RandomUtils;
import pl.edu.agh.genetic.utils.SignUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MultiPointGradientSignBasedCrossover implements IMultiPointCrossover {

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

  private List<BitSet> chooseBitsets(Chromosome firstParent, List<Chromosome> parents) {
    ArrayList<BitSet> chosenOnes = new ArrayList<>();
    for (int i = 0; i < firstParent.getNumberOfDoublesCoded(); i++) {
      int it = i;
      List<Chromosome> plusSignGradientChromosomes =
          parents.stream()
              .filter(
                  chromosome ->
                      SignUtils.determine(chromosome.getGradient().getGradient()[it])
                          .equals(Sign.PLUS))
              .toList();
      List<Chromosome> minusSignGradientChromosomes =
          parents.stream()
              .filter(
                  chromosome ->
                      SignUtils.determine(chromosome.getGradient().getGradient()[it])
                          .equals(Sign.MINUS))
              .toList();

      if (SignUtils.determine(firstParent.getGradient().getGradient()[it]).equals(Sign.PLUS)
          && !plusSignGradientChromosomes.isEmpty()) {
        chosenOnes.add(
            plusSignGradientChromosomes
                .get(RandomUtils.getRandomIntInRange(0, plusSignGradientChromosomes.size()))
                .getCodedChromosome()
                .get(it));
      } else if (SignUtils.determine(firstParent.getGradient().getGradient()[it]).equals(Sign.MINUS)
          && !minusSignGradientChromosomes.isEmpty()) {
        chosenOnes.add(
            minusSignGradientChromosomes
                .get(RandomUtils.getRandomIntInRange(0, minusSignGradientChromosomes.size()))
                .getCodedChromosome()
                .get(it));
      } else {
        chosenOnes.add(
            parents
                .get(RandomUtils.getRandomIntInRange(0, parents.size()))
                .getCodedChromosome()
                .get(it));
      }
    }
    return chosenOnes;
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
