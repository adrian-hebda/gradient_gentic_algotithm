package pl.edu.agh.genetic.model;

import lombok.Data;
import pl.edu.agh.genetic.exceptions.FunctionDoesNotImplementGradientInterfaceException;
import pl.edu.agh.genetic.model.functions.Function;
import pl.edu.agh.genetic.model.functions.GradientFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class Population {
  private List<Chromosome> population = new ArrayList<>();
  private Chromosome fittest;
  private final Function function;

  public Population(int populationSize, Function function) {
    if (populationSize % 2 != 0) {
      throw new RuntimeException("Population size must be even.");
    }
    this.function = function;
    for (int i = 0; i < populationSize; i++) {
      population.add(
          new Chromosome(
              function.getVariablesConstraints(), function.getRequiredNumberOfParameters()));
    }
    calculateFitness();
  }

  public void calculateFitness() {
    population.stream().forEach(chromosome -> chromosome.calculateFitness(function));
    fittest =
        population.stream()
            .max(Comparator.comparing(Chromosome::getFitness))
            .orElseThrow(RuntimeException::new); // TODO exception
  }

  // TODO wyciągnąć do Step

  public void moveByGradient(double rate) {
    if (function instanceof GradientFunction) {
      population.forEach(
          chromosome -> chromosome.moveByGradient((GradientFunction) function, rate));
    } else {
      throw new FunctionDoesNotImplementGradientInterfaceException();
    }
  }
}
