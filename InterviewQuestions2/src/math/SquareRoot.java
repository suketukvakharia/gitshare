package math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Implementation of square root
 */
public class SquareRoot
{

  public static final double ERROR_MARGIN = 0.0001;

  /**
   * Generic case would be square root of float, but for now focus on int.
   * @param n
   * @return
   */
  private static double squareRoot(int n) {

    if (n <= 0) {
      throw new IllegalArgumentException("negative");
    }

    double upperBound = (double) n;
    double lowerBound = 1d;
    double midPoint = (upperBound + lowerBound) /2;
    double square = midPoint * midPoint;

    while (Math.abs(square - n) > ERROR_MARGIN) {

      if (square > n) {
        upperBound = midPoint;
      }
      else {
        lowerBound = midPoint;
      }
      midPoint = (upperBound + lowerBound) /2;
      square = midPoint * midPoint;
    }
    return midPoint;
  }

  @Test
  public void test() {
    for (int i = 1; i < 10000; i++) {
      testSqrt(i);
    }
  }

  private void testSqrt(int testNum)
  {
    assertEquals(Math.sqrt(testNum), squareRoot(testNum), ERROR_MARGIN);
  }
}
