package pl.edu.agh.genetic.operations;

import lombok.Builder;
import org.apache.commons.lang3.SerializationUtils;
import pl.edu.agh.genetic.exceptions.FunctionDoesNotImplementGradientInterfaceException;
import pl.edu.agh.genetic.exceptions.MissingRequiredOperationException;
import pl.edu.agh.genetic.exceptions.PopulationNotGeneratedException;
import pl.edu.agh.genetic.model.AlgorithmMetadata;
import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Gradient;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.model.functions.Function;
import pl.edu.agh.genetic.model.stop_conditions.FitnessFunctionIsInfinity;
import pl.edu.agh.genetic.model.stop_conditions.StopCondition;
import pl.edu.agh.genetic.operations.crossovers.Crossover;
import pl.edu.agh.genetic.operations.mutations.Mutation;
import pl.edu.agh.genetic.operations.selections.Selection;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {
  private final Crossover crossover;
  private final Mutation mutation;
  private final Selection selection;
  private final List<Step> preSteps;
  private final List<Step> postSteps;
  private final Population population;
  private final List<StopCondition> stopConditions = new ArrayList<>();
  public final AlgorithmMetadata metadata = new AlgorithmMetadata();

  @Builder
  public GeneticAlgorithm(
      Crossover crossover,
      Mutation mutation,
      Selection selection,
      List<Step> preSteps,
      List<Step> postSteps,
      List<StopCondition> stopConditions,
      Function function,
      int populationSize) {
    this.crossover = crossover;
    this.mutation = mutation;
    this.selection = selection;
    this.population = new Population(populationSize, function);

    this.stopConditions.addAll(stopConditions);
    this.stopConditions.add(new FitnessFunctionIsInfinity());

    this.preSteps = preSteps == null ? Collections.emptyList() : preSteps;
    this.postSteps = postSteps == null ? Collections.emptyList() : postSteps;
  }

  public Chromosome runAlgorithm() {
    validateRun();
    population.calculateFitness();
    while (stopConditions.stream()
        .noneMatch(stopCondition -> stopCondition.isStopConditionMet(metadata))) {
      preSteps.forEach(step -> step.performStep(population, this));
      runCoreAlgorithm();
      postSteps.forEach(step -> step.performStep(population, this));
      population.calculateFitness();
      updateMetadata();
    }

    return metadata.getBestChromosome();
  }

  private void runCoreAlgorithm() {

    List<Chromosome> matingPool = selection.performSelection(population);
    List<Chromosome> newChromosomes = crossover.performCrossover(matingPool);
    // saveFittest(newChromosomes);
    population.setPopulation(newChromosomes);
    population.calculateFitness();
    mutation.performMutation(population);
  }

  private void saveFittest(List<Chromosome> newChromosomes) {
    Chromosome fittest = population.getFittest();
    newChromosomes.set(
        RandomUtils.getRandomIntInRange(0, population.getPopulation().size()), fittest);
  }

  private void updateMetadata() {
    updateNumberOfGenerationWithoutImprovement();
    updateBestChromosome();
    updateNumberOfFitnessFunctionCalculations();
    metadata.setNumberOfGenerations(metadata.getNumberOfGenerations() + 1);
  }

  private void updateNumberOfFitnessFunctionCalculations() {
    metadata.setNumberOfFitnessFunctionsExecutions(
        population.getFunction().getNumberOfExecutions());
  }

  private void updateBestChromosome() {
    if (metadata.getBestChromosome() == null
        || metadata.getBestChromosome().getFitness() < population.getFittest().getFitness()) {
      metadata.setBestChromosome(population.getFittest());
    }
  }

  private void updateNumberOfGenerationWithoutImprovement() {
    if (metadata.getBestChromosome() == null
        || metadata.getBestChromosome().getFitness() >= population.getFittest().getFitness()) {
      metadata.setNumberOfGenerationsWithoutImprovement(
          metadata.getNumberOfGenerationsWithoutImprovement() + 1);
    } else {
      metadata.setNumberOfGenerationsWithoutImprovement(0);
    }
  }

  private void validateRun() {
    validateCrossover();
    validateMutation();
    validateSelection();
    validatePopulation();
    validateGradient();
  }

  private void validateGradient() {
    boolean gradientCrossover = crossover instanceof Gradient;
    boolean gradientFunction = population.getFunction() instanceof Gradient;

    if (gradientCrossover && !gradientFunction) {
      throw new FunctionDoesNotImplementGradientInterfaceException();
    }
  }

  private void validatePopulation() {
    if (population == null) {
      throw new PopulationNotGeneratedException();
    }
  }

  private void validateCrossover() {
    if (crossover == null) {
      throw new MissingRequiredOperationException(Crossover.class.getSimpleName());
    }
  }

  private void validateMutation() {
    if (mutation == null) {
      throw new MissingRequiredOperationException(Mutation.class.getSimpleName());
    }
  }

  private void validateSelection() {
    if (selection == null) {
      throw new MissingRequiredOperationException(Selection.class.getSimpleName());
    }
  }
}
