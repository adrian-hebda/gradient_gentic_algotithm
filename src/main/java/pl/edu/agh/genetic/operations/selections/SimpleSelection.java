package pl.edu.agh.genetic.operations.selections;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.LinkedList;
import java.util.List;

public class SimpleSelection implements Selection {
  @Override
  public List<Chromosome> performSelection(Population population) {
    List<Chromosome> matingPool = new LinkedList<>();
    for (int i = 0; i < population.getPopulation().size() / 2; i++) {
      matingPool.add(selectChromosomeByRouletteWheel(population));
    }
    return matingPool;
  }

  private Chromosome selectChromosomeByRouletteWheel(Population population) {
    double fitnessSum =
        population.getPopulation().stream().mapToDouble(Chromosome::getFitness).sum();

    final double finalSum = Double.isFinite(fitnessSum) ? fitnessSum : Double.MAX_VALUE;
    double[] probabilities =
        population.getPopulation().stream()
            .mapToDouble(
                chromosome ->
                    Double.isFinite(chromosome.getFitness())
                        ? chromosome.getFitness()
                        : Double.MAX_VALUE)
            .map(
                fitness -> {
                  double x = fitness / finalSum;
                  if (x > 1.0) throw new RuntimeException();
                  return x;
                })
            .toArray();
    double randomDouble = RandomUtils.getRandomDoubleInRange(0.0, 1.0);
    for (int i = 0; i < population.getPopulation().size(); i++) {
      randomDouble -= probabilities[i];
      if (randomDouble < 0) {
        return population.getPopulation().get(i);
      }
    }
    return population.getPopulation().get(population.getPopulation().size() - 1);
  }
}
