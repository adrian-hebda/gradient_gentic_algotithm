package pl.edu.agh.genetic;

import pl.edu.agh.genetic.model.functions.*;
import pl.edu.agh.genetic.model.stop_conditions.NoImprovementStopCondition;
import pl.edu.agh.genetic.model.stop_conditions.NumberOfGenerationStopCondition;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.Step;
import pl.edu.agh.genetic.operations.crossovers.*;
import pl.edu.agh.genetic.operations.mutations.GradientMutation;
import pl.edu.agh.genetic.operations.mutations.Mutation;
import pl.edu.agh.genetic.operations.mutations.SimpleMutation;
import pl.edu.agh.genetic.operations.selections.SimpleSelection;

import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      double step = i * 0.003 + 0.001;
      double mutationRate = 0.0001 + (i * 0.0001);
//      System.out.println("Mutation rate: " + mutationRate);
//      System.out.println("One point crossover");
      run(List.of(), new SimpleMutation(mutationRate), new OnePointCrossover(), i, step);
//      System.out.println("One point crossover with gradient");
//      run(List.of(), new GradientMutation(mutationRate), new OnePointCrossover(), i, step);
      System.out.println();
    }
  }

  static void run(
      List<Step> gradientSteps, Mutation mutation, Crossover crossover, long i, double step) {
    int testRuns = 1000;
    double[] percentages = new double[28];
    double sumFitFunCall = 0;
    double maxFitness = 0;
    double numOfGen = 0;
    double numOfGenWI = 0;
    for (int j = 0; j < testRuns; j++) {

      GeneticAlgorithm ge =
          GeneticAlgorithm.builder()
              .crossover(crossover)
              .mutation(mutation)
              .selection(new SimpleSelection())
              .postSteps(gradientSteps)
              .populationSize(40)
              .function(new HimmelblauFunction())
              .stopConditions(
                  List.of(
                      new NumberOfGenerationStopCondition(1000),
                      new NoImprovementStopCondition(100)))
              .build();

      ge.runAlgorithm();
      // System.out.println(ge.metadata);
      if (ge.metadata.getBestChromosome().getFitness() > 1e5) percentages[0]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e6) percentages[1]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e7) percentages[2]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e8) percentages[3]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e9) percentages[4]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e10) percentages[5]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e11) percentages[6]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e12) percentages[7]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e13) percentages[8]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e14) percentages[9]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e15) percentages[10]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e16) percentages[11]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e17) percentages[12]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e18) percentages[13]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e19) percentages[14]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e20) percentages[15]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e21) percentages[16]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e22) percentages[17]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e23) percentages[18]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e24) percentages[19]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e25) percentages[20]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e26) percentages[21]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e27) percentages[22]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e28) percentages[23]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e29) percentages[24]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e30) percentages[25]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e35) percentages[26]++;
      if (ge.metadata.getBestChromosome().getFitness().isInfinite()) percentages[27]++;
      sumFitFunCall += ge.metadata.getNumberOfFitnessFunctionsExecutions();
      maxFitness =
          maxFitness > ge.metadata.getBestChromosome().getFunctionValue()
              ? maxFitness
              : ge.metadata.getBestChromosome().getFunctionValue();
      numOfGen += ge.metadata.getNumberOfGenerations();
      numOfGenWI += ge.metadata.getNumberOfGenerationsWithoutImprovement();
    }
    double[] result = Arrays.stream(percentages).map(p -> p / (testRuns)).toArray();
    System.out.println(
        Arrays.toString(result).replace(",",";").replace("\\.",",").replace("[","").replace("]",""));
//            + " Średnia liczba wywołań funkcji celu: "
//            + sumFitFunCall / testRuns
//            + " Średnia liczba generacji: "
//            + numOfGen / testRuns
//            + " Średnia liczba generacji bez poprawy : "
//            + numOfGenWI / testRuns
//            + " fittest: "
//            + maxFitness);
  }
}
