package pl.edu.agh.genetic.model;

import lombok.Data;
import pl.edu.agh.genetic.exceptions.InvalidNumberOfConstraintsException;
import pl.edu.agh.genetic.model.functions.Function;
import pl.edu.agh.genetic.model.functions.GradientFunction;
import pl.edu.agh.genetic.utils.BitSetUtils;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Data
public class Chromosome implements Serializable {
    private Double fitness;
    // List of bitsets containing set of 64 bits representing double value.
    private List<BitSet> codedChromosome;
    private Integer numberOfDoublesCoded;
    private List<Double> codedChromosomeAsDoubleList;
    private Double functionValue = Double.NaN;

    private GradientData gradient;


    public Chromosome(List<Constraint> constraints, int requiredNumberOfParameters) {
        this.numberOfDoublesCoded = requiredNumberOfParameters;
        this.codedChromosome = new ArrayList<>(numberOfDoublesCoded);

        validate(numberOfDoublesCoded, constraints);

        for (int i = 0; i < numberOfDoublesCoded; i++) {
            this.codedChromosome.add(generateRandomValue(constraints.get(i)));
        }
    }

    public Chromosome(int requiredNumberOfParameters) {
        this.numberOfDoublesCoded = requiredNumberOfParameters;
        this.codedChromosome = new ArrayList<>(numberOfDoublesCoded);

        for (int i = 0; i < numberOfDoublesCoded; i++) {
            this.codedChromosome.add(new BitSet(Double.SIZE));
        }
    }

    public Chromosome(int requiredNumberOfParameters, List<BitSet> codedChromosome) {
        if(requiredNumberOfParameters != codedChromosome.size()){
            throw new RuntimeException("Wrong chromosome size!");
        }
        this.numberOfDoublesCoded = requiredNumberOfParameters;
        this.codedChromosome = codedChromosome;

    }

    public void calculateFitness(Function function) {
        Double[] parametersValues = convertBitsToDoubles();
        functionValue = function.calculate(parametersValues);
        fitness = function.getFitness(functionValue);
        if (function instanceof GradientFunction gradientFunction) {
            gradient = new GradientData(gradientFunction, parametersValues);
        }
    }

    private Double[] convertBitsToDoubles() {
        List<Double> list = new ArrayList<>();
        for (BitSet bitSet : codedChromosome) {
            if (bitSet.toLongArray().length != 1) {
                list.add(0.0);
                continue;
            }
            Double longBitsToDouble = BitSetUtils.toDouble(bitSet);
            list.add(longBitsToDouble);
        }
        codedChromosomeAsDoubleList = list;
        return list.toArray(new Double[0]);
    }

    private BitSet generateRandomValue(Constraint constraint) {
        double randomValue =
                RandomUtils.getRandomDoubleInRange(constraint.getLowerBound(), constraint.getUpperBound());
        return BitSetUtils.toFixedSizeBitset(randomValue);
    }

    private void validate(Integer numberOfDoublesCoded, List<Constraint> constraints) {
        validateNumberOfDoublesCoded(numberOfDoublesCoded, constraints);
    }

    private void validateNumberOfDoublesCoded(
            Integer numberOfDoublesCoded, List<Constraint> constraints) {
        if (numberOfDoublesCoded != constraints.size()) {
            throw new InvalidNumberOfConstraintsException(constraints.size(), numberOfDoublesCoded);
        }
    }

    @Override
    public String toString() {
        return "Chromosome{"
                + "fitness="
                + fitness
                + ", codedChromosomeAsDoubleList="
                + codedChromosomeAsDoubleList
                + '}';
    }

    public void moveByGradient(GradientFunction function, double rate) {
        Double[] functionArguments = convertBitsToDoubles();
        if(gradient == null){
            gradient = new GradientData(function, functionArguments);
        }

        for (int i = 0; i < functionArguments.length; i++) {
            functionArguments[i] -= rate * gradient.getGradient()[i];
            codedChromosome.set(i, BitSetUtils.toFixedSizeBitset(functionArguments[i]));
            convertBitsToDoubles();
        }
    }

}
