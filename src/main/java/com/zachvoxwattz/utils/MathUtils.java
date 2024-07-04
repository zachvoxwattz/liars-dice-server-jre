package com.zachvoxwattz.utils;

import io.netty.util.internal.ThreadLocalRandom;

/**
 * All maths related operations.
 * 
 * Shared across the entire program for ease of access.
 */
public class MathUtils {
    /**
     * Generates a random integer with the given interval.
     * @param min Minimum possible number in interval.
     * @param max Maximum possible number in interval.
     * @return Randomly generated {@code int}.
     */
    public static int getRandomIntFromInterval(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
