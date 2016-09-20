package com.ti.scs.parampermutator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author a0284021
 */
public class ParamPermutatorTest {

    @Test
    public void testSomeMethod() {
        final int[] test = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final int sum = IntStream.of(test).sum();
        final List<int[]> permList = ParamPermutator.permutate(test);

        assertEquals("# of expected permutated values = n * (n-1)",
                test.length * (test.length - 1), permList.size());

        final String testStr = convertIntArToStr(test);

        permList.stream()
                .forEach(x -> {
                    assertEquals("expect permutated values have same sum with param sum",
                            sum, IntStream.of(x).sum());
                    final String permStr = convertIntArToStr(x);

                    assertFalse("permutated values should not be same with test values",
                            testStr.equals(permStr));
                });

    }

    private String convertIntArToStr(final int[] test) {
        return IntStream.of(test).boxed()
                .flatMap(y -> Stream.of(y.toString())).collect(Collectors.joining());
    }

}
