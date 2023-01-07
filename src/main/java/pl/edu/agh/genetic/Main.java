package pl.edu.agh.genetic;

import pl.edu.agh.genetic.model.functions.*;
import pl.edu.agh.genetic.model.stop_conditions.AssumedPrecisionReachedStopCondition;
import pl.edu.agh.genetic.model.stop_conditions.NoImprovementStopCondition;
import pl.edu.agh.genetic.model.stop_conditions.NumberOfGenerationStopCondition;
import pl.edu.agh.genetic.operations.GeneticAlgorithm;
import pl.edu.agh.genetic.operations.Step;
import pl.edu.agh.genetic.operations.crossovers.*;
import pl.edu.agh.genetic.operations.mutations.GradientMutation;
import pl.edu.agh.genetic.operations.mutations.GradientSignGuidedMutation;
import pl.edu.agh.genetic.operations.mutations.Mutation;
import pl.edu.agh.genetic.operations.mutations.SimpleMutation;
import pl.edu.agh.genetic.operations.selections.SimpleSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main {
  public static void main(String[] args) {
    System.out.println();
    System.out.println("BASIC");
    System.out.println();
    for (int i = 0; i < 50; i++) {
      double mutationRate = 0.001 + (i * 0.001);
      run(
          List.of(),
          new SimpleMutation(mutationRate),
          new MultiPointCrossover(),
          new RosenbrockFunction());
    }

    System.out.println();
    System.out.println("GRADIENT MUTATION");
    System.out.println();
    for (int i = 0; i < 50; i++) {
      double mutationRate = 0.001 + (i * 0.001);
      run(
          List.of(),
          new GradientSignGuidedMutation(mutationRate),
          new MultiPointCrossover(),
          new RosenbrockFunction());
    }

    System.out.println();
    System.out.println("GRADIENT MUTATION + CROSSOVER");
    System.out.println();
    for (int i = 0; i < 50; i++) {
      double mutationRate = 0.001 + (i * 0.001);
      run(
          List.of(),
          new GradientSignGuidedMutation(mutationRate),
          new MultiPointGradientSignBasedCrossover(),
          new RosenbrockFunction());
    }

    System.out.println();
    System.out.println("GRADIENT CROSSOVER");
    System.out.println();
    for (int i = 0; i < 50; i++) {
      double mutationRate = 0.001 + (i * 0.001);
      run(
          List.of(),
          new SimpleMutation(mutationRate),
          new MultiPointGradientSignBasedCrossover(),
          new RosenbrockFunction());
    }
  }

  static void run(
      List<Step> gradientSteps, Mutation mutation, Crossover crossover, Function function) {
    int testRuns = 2000;
    double[] percentages = new double[24];
    double minFunValue = Double.POSITIVE_INFINITY;
    List<Integer> numberOfGenerations = new ArrayList();
    List<Integer> numberOfGenerationsWithoutImprovement = new ArrayList();
    for (int j = 0; j < testRuns; j++) {

      GeneticAlgorithm ge =
          GeneticAlgorithm.builder()
              .crossover(crossover)
              .mutation(mutation)
              .selection(new SimpleSelection())
              .postSteps(gradientSteps)
              .populationSize(30)
              .function(function)
              .stopConditions(
                  List.of(
                          new AssumedPrecisionReachedStopCondition(1e-10),
                      new NumberOfGenerationStopCondition(1000),
                      new NoImprovementStopCondition(100)))
              .build();

      ge.runAlgorithm();
      // System.out.println(ge.metadata);
      if (ge.metadata.getBestChromosome().getFitness() > 1e1) percentages[0]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e2) percentages[1]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e3) percentages[2]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e4) percentages[3]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e5) percentages[4]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e6) percentages[5]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e7) percentages[6]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e8) percentages[7]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e9) percentages[8]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e10) percentages[9]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e11) percentages[10]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e12) percentages[11]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e13) percentages[12]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e14) percentages[13]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e15) percentages[14]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e16) percentages[15]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e17) percentages[16]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e18) percentages[17]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e19) percentages[18]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e20) percentages[19]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e25) percentages[20]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e30) percentages[21]++;
      if (ge.metadata.getBestChromosome().getFitness() > 1e35) percentages[22]++;
      if (ge.metadata.getBestChromosome().getFitness().isInfinite()) percentages[23]++;

      if (minFunValue < ge.metadata.getBestChromosome().getFunctionValue()) {
        minFunValue = minFunValue;
      } else {
        minFunValue = ge.metadata.getBestChromosome().getFunctionValue();
      }

      numberOfGenerations.add(ge.metadata.getNumberOfGenerations());
      numberOfGenerationsWithoutImprovement.add(
          ge.metadata.getNumberOfGenerationsWithoutImprovement());
    }

    double avgIterations = (double) numberOfGenerations.stream().reduce(0, Integer::sum) / testRuns;
    double avgIterationsWithoutImprovement =
        (double) numberOfGenerationsWithoutImprovement.stream().reduce(0, Integer::sum) / testRuns;
    double stdDeviationNumberOfGenerations =
        sqrt(
            numberOfGenerations.stream()
                    .map(x -> pow((double) x - avgIterations, 2))
                    .reduce(0.0, Double::sum)
                / numberOfGenerations.size());

    double stdDeviationNumberOfGenerationsWithoutImprovement =
        sqrt(
            numberOfGenerationsWithoutImprovement.stream()
                    .map(x -> pow((double) x - avgIterationsWithoutImprovement, 2))
                    .reduce(0.0, Double::sum)
                / numberOfGenerations.size());

    double[] result = Arrays.stream(percentages).map(p -> p / (testRuns)).toArray();

    System.out.println(
        Arrays.toString(result)
                .replace(",", ";")
                .replace("[.]", ",")
                .replace("[", "")
                .replace("]", "")
            + ";"
            //            + " Ś: "
            + avgIterations
            + ";"
            //            + " ŚBP: "
            + avgIterationsWithoutImprovement
            + ";"
            //            + " O: "
            + stdDeviationNumberOfGenerations
            + ";"
            //            + " OBP: "
            + stdDeviationNumberOfGenerationsWithoutImprovement
            + ";"
            //            + " Min: "
            + minFunValue);
  }
}
