package pl.edu.agh.genetic.utils;

import java.util.BitSet;

public class BitSetUtils {
    public static BitSet toFixedSizeBitset(Double value){
        BitSet randomBitset = BitSet.valueOf(new long[]{Double.doubleToRawLongBits(value)});

        BitSet targetBitset = new BitSet(Double.SIZE);
        for(int i = 0; i < randomBitset.size(); ++i) {
            if (randomBitset.get(i)) {
                targetBitset.set(i);
            }
        }
        return targetBitset;
    }

    public static BitSet toFixedSizeBitset(BitSet bitSet){

        BitSet targetBitset = new BitSet(Double.SIZE);
        for(int i = 0; i < bitSet.size(); ++i) {
            if (bitSet.get(i)) {
                targetBitset.set(i);
            }
        }
        return targetBitset;
    }

    public static Double toDouble(BitSet bitSet){
       return Double.longBitsToDouble(bitSet.toLongArray()[0]);
    }

    public static boolean isUndefined(BitSet bitSet){
        return (bitSet.get(52, 63).cardinality() == 11 && bitSet.get(0, 52).cardinality() == 0) || (bitSet.get(52, 63).cardinality() == 11 && bitSet.get(0, 52).cardinality() != 0);
    }
}
