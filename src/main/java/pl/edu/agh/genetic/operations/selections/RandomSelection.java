package pl.edu.agh.genetic.operations.selections;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.LinkedList;
import java.util.List;

public class RandomSelection implements Selection {

  @Override
  public List<Chromosome> performSelection(Population population) {
    List<Chromosome> matingPool = new LinkedList<>();
    for (int i = 0; i < population.getChromosomes().size() / 2; i++) {
      matingPool.add(
          population
              .getChromosomes()
              .get(RandomUtils.getRandomIntInRange(0, population.getChromosomes().size())));
    }
    return matingPool;
  }
}
