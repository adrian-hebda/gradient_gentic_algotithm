package pl.edu.agh.genetic.operations.additional;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.Step;

public class MoveByGradient implements Step {

  private Double rate = 0.01;

  public MoveByGradient(Double rate) {
    this.rate = rate;
  }

  @Override
  public void performStep(Population population, GeneticAlgorithm algorithm) {
    population.moveByGradient(rate);
  }
}
