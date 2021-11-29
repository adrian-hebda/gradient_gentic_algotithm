package pl.edu.agh.genetic.utils;

import java.util.Random;

public class RandomUtils {
  private static final Random RANDOM = new Random();

  public static double getRandomDoubleInRange(double fromInclusive, double toInclusive) {
    return fromInclusive + (toInclusive - fromInclusive) * RANDOM.nextDouble();
  }

  public static int getRandomIntInRange(int fromInclusive, int toExclusive){
    return fromInclusive + RANDOM.nextInt(toExclusive - fromInclusive);
  }

  private RandomUtils() {}
}
