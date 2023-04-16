package pl.edu.agh.genetic.model;

import lombok.Data;
import lombok.Getter;
import pl.edu.agh.genetic.exceptions.InvalidNumberOfConstraintsException;
import pl.edu.agh.genetic.model.functions.Function;
import pl.edu.agh.genetic.model.functions.GradientFunction;
import pl.edu.agh.genetic.utils.BitSetUtils;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

@Getter
public class Chromosome {
    private Double fitness;
    // List of bitsets containing set of 64 bits representing double value.
    private List<BitSet> codedChromosome;
    private Integer numberOfDoublesCoded;
    private Double functionValue = Double.NaN;
    private GradientData gradient;


    public Chromosome(List<Constraint> constraints, int requiredNumberOfParameters) {
        this.numberOfDoublesCoded = requiredNumberOfParameters;
        this.codedChromosome = new ArrayList<>(numberOfDoublesCoded);

        validate(numberOfDoublesCoded, constraints);

        for (int i = 0; i < numberOfDoublesCoded; i++) {
            this.codedChromosome.add(generateRandomBitset(constraints.get(i)));
        }
    }

    public Chromosome(int requiredNumberOfParameters, List<BitSet> codedChromosome) {
        if (requiredNumberOfParameters != codedChromosome.size()) {
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
            Double longBitsToDouble = BitSetUtils.toDouble(bitSet);
            list.add(longBitsToDouble);
        }

        return list.toArray(new Double[0]);
    }

    private BitSet generateRandomBitset(Constraint constraint) {
        Double randomValue =
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
                + Arrays.toString(convertBitsToDoubles())
                + '}';
    }

    public void moveByGradient(GradientFunction function, Double rate) {
        Double[] functionArguments = convertBitsToDoubles();
        if (gradient == null) {
            gradient = new GradientData(function, functionArguments);
        }

        for (int i = 0; i < functionArguments.length; i++) {
            functionArguments[i] -= rate * gradient.getGradient()[i];
            codedChromosome.set(i, BitSetUtils.toFixedSizeBitset(functionArguments[i]));
            convertBitsToDoubles();
        }
    }

    public Double[] getCodedChromosomeAsDoubleList() {
        return convertBitsToDoubles();
    }
}
