package pl.edu.agh.genetic.model;


import lombok.Data;

@Data
public class AlgorithmMetadata {
    private int numberOfGenerations = 0;
    private int numberOfGenerationsWithoutImprovement = 0;
    private int numberOfFitnessFunctionsExecutions = 0;
    private Chromosome bestChromosome = null;

    @Override
    public String toString() {
        return "AlgorithmMetadata: \n" +
                "numberOfGenerations=" + numberOfGenerations +
                ", numberOfGenerationsWithoutImprovement=" + numberOfGenerationsWithoutImprovement +
                ", numberOfFitnessFunctionsExecutions=" + numberOfFitnessFunctionsExecutions +
                ", bestChromosome.x=" + bestChromosome.getCodedChromosomeAsDoubleList() +
                ", bestChromosome.fitness=" + bestChromosome.getFitness();
    }
}
