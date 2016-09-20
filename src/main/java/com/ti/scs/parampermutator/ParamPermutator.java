package com.ti.scs.parampermutator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author a0284021
 */
public class ParamPermutator {

    private static int DELTA = 1;

    public static void main(String[] args) {

        final int[] params = {1, 2, 3, 4};

        final List<int[]> perms = ParamPermutator.permutate(params);

        final List<Integer> origList = IntStream.of(params).boxed().collect(Collectors.toList());
        System.out.println("origList = " + origList);

        perms.stream()
                .forEach((int[] x) -> {
                    final List<Integer> perm = IntStream.of(x).boxed().collect(Collectors.toList());
                    System.out.println("perm = " + perm);
                });

        System.out.println("perms.size = " + perms.size());
    }

    public static void setDELTA(int DELTA) {
        if (DELTA == 0) {
            throw new IllegalArgumentException("Delta cannot be 0");
        }
        ParamPermutator.DELTA = Math.abs(DELTA);
    }

    public static List<int[]> permutate(int[] params) {
        // Java streams to enable easy parallel execution
        return IntStream.range(0, params.length)
                .parallel()
                // build list of perms per param
                .mapToObj(i -> {
                    // for each params set a minused param except for added param
                    // then build permed array
                    return IntStream.range(0, params.length)
                    .parallel()
                    .filter(j -> j != i)
                    .mapToObj(j -> {
                        // build permed param
                        final int[] perm = new int[params.length]; // build perm
                        perm[i] = params[i] + DELTA;
                        perm[j] = params[j] - DELTA;
                        // copy non permed values
                        IntStream.range(0, params.length)
                        .filter(k -> k != i && k != j)
                        .forEach(k -> {
                            perm[k] = params[k];
                        });

                        assert perm.length > 0;
                        assert IntStream.of(perm).sum() == IntStream.of(params).sum();
                        return perm;
                    }).collect(Collectors.toList());
                })
                // flatten lists
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
    }
}
