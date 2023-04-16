package pl.edu.agh.genetic.operations.additional;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.Step;

import static java.lang.StrictMath.pow;

public class MoveByAdjustableGradient implements Step {

  private Double rate = 0.01;
  private Double multiplier = 1.0;

  public MoveByAdjustableGradient(Double rate) {
    this.rate = rate;
  }

  @Override
  public void performStep(Population population, GeneticAlgorithm algo) {
    if (algo.metadata.getNumberOfGenerations() != 0) {
      multiplier =
          (algo.metadata.getNumberOfGenerations()
                  - algo.metadata.getNumberOfGenerationsWithoutImprovement() + 1.0)
              / ((pow(algo.metadata.getNumberOfGenerationsWithoutImprovement(), 1.5)
                  + algo.metadata.getNumberOfGenerations()) + 1.0);
    }

    population.moveByGradient(rate * multiplier);
  }
}
