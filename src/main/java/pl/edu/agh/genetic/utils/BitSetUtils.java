package pl.edu.agh.genetic.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.BitSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BitSetUtils {
    public static BitSet toFixedSizeBitset(Double value){
        BitSet randomBitset = BitSet.valueOf(new long[]{Double.doubleToRawLongBits(value)});
        return toFixedSizeBitset(randomBitset);
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
    if (bitSet.isEmpty()){
        return +0.0;
    }
    return Double.longBitsToDouble(bitSet.toLongArray()[0]);
    }

    public static boolean isUndefined(BitSet bitSet){
        return !Double.isFinite(toDouble(bitSet));
    }
}
