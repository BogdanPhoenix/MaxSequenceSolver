package org.example;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a puzzle constructed from a string of digits, split into three parts:
 * start, middle, and end. Provides utilities for validation, splitting, and concatenation of puzzles.
 */
public final class Puzzle {
    private static final String REGEX_SPLIT_NUM = "(.{2})(.*)(.{2})";
    private static final int MIN_LENGTH_NUM = 4;
    private static final String CONCAT_EXCEPTION_MESSAGE = "In the collection you provided, the following two puzzles: \"%s\" and \"%s\" cannot form a sequence. A sequence can be formed if the last two digits of the first number match the first two digits of the next number.";

    private final String middenNum;
    @Getter
    private final String startNum;
    @Getter
    private final String endNum;

    /**
     * Constructs a Puzzle object by splitting a valid number string into start, middle, and end parts.
     *
     * @param number a string representing a number to create the Puzzle from.
     * @throws NullPointerException if the number is null.
     * @throws IllegalArgumentException if the number is invalid.
     */
    public Puzzle(String number) throws NullPointerException, IllegalArgumentException {
        validateNumber(number);

        Queue<String> groups = splitNumber(number);
        startNum = groups.poll();
        middenNum = groups.poll();
        endNum = groups.poll();
    }

    /**
     * Concatenates a sequence of puzzles into a single string if they form a valid sequence.
     *
     * @param puzzles a collection of Puzzle objects to concatenate.
     * @return a concatenated string representation of the puzzles.
     * @throws IllegalArgumentException if the puzzles do not form a valid sequence.
     */
    public static String concatPuzzles(@NonNull Collection<Puzzle> puzzles) throws IllegalArgumentException {
        if (puzzles.isEmpty()) {
            return "";
        }

        Iterator<Puzzle> iterator = puzzles.iterator();
        Puzzle previousPuzzle = iterator.next();
        StringBuilder result = new StringBuilder(previousPuzzle.toString());

        while (iterator.hasNext()) {
            Puzzle currentPuzzle = iterator.next();
            if (!previousPuzzle.endNum.equals(currentPuzzle.startNum)) {
                throw new IllegalArgumentException(String.format(CONCAT_EXCEPTION_MESSAGE, previousPuzzle, currentPuzzle));
            }

            result.append(currentPuzzle.middenNum).append(currentPuzzle.endNum);
            previousPuzzle = currentPuzzle;
        }

        return result.toString();
    }

    /**
     * Validates the input number string for proper length and format.
     *
     * @param number the number string to validate.
     * @throws IllegalArgumentException if the number is blank, too short, or contains non-digit characters.
     */
    private void validateNumber(@NonNull String number) throws NullPointerException, IllegalArgumentException {
        if(number.isBlank()) {
            throw new IllegalArgumentException("Number cannot be blank");
        } else if(number.length() < MIN_LENGTH_NUM) {
            throw new IllegalArgumentException("Number must be at least 4 characters");
        } else if(!isOnlyDigits(number)) {
            throw new IllegalArgumentException("Number must be only digits");
        }
    }

    /**
     * Checks if the given string contains only digit characters.
     *
     * @param number the number string to check.
     * @return true if the string contains only digits, false otherwise.
     */
    private boolean isOnlyDigits(String number) {
        return number.chars()
                .allMatch(Character::isDigit);
    }

    /**
     * Splits a valid number string into three parts: start, middle, and end.
     *
     * @param number the number string to split.
     * @return a queue containing the split parts in order.
     * @throws IllegalArgumentException if the string does not match the expected format.
     */
    private Queue<String> splitNumber(String number) {
        Matcher matcher = Pattern.compile(REGEX_SPLIT_NUM).matcher(number);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid number format. Number must match the expected pattern.");
        }

        Queue<String> splitNumber = new LinkedList<>();
        for(int i = 1; i <= matcher.groupCount(); i++) {
            splitNumber.add(matcher.group(i));
        }

        return splitNumber;
    }

    /**
     * Checks if the current puzzle can be followed by another puzzle to form a sequence.
     *
     * @param puzzle the puzzle to check for sequence continuity.
     * @return true if the current puzzle's end matches the given puzzle's start, false otherwise.
     */
    public boolean isNext(Puzzle puzzle) {
        return this.endNum.equals(puzzle.startNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Puzzle puzzle)) return false;
        return Objects.equals(startNum, puzzle.startNum) && Objects.equals(middenNum, puzzle.middenNum) && Objects.equals(endNum, puzzle.endNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startNum, middenNum, endNum);
    }

    @Override
    public String toString() {
        return startNum + middenNum + endNum;
    }
}
