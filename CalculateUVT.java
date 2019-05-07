import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculateUVT {

  // Takes an array of viewed fragments, merges any overlapping fragments,
  // and returns the "unique view time" for those fragments
  public static int UVT(Fragment[] fragments) {

    // Begin by sorting fragments in descending order of start times.
    // Sorted array allows for faster merging

    // Sorting by descending start times allows for merging "in-place",
    // avoiding the need for allocating extra memory
    Arrays.sort(fragments, new SortFragmentsDescending());

    // Index to keep track of lest element of merged range of the array
    int j = 0;

    for (int i = 0; i < fragments.length; i++) {

      // Throw an error if the start or end time is less than 0
      if (fragments[i].startTime < 0 || fragments[i].endTime < 0) {
        throw new ArithmeticException("Input Error: start and end times must not be less than 0 " + fragments[i]);
      }

      // Throw an error if the start time is greater than the end time
      if (fragments[i].startTime > fragments[i].endTime) {
        throw new ArithmeticException("Input Error: start time must not be greater than end time " + fragments[i]);
      }

      // If the fragment end time is greater than the previous start time,
      // the fragments overlap
      if (j != 0 && fragments[j - 1].startTime <= fragments[i].endTime) {
        // Merge fragment with any previous overlapping fragments
        while (j != 0 && fragments[j - 1].startTime <= fragments[i].endTime) {
          fragments[j - 1].endTime = Math.max(fragments[j - 1].endTime, fragments[i].endTime);
          fragments[j - 1].startTime = Math.min(fragments[j - 1].startTime, fragments[i].startTime);
          j--;
        }
      } else {
        // Fragment does not overlap with previous; include in range of array
        // to be used for calculating view time
        fragments[j] = fragments[i];
      }
      j++;
    }

    int uvt = 0;

    // Resulting range [0..j-1] contains fragments to be used for calculating
    // the UVT. Loop through the fragments and accumulate their view times
    for (int i = 0; i < j; i++) {
      uvt += fragments[i].viewTime();
    }

    return uvt;
  }

  public static void main(String[] args) {
    // Read in a single line of comma-separated fragments in the form:
    // [start1:end1],[start2:end2],[start3:end3]...
    Scanner in = new Scanner(System.in);

    // Read fragment timestamps into array
    String[] input = in.nextLine().trim().split(",");
    in.close();

    // Initialize array of fragment objects
    Fragment[] fragments = new Fragment[input.length];

    // Pattern to check for valid input ([digits:digits])
    String regex = "\\[(\\d+):(\\d+)\\]";
    Pattern pattern = Pattern.compile(regex);

    // Loop through input timestamp array
    for (int i = 0; i < input.length; i++) {
      // Check input for matching pattern
      Matcher matcher = pattern.matcher(input[i]);

      // Pattern not found in input. Display error message and quit.
      if (!matcher.find()) {
        String errMsg = "Input should be a single line of "
            + "comma-separated positive integer fragment times in the form: "
            + "[start1:end1],[start2:end2],[start3:end3]...";
        in.close();
        System.out.printf("Input error: %s\n%s\n", input[i], errMsg);
        return;
      }

      // Pattern found. Construct new fragment from input times
      int start = Integer.parseInt(matcher.group(1));
      int end = Integer.parseInt(matcher.group(2));
      fragments[i] = new Fragment(start, end);
    }

    // Calculate UVT from constructed fragment array
    int uvt = 0;

    try {
      uvt = UVT(fragments);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

    // Display UVT for fragments in human readable format
    int hours = ((uvt / (1000 * 60 * 60)) % 24);
    int mins = ((uvt / (1000 * 60)) % 60);
    int secs = (uvt / 1000) % 60;
    int ms = uvt % 1000;
    System.out.printf("UVT for given fragments: %d:%d:%d.%d (%d ms)\n", hours, mins, secs, ms, uvt);
  }

}

class SortFragmentsDescending implements Comparator<Fragment> {
  // Sort in descending order of fragment start time
  public int compare(Fragment a, Fragment b) {
    return b.startTime - a.startTime;
  }
}

class Fragment {
  int startTime, endTime;

  Fragment(int start, int end) {
    this.startTime = start;
    this.endTime = end;
  }

  int viewTime() {
    return this.endTime - this.startTime;
  }

  public String toString() {
    return "[" + this.startTime + ":" + this.endTime + "]";
  }
}