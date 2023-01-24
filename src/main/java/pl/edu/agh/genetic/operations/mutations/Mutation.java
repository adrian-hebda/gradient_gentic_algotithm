package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.utils.BitSetUtils;

import java.util.BitSet;

public interface Mutation{
    void performMutation(Population population);

    default void flip(BitSet bitSet, int bitToFlipIndex) {
        bitSet.flip(bitToFlipIndex);
        if (BitSetUtils.isUndefined(bitSet)) {
            bitSet.flip(bitToFlipIndex);
        }
    }
}
