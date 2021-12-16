package pl.edu.agh.genetic.model;

import lombok.Data;
import pl.edu.agh.genetic.exceptions.InvalidNumberOfConstraintsException;
import pl.edu.agh.genetic.model.functions.Function;
import pl.edu.agh.genetic.model.functions.GradientFunction;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static java.lang.Math.*;

@Data
public class Chromosome implements Serializable {
  private Double fitness;
  // List of bitsets containing set of 64 bits representing double value.
  private List<BitSet> codedChromosome;
  private Integer numberOfDoublesCoded = -1;
  private List<Double> codedChromosomeAsDoubleList;
  private Double functionValue = Double.NaN;
  private double lenOfGradVector = -1;

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

  public void calculateFitness(Function function) {
    Double[] parametersValues = convertBitsToDoubles();
    functionValue = function.calculateResult(parametersValues);
    fitness = function.getFitness();
  }

  private Double[] convertBitsToDoubles() {
    List<Double> list = new ArrayList<>();
    for (BitSet bitSet : codedChromosome) {
      if (bitSet.toLongArray().length != 1) {
        list.add(0.0);
        continue;
      }
      Double longBitsToDouble = Double.longBitsToDouble(bitSet.toLongArray()[0]);
      list.add(longBitsToDouble);
    }
    codedChromosomeAsDoubleList = list;
    return list.toArray(new Double[0]);
  }

  private BitSet generateRandomValue(Constraint constraint) {
    double randomValue =
        RandomUtils.getRandomDoubleInRange(constraint.getLowerBound(), constraint.getUpperBound());
    return BitSet.valueOf(new long[] {Double.doubleToRawLongBits(randomValue)});
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
        + ", codedChrmosomeAsDoubleList="
        + codedChromosomeAsDoubleList
        + '}';
  }

  public void moveByGradient(GradientFunction function, double rate) {
    Double[] parameters = convertBitsToDoubles();
    Double[] gradient = function.calculateGradient(parameters);
    Double sumOfXiSquared = 0.0;
    for (int i = 0; i < parameters.length; i++) {
      sumOfXiSquared += pow(gradient[i] - parameters[i], 2);
      parameters[i] -= rate * gradient[i];
      codedChromosome.set(
          i, BitSet.valueOf(new long[] {Double.doubleToRawLongBits(parameters[i])}));
    }
    lenOfGradVector = sqrt(sumOfXiSquared);
  }
}
