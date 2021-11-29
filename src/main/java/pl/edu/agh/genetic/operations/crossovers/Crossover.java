package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;

import java.util.List;

public interface Crossover {
  List<Chromosome> performCrossover(List<Chromosome> parents);
}
