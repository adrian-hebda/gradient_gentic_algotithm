package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class DifferencesBasedCrossover implements Crossover {

  @Override
  public List<Chromosome> performCrossover(List<Chromosome> parents) {

    List<Chromosome> chromosomesAfterCrossover = new LinkedList<>();
    for (int i = 0; i < parents.size(); i++) {
      chromosomesAfterCrossover.addAll(
          createTwoNewChromosomes(chooseRandomParent(parents), chooseRandomParent(parents)));
    }
    return chromosomesAfterCrossover;
  }

  private List<Chromosome> createTwoNewChromosomes(Chromosome parent1, Chromosome parent2) {
    int numberOfBits = Double.SIZE * parent1.getNumberOfDoublesCoded();
    Boolean[] similarityVector = new Boolean[numberOfBits];
    List<Boolean> firstParentDifferences = new ArrayList<>();
    List<Boolean> secondParentDifferences = new ArrayList<>();
    for (int i = 0; i < numberOfBits; i++) {
      if (parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE)
          == parent2.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE)) {
        similarityVector[i] =
            parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE);
      } else {
        similarityVector[i] = null;
        firstParentDifferences.add(
            parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE));
        secondParentDifferences.add(
            parent2.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE));
      }
    }

    // crossover between differences
    int differenceListSize = firstParentDifferences.size();
    List<Boolean> firstParentDifferencesAfterCrossover = new ArrayList<>();
    List<Boolean> secondParentDifferencesAfterCrossover = new ArrayList<>();

    if (differenceListSize == 0) {
      return List.of(
          new Chromosome(parent1.getNumberOfDoublesCoded(), parent1.getCodedChromosome()),
          new Chromosome(parent2.getNumberOfDoublesCoded(), parent2.getCodedChromosome()));
    } else if (differenceListSize == 1) {
      secondParentDifferencesAfterCrossover.add(firstParentDifferences.get(0));
      firstParentDifferencesAfterCrossover.add(secondParentDifferences.get(0));
    } else if (differenceListSize == 2) {
      secondParentDifferencesAfterCrossover.addAll(
          List.of(firstParentDifferences.get(0), secondParentDifferences.get(1)));
      firstParentDifferencesAfterCrossover.addAll(
          List.of(secondParentDifferences.get(0), firstParentDifferences.get(1)));
    } else {
      int crossoverPoint = RandomUtils.getRandomIntInRange(1, differenceListSize - 1);

      firstParentDifferencesAfterCrossover.addAll(
          firstParentDifferences.subList(0, crossoverPoint));
      firstParentDifferencesAfterCrossover.addAll(
          secondParentDifferences.subList(crossoverPoint, secondParentDifferences.size()));
      secondParentDifferencesAfterCrossover.addAll(
          secondParentDifferences.subList(0, crossoverPoint));
      secondParentDifferencesAfterCrossover.addAll(
          firstParentDifferences.subList(crossoverPoint, firstParentDifferences.size()));
    }

    List<BitSet> firstChild = new ArrayList<>();
    List<BitSet> secondChild = new ArrayList<>();

    for (int i = 0; i < parent1.getNumberOfDoublesCoded(); i++) {
      firstChild.add(new BitSet(Double.SIZE));
      secondChild.add(new BitSet(Double.SIZE));
    }

    int numberOfNotSimilar = 0;
    for (int i = 0; i < similarityVector.length; i++) {
      if (similarityVector[i] == Boolean.TRUE) {
        firstChild.get(i / Double.SIZE).set(i % Double.SIZE);
        secondChild.get(i / Double.SIZE).set(i % Double.SIZE);
      } else if (similarityVector[i] == Boolean.FALSE) {
        // do nothing - already set to false
      } else {
        firstChild
            .get(i / Double.SIZE)
            .set(i % Double.SIZE, firstParentDifferencesAfterCrossover.get(numberOfNotSimilar));
        secondChild
            .get(i / Double.SIZE)
            .set(i % Double.SIZE, secondParentDifferencesAfterCrossover.get(numberOfNotSimilar));
        numberOfNotSimilar++;
      }
    }
    return List.of(
        new Chromosome(parent1.getNumberOfDoublesCoded(), firstChild),
        new Chromosome(parent2.getNumberOfDoublesCoded(), secondChild));
  }
}
