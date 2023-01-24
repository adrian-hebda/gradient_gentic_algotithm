package pl.edu.agh.genetic.model;

import lombok.Data;
import pl.edu.agh.genetic.exceptions.FunctionDoesNotImplementGradientInterfaceException;
import pl.edu.agh.genetic.model.functions.Function;
import pl.edu.agh.genetic.model.functions.GradientFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class Population {
    private final Function function;
    private List<Chromosome> chromosomes = new ArrayList<>();
    private Chromosome fittest;

    public Population(int populationSize, Function function) {
        if (populationSize % 2 != 0) {
            throw new RuntimeException("Population size must be even.");
        }
        this.function = function;
        for (int i = 0; i < populationSize; i++) {
            chromosomes.add(
                    new Chromosome(
                            function.getVariablesConstraints(), function.getRequiredNumberOfParameters()));
        }
        calculateFitness();
    }

    public void calculateFitness() {
        chromosomes.forEach(chromosome -> chromosome.calculateFitness(function));
        fittest =
                chromosomes.stream()
                        .max(Comparator.comparing(Chromosome::getFitness))
                        .orElseThrow(RuntimeException::new);
    }

    public void moveByGradient(double rate) {
        if (function instanceof GradientFunction gradientFunction) {
            chromosomes.forEach(
                    chromosome -> chromosome.moveByGradient(gradientFunction, rate));
        } else {
            throw new FunctionDoesNotImplementGradientInterfaceException();
        }
    }
}
