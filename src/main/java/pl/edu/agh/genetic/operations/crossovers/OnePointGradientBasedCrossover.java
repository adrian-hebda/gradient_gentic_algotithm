package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Gradient;
import pl.edu.agh.genetic.utils.BitSetUtils;

import java.util.*;

public class OnePointGradientBasedCrossover implements IOnePointCrossover, Gradient {

  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {
    List<Chromosome> offsprings = new ArrayList<>();
    for (int i = 0; i < parents.size() * 2; i++) {
      offsprings.add(createNewChromosome(chooseRandomParent(parents), chooseRandomParent(parents)));
    }
    return offsprings;
  }

  private Chromosome createNewChromosome(Chromosome parent1, Chromosome parent2) {

    Chromosome greaterGradient =
            parent1.getGradient().getLenOfGradVector() > parent2.getGradient().getLenOfGradVector()
                    ? parent1
                    : parent2;
    Chromosome smallerGradient =
            parent1.getGradient().getLenOfGradVector() > parent2.getGradient().getLenOfGradVector()
                    ? parent2
                    : parent1;

    int numberOfBits = smallerGradient.getNumberOfDoublesCoded() * Double.SIZE;

    int crossoverPoint =
            (int)
                    (numberOfBits
                            * greaterGradient.getGradient().getLenOfGradVector()
                            / (smallerGradient.getGradient().getLenOfGradVector()
                            + greaterGradient.getGradient().getLenOfGradVector()));

    // Indicates which double coded by chromosome is the crossover point of the chromosome
    int crossoverDoubleNumber = crossoverPoint / Double.SIZE;
    // Indicates which bit in double coded by chromosome is the crossover point of the chromosome
    int crossoverPointInDouble = crossoverPoint % Double.SIZE;

    if (crossoverDoubleNumber < numberOfBits / 2){
      return createCodedChromosome(greaterGradient.getCodedChromosome(), smallerGradient.getCodedChromosome(), crossoverDoubleNumber, crossoverPointInDouble);
    } else {
      return createCodedChromosome(smallerGradient.getCodedChromosome(), greaterGradient.getCodedChromosome(), crossoverDoubleNumber, crossoverPointInDouble);
    }


  }
  private Chromosome createCodedChromosome(
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
        newCodedChromosome.add(BitSetUtils.toFixedSizeBitset(firstPartChromosome.get(doubleNumber)));
      } else {
        newCodedChromosome.add(BitSetUtils.toFixedSizeBitset(secondPartChromosome.get(doubleNumber)));
      }
    }
    return new Chromosome(secondPartChromosome.size(), newCodedChromosome);
  }
}
