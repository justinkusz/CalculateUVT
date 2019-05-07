import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestCalculateUVT {

  @Test
  // Test for inputs where no fragments overlap
  public void TestNonOverlappingFragments() {
    Fragment[] fragments = new Fragment[4];
    fragments[0] = new Fragment(8, 10);
    fragments[1] = new Fragment(1, 3);
    fragments[2] = new Fragment(5, 6);
    fragments[3] = new Fragment(12, 16);

    assertEquals(9, CalculateUVT.UVT(fragments));
  }

  @Test
  // Test for inputs where fragments do overlap
  public void TestOverlappingFragments() {
    Fragment[] fragments = new Fragment[4];
    fragments[0] = new Fragment(1, 5);
    fragments[1] = new Fragment(8, 10);
    fragments[2] = new Fragment(7, 8);
    fragments[3] = new Fragment(4, 6);

    assertEquals(8, CalculateUVT.UVT(fragments));
  }

  @Test
  // Test for inputs including only a single fragment
  public void TestSingleFragment() {
    Fragment[] fragments = new Fragment[1];
    fragments[0] = new Fragment(0, 20);

    assertEquals(20, CalculateUVT.UVT(fragments));
  }

  @Test(expected = ArithmeticException.class)
  // Test for inputs including a fragment with a start time
  // that is greater than its end time
  public void TestStartGreaterThanEnd() {
    Fragment[] fragments = new Fragment[4];
    fragments[0] = new Fragment(5, 1); // Erroneous fragment
    fragments[1] = new Fragment(8, 10);
    fragments[2] = new Fragment(7, 8);
    fragments[3] = new Fragment(4, 6);

    CalculateUVT.UVT(fragments);
  }

  @Test(expected = ArithmeticException.class)
  // Test for inputs including a fragment with a start time
  // or end time that is less than 0
  public void TestStartOrEndTimeLessThanZero() {
    Fragment[] fragments = new Fragment[4];
    fragments[0] = new Fragment(1, 5);
    fragments[1] = new Fragment(8, 10);
    fragments[2] = new Fragment(7, -8); // Erroneous fragment
    fragments[3] = new Fragment(4, 6);

    CalculateUVT.UVT(fragments);
  }
}