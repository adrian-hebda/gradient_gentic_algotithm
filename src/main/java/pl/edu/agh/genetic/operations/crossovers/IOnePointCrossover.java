package pl.edu.agh.genetic.operations.crossovers;

import java.util.BitSet;

public interface IOnePointCrossover extends Crossover {
    default BitSet createMixedBitset(
            BitSet firstChromosomeBitSet, BitSet secondChromosomeBitSet, int crossoverPointInDouble) {
        BitSet newBitSet = new BitSet(Double.SIZE);
        for (int bitIndex = 0; bitIndex < Double.SIZE; bitIndex++) {
            if (bitIndex < crossoverPointInDouble) {
                setBitDependingOnOtherBitset(firstChromosomeBitSet, bitIndex, newBitSet);
            } else {
                setBitDependingOnOtherBitset(secondChromosomeBitSet, bitIndex, newBitSet);
            }
        }
        return newBitSet;
    }
}
