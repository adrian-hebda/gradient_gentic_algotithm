package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.*;

public class DifferencesBasedGradientCrossover implements IMultipointGradientSignBasedCrossover {

    @Override
    public List<Chromosome> performCrossover(List<Chromosome> parents) {

        List<Chromosome> chromosomesAfterCrossover = new LinkedList<>();
        for (int i = 0; i < parents.size(); i++) {
            Chromosome firstParent = chooseRandomParent(parents);
            chromosomesAfterCrossover.addAll(
                    createTwoNewChromosomes(firstParent, chooseBitsets(firstParent, parents)));
        }
        return chromosomesAfterCrossover;
    }


    private List<Chromosome> createTwoNewChromosomes(Chromosome parent1, List<BitSet> chosenOnes) {
        int numberOfBits = Double.SIZE * parent1.getNumberOfDoublesCoded();
        Boolean[] similarityVector = new Boolean[numberOfBits];

        // For each of double coded in chromosome there is a list of boolean representing differences in binary representation
        List<List<Boolean>> firstParentDifferences = new ArrayList<>();
        List<List<Boolean>> secondParentDifferences = new ArrayList<>();

        for (int i = 0; i < parent1.getNumberOfDoublesCoded(); i++) {
            firstParentDifferences.add(new ArrayList<>());
            secondParentDifferences.add(new ArrayList<>());
        }

        for (int i = 0; i < numberOfBits; i++) {
            if (parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE)
                    == chosenOnes.get(i / Double.SIZE).get(i % Double.SIZE)) {
                similarityVector[i] =
                        parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE);
            } else {
                similarityVector[i] = null;
                firstParentDifferences.get(i / Double.SIZE).add(
                        parent1.getCodedChromosome().get(i / Double.SIZE).get(i % Double.SIZE));
                secondParentDifferences.get(i / Double.SIZE).add(
                        chosenOnes.get(i / Double.SIZE).get(i % Double.SIZE));
            }
        }

        // crossover between differences

        List<List<Boolean>> firstParentDifferencesAfterCrossover = new ArrayList<>();
        List<List<Boolean>> secondParentDifferencesAfterCrossover = new ArrayList<>();
        for (int i = 0; i < parent1.getNumberOfDoublesCoded(); i++) {
            firstParentDifferencesAfterCrossover.add(new ArrayList<>());
            secondParentDifferencesAfterCrossover.add(new ArrayList<>());
        }

        for (int codedDouble = 0; codedDouble < firstParentDifferences.size(); codedDouble++) {
            int differenceListSize = firstParentDifferences.get(codedDouble).size();

            if (differenceListSize == 0) {
                // do nothing
            } else if (differenceListSize == 1) {
                /*
                 *  |x| -> |y|
                 *  |y| -> |x|
                 *
                 * */
                secondParentDifferencesAfterCrossover.get(codedDouble).add(firstParentDifferences.get(codedDouble).get(0));
                firstParentDifferencesAfterCrossover.get(codedDouble).add(secondParentDifferences.get(codedDouble).get(0));
            } else if (differenceListSize == 2) {
                /*
                 *  |x|x| -> |x|y|
                 *  |y|y| -> |y|x|
                 *
                 * */
                secondParentDifferencesAfterCrossover.get(codedDouble).addAll(
                        List.of(firstParentDifferences.get(codedDouble).get(0), secondParentDifferences.get(codedDouble).get(1)));
                firstParentDifferencesAfterCrossover.get(codedDouble).addAll(
                        List.of(secondParentDifferences.get(codedDouble).get(0), firstParentDifferences.get(codedDouble).get(1)));
            } else {
                int crossoverPoint = RandomUtils.getRandomIntInRange(1, differenceListSize - 1);

                firstParentDifferencesAfterCrossover.get(codedDouble).addAll(
                        firstParentDifferences.get(codedDouble).subList(0, crossoverPoint));
                firstParentDifferencesAfterCrossover.get(codedDouble).addAll(
                        secondParentDifferences.get(codedDouble).subList(crossoverPoint, secondParentDifferences.get(codedDouble).size()));
                secondParentDifferencesAfterCrossover.get(codedDouble).addAll(
                        secondParentDifferences.get(codedDouble).subList(0, crossoverPoint));
                secondParentDifferencesAfterCrossover.get(codedDouble).addAll(
                        firstParentDifferences.get(codedDouble).subList(crossoverPoint, firstParentDifferences.get(codedDouble).size()));
            }
        }


        var flatFirstParentDifferencesAfterCrossover = firstParentDifferencesAfterCrossover.stream().flatMap(Collection::stream).toList();
        var flatSecondParentDifferencesAfterCrossover = secondParentDifferencesAfterCrossover.stream().flatMap(Collection::stream).toList();

        List<BitSet> firstChild = new ArrayList<>();
        List<BitSet> secondChild = new ArrayList<>();

        for (int i = 0; i < parent1.getNumberOfDoublesCoded(); i++) {
            firstChild.add(new BitSet(Double.SIZE));
            secondChild.add(new BitSet(Double.SIZE));
        }

        int numberOfNotSimilar = 0;
        for (int i = 0; i < similarityVector.length; i++) {
            int doubleNumber = i / Double.SIZE;
            int numberInDouble = i % Double.SIZE;
            if (similarityVector[i] == Boolean.TRUE) {
                firstChild.get(doubleNumber).set(numberInDouble);
                secondChild.get(doubleNumber).set(numberInDouble);
            } else if (similarityVector[i] == Boolean.FALSE) {
                // do nothing - already set to false
            } else {

                firstChild
                        .get(doubleNumber)
                        .set(numberInDouble, flatFirstParentDifferencesAfterCrossover.get(numberOfNotSimilar));
                secondChild
                        .get(doubleNumber)
                        .set(numberInDouble, flatSecondParentDifferencesAfterCrossover.get(numberOfNotSimilar));
                numberOfNotSimilar++;
            }
        }
        return List.of(
                new Chromosome(parent1.getNumberOfDoublesCoded(), firstChild),
                new Chromosome(chosenOnes.size(), secondChild));
    }
}
