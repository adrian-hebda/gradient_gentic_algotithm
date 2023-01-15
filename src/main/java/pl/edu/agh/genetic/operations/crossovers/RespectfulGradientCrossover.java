package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class RespectfulGradientCrossover implements Crossover {
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
    for (int i = 0; i < numberOfBits; i++) {
      if (parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE)
          == parent2.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE)) {
        similarityVector[i] =
            parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE);
      } else {
        similarityVector[i] = null;
      }
    }

    List<BitSet> firstChild = new ArrayList<>();
    List<BitSet> secondChild = new ArrayList<>();

    for (int i = 0; i < parent1.getNumberOfDoublesCoded(); i++) {
      firstChild.add(new BitSet(Double.SIZE));
      secondChild.add(new BitSet(Double.SIZE));
    }

    for (int i = 0; i < similarityVector.length; i++) {
      if (similarityVector[i] == Boolean.TRUE) {
        firstChild.get(i / Double.SIZE).set(i % Double.SIZE);
        secondChild.get(i / Double.SIZE).set(i % Double.SIZE);
      } else if (similarityVector[i] == Boolean.FALSE) {
        // do nothing - already set to false
      } else {
        Double gradientValueFirstChild = parent1.getGradient().getGradient()[i / Double.SIZE];
        firstChild.get(i / Double.SIZE).set(i % Double.SIZE, gradientValueFirstChild < 0);

        Double gradientValueSecondChild = parent2.getGradient().getGradient()[i / Double.SIZE];
        secondChild.get(i / Double.SIZE).set(i % Double.SIZE, gradientValueSecondChild < 0);
      }
    }
    return List.of(
        new Chromosome(parent1.getNumberOfDoublesCoded(), firstChild),
        new Chromosome(parent2.getNumberOfDoublesCoded(), secondChild));
  }


}
