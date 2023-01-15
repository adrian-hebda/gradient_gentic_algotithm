package pl.edu.agh.genetic.operations.crossovers;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public interface IMultiPointCrossover extends Crossover {

    default Chromosome createChromosome(
            List<BitSet> firstChromosome,
            List<BitSet> secondChromosome, List<Integer> crossoverPoints) {

        List<BitSet> newCodedChromosome = new ArrayList<>(firstChromosome.size());
        for (int doubleNumber = 0; doubleNumber < firstChromosome.size(); doubleNumber++) {
            BitSet mixedBitset =
                    createMixedBitset(
                            firstChromosome.get(doubleNumber),
                            secondChromosome.get(doubleNumber),
                            crossoverPoints.get(doubleNumber));
            newCodedChromosome.add(mixedBitset);
        }
    return new Chromosome(firstChromosome.size(), newCodedChromosome);
    }
}
