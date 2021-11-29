package pl.edu.agh.genetic;

import pl.edu.agh.genetic.model.Constraint;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.model.functions.SimpleTestFunction;
import pl.edu.agh.genetic.model.stop_conditions.NumberOfGenerationStopCondition;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.crossovers.SimpleCrossover;
import pl.edu.agh.genetic.operations.mutations.SimpleMutation;
import pl.edu.agh.genetic.operations.selections.SimpleSelection;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    int counter = 0;
    for (int i = 0; i < 100; i++) {

      GeneticAlgorithm ge =
          GeneticAlgorithm.builder()
              .crossover(new SimpleCrossover())
              .mutation(new SimpleMutation(0.01))
              .selection(new SimpleSelection())
              .population(
                  new Population(
                      10,
                      List.of(new Constraint(-4.5, 4.5), new Constraint(-4.5, 4.5)),
                      new SimpleTestFunction()))
              .stopConditions(List.of(new NumberOfGenerationStopCondition(200)))
              .build();

      ge.runAlgorithm();
      System.out.println(ge.metadata);
      if (ge.metadata.getBestChromosome().getFitness() > 25000) counter++;
    }
    System.out.println(counter);
  }
}
