package pl.edu.agh.genetic.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {
  public static final Random RANDOM = new Random();

  public static Double getRandomDoubleInRange(Double fromInclusive, Double toInclusive) {
    return fromInclusive + (toInclusive - fromInclusive) * RANDOM.nextDouble();
  }

  public static int getRandomIntInRange(int fromInclusive, int toExclusive){
    return fromInclusive + RANDOM.nextInt(toExclusive - fromInclusive);
  }
}
