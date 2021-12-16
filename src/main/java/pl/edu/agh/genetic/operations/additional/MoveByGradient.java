package pl.edu.agh.genetic.operations.additional;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.Step;

public class MoveByGradient implements Step {

  private double rate = 0.01;

  public MoveByGradient(double rate) {
    this.rate = rate;
  }

  @Override
  public void performStep(Population population, GeneticAlgorithm algorithm) {
    population.moveByGradient(rate);
  }
}
