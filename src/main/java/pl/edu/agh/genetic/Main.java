package pl.edu.agh.genetic;

import pl.edu.agh.genetic.model.functions.StyblinskiFunction;
import pl.edu.agh.genetic.model.stop_conditions.NoImprovementStopCondition;
import pl.edu.agh.genetic.model.stop_conditions.NumberOfGenerationStopCondition;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.Step;
import pl.edu.agh.genetic.operations.additional.MoveByAdjustableGradient;
import pl.edu.agh.genetic.operations.additional.MoveByGradient;
import pl.edu.agh.genetic.operations.crossovers.SimpleCrossover;
import pl.edu.agh.genetic.operations.mutations.SimpleMutation;
import pl.edu.agh.genetic.operations.selections.SimpleSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      double step = i * 0.005 + 0.0003;
      //run(List.of(new MoveByGradient(step)), i, step);
      run(List.of(new MoveByAdjustableGradient(step)), i, step);
      System.out.println();
    }
  }

  static void run(List<Step> gradientSteps, long i, double step) {
    int testRuns = 1000;
    double[] procentages = new double[28];
    double sumFitFunCall = 0;
    for (int j = 0; j < testRuns; j++) {

      GeneticAlgorithm ge =
          GeneticAlgorithm.builder()
              .crossover(new SimpleCrossover())
              .mutation(new SimpleMutation(0.008))
              .selection(new SimpleSelection())
              .postSteps(gradientSteps)
              .populationSize(20)
              .function(new StyblinskiFunction())
              .stopConditions(
                  List.of(
                      new NumberOfGenerationStopCondition(500),
                      new NoImprovementStopCondition(100)))
              .build();

      ge.runAlgorithm();
      // System.out.println(ge.metadata);
      if (ge.metadata.getBestChromosome().getFitness() > 1e5) procentages[0]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e6) procentages[1]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e7) procentages[2]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e8) procentages[3]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e9) procentages[4]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e10) procentages[5]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e11) procentages[6]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e12) procentages[7]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e13) procentages[8]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e14) procentages[9]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e15) procentages[10]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e16) procentages[11]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e17) procentages[12]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e18) procentages[13]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e19) procentages[14]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e20) procentages[15]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e21) procentages[16]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e22) procentages[17]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e23) procentages[18]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e24) procentages[19]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e25) procentages[20]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e26) procentages[21]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e27) procentages[22]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e28) procentages[23]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e29) procentages[24]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e30) procentages[25]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e35) procentages[26]++;
      if (ge.metadata.getBestChromosome().getFitness().isInfinite()) procentages[27]++;
      sumFitFunCall += ge.metadata.getNumberOfFitnessFunctionsExecutions();
    }
    double[] result = Arrays.stream(procentages).map(p -> p / (testRuns)).toArray();
    System.out.println( i + " - iteracja " + step + " - step " + gradientSteps.get(0).getClass().getName() + " - funkcja gradientowa  "  +
        Arrays.toString(result) + " Liczba wywołań funkcji celu: " + sumFitFunCall / testRuns);
  }
}
