package com.vodafone.warehouse.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * The type Utils.
 */
public class Utils {

    private Utils() {

    }

    /**
     * Random string string.
     *
     * @param useLetters the use letters
     * @param useNumbers the use numbers
     * @param length     the length
     * @return the string
     */
    public static String randomString(boolean useLetters, boolean useNumbers, int length) {

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
