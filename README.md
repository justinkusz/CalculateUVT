# CalculateUVT

## Getting Started

### Prerequisites
1. Have JDK 8 and JRE 8 installed:
    * [Oracle JDK 8 and JRE 8 Installation](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
    * (Alternatively) [OpenJDK 8 Installation](https://openjdk.java.net/install/)
2. Ensure successful installation:
    * In a terminal / command prompt: both `java -version` and `javac -version` should show version 1.8
    * If not, ensure that the location of the Java SDK is in your system PATH:
      * [Windows](https://docs.oracle.com/javase/8/docs/technotes/guides/install/windows_jdk_install.html#BABGDJFH)
      * OS X: 
        * Set `JAVA_HOME` environment variable:

          i. `export JAVA_HOME=$(/usr/libexec/java_home)`
          * Or on older versions of OS X: `export JAVA_HOME=/path/to/java_installation`

          ii. `export PATH=$PATH:$JAVA_HOME`
    * Linux:

      i. `export JAVA_HOME=/path/to/java_installation`
      
      ii. `export PATH=$PATH:$JAVA_HOME`

## Build and run
1. Clone the repo: `git clone https://github.com/justinkusz/CalculateUVT.git`
2. Compile the `.java` file `CalculateUVT.java`:
    ```
    javac CalculateUVT.java
    ```
3. Run the program.

    The program takes its **input as a single line of comma-separated fragments** in the form: 
    
    `[start1:end1],[start2:end2],[start3:end3]...`

    and **outputs a message displaying the UVT**:

    `UVT for given fragments: 0:18:39.752 (1119752 ms)`

    The fragments are **read from standard input**. This can be done by **supplying the input manually**, **redirecting from a file**, or by **piping the output of another command** to the program. For example,

    **Manually**:

    ```
    java CalculateUVT <press enter>
    [893622:1429878],[5187338:5257755],[44359:270715],[317824:604547] <press enter>
    UVT for given fragments: 0:18:39.752 (1119752 ms)
    ```

    **Piping output of another command**: 

    ```
    echo "[893622:1429878],[5187338:5257755],[44359:270715],[317824:604547]" | java CalculateUVT
    UVT for given fragments: 0:18:39.752 (1119752 ms)
    ```

    **Reading from a file**:

    ```
    java CalculateUVT < ./input_file 
    UVT for given fragments: 0:18:39.752 (1119752 ms)
    ```

  ## Running Tests

  Tests for the `UVT` algorithm can be found in `TestCalculateUVT.java`.
  The testing framework used is [JUnit](https://junit.org/junit4/).
  
  For convenience, the JAR files needed for JUnit to run are included in the `lib` directory of this repository. They are:
  
  `lib/junit-4.13-beta-3.jar`
  
  `lib/hamcrest-core-1.3.jar`

  ### To run the tests defined in `TestCalculateUVT.java`:
    
  1. First, **compile both `TestCalculateUVT.java` and `TestRunner.java`**.
    
      **Make sure to pass the class path of the JUnit JAR files** to the `javac` command using the `-cp` (or `-classpath`) switch. When setting the classpath, be sure to also **include the current directory**, separated by a colon (`:`) on Linux/OS X, or a semicolon (`;`) on Windows.

      For example:

      ```
      javac -cp /path/to/CalculateUVT/lib/*:. TestCalculate.java TestRunner.java
      ```
  2. Next, **run the `TestRunner`**, again making sure to **specify the classpath**:

      ```
      java -cp /path/to/CalculateUVT/lib/*:. TestRunner
      true
      ```

      If all tests defined in `TestCalculateUVT.java` **successfully pass**, the `TestRunner` will simply return `true`.

      If any of the **tests fail**, `TestRunner` will return `false`, along with some details of the test(s) that failed. For example:

      ```
      TestOverlappingFragments(TestCalculateUVT): expected:<8> but was:<49>
      false
      ```

      In the case above, the test `TestOverlappingFragments` expected the returned value of the `UVT` algorith to be `8`, but the actual value returned was `49`.