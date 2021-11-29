package pl.edu.agh.genetic.operations;

import lombok.Builder;
import pl.edu.agh.genetic.exceptions.MissingRequiredOperationException;
import pl.edu.agh.genetic.exceptions.PopulationNotGeneratedException;
import pl.edu.agh.genetic.model.AlgorithmMetadata;
import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.model.stop_conditions.FitnessFunctionIsInfinity;
import pl.edu.agh.genetic.model.stop_conditions.StopCondition;
import pl.edu.agh.genetic.operations.crossovers.Crossover;
import pl.edu.agh.genetic.operations.mutations.Mutation;
import pl.edu.agh.genetic.operations.selections.Selection;

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
      Population population,
      List<StopCondition> stopConditions) {
    this.crossover = crossover;
    this.mutation = mutation;
    this.selection = selection;
    this.population = population;

    this.stopConditions.addAll(stopConditions);
    this.stopConditions.add(new FitnessFunctionIsInfinity());

    this.preSteps = preSteps == null ? Collections.emptyList() : preSteps;
    this.postSteps = postSteps == null ? Collections.emptyList() : preSteps;
  }

  public Chromosome runAlgorithm() {
    validateRun();
    population.calculateFitness();
    while (stopConditions.stream()
        .noneMatch(stopCondition -> stopCondition.isStopConditionMet(metadata))) {
      preSteps.forEach(step -> step.performStep(population));
      runCoreAlgorithm();
      postSteps.forEach(step -> step.performStep(population));
      updateMetadata();
    }

    return metadata.getBestChromosome();
  }

  private void runCoreAlgorithm() {
    List<Chromosome> matingPool = selection.performSelection(population);
    List<Chromosome> newChromosomes = crossover.performCrossover(matingPool);
    population.setPopulation(newChromosomes);
    mutation.performStep(population);
    population.calculateFitness();
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
    if (population.getFittest().equals(metadata.getBestChromosome())) {
      metadata.setNumberOfGenerationsWithoutImprovement(metadata.getNumberOfGenerations() + 1);
    } else {
      metadata.setNumberOfGenerationsWithoutImprovement(0);
    }
  }

  private void validateRun() {
    validateCrossover();
    validateMutation();
    validateSelection();
    validatePopulation();
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
