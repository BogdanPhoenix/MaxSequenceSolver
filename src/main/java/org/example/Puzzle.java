package org.example;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Puzzle {
    private static final String REGX_SPLIT_NUM = "(.{2})(.*)(.{2})";
    private static final int MIN_LENGTH_NUM = 4;
    private static final String CONCAT_EXCEPTION_MESSAGE = "In the collection you provided, the following two puzzles: \"%s\" and \"%s\" cannot form a sequence. A sequence can be formed if the last two digits of the first number match the first two digits of the next number.";

    private final String middenNum;
    @Getter
    private final String startNum;
    @Getter
    private final String endNum;

    public static String concatPuzzles(@NonNull Collection<Puzzle> puzzles) throws IllegalArgumentException {
        if(puzzles.isEmpty()) {
            return "";
        }

        int index = 0;
        StringBuilder result = new StringBuilder();
        Iterator<Puzzle> iterator = puzzles.iterator();
        Puzzle previousPuzzle = null;
        Puzzle currentPuzzle;

        while (iterator.hasNext()) {
            currentPuzzle = iterator.next();

            if(index == 0) {
                result.append(currentPuzzle);
                previousPuzzle = currentPuzzle;
                index++;
                continue;
            }

            if(!previousPuzzle.endNum.equals(currentPuzzle.startNum)) {
                throw new IllegalArgumentException(String.format(CONCAT_EXCEPTION_MESSAGE, previousPuzzle.endNum, currentPuzzle.startNum));
            }

            result.append(currentPuzzle.middenNum).append(currentPuzzle.endNum);

            previousPuzzle = currentPuzzle;
            index++;
        }

        return result.toString();
    }

    public Puzzle(String number) throws NullPointerException, IllegalArgumentException {
        checkNumber(number);

        Queue<String> groups = splitNumber(number);

        startNum = groups.poll();
        middenNum = groups.poll();
        endNum = groups.poll();
    }

    private void checkNumber(@NonNull String number) throws NullPointerException, IllegalArgumentException {
        if(number.isBlank()) {
            throw new IllegalArgumentException("Number cannot be blank");
        } else if(number.length() < MIN_LENGTH_NUM) {
            throw new IllegalArgumentException("Number must be at least 4 characters");
        } else if(!isOnlyDigits(number)) {
            throw new IllegalArgumentException("Number must be only digits");
        }
    }

    private boolean isOnlyDigits(String number) {
        return number.chars()
                .allMatch(Character::isDigit);
    }

    private Queue<String> splitNumber(String number) {
        Queue<String> splitNumber = new LinkedList<>();

        Pattern pattern = Pattern.compile(REGX_SPLIT_NUM);
        Matcher matcher = pattern.matcher(number);

        if(!matcher.matches()) {
            throw new IllegalArgumentException("Number must contain digits");
        }

        for(int i = 1; i <= matcher.groupCount(); i++) {
            splitNumber.add(matcher.group(i));
        }

        return splitNumber;
    }

    public boolean isNext(Puzzle puzzle) {
        return this.endNum.equals(puzzle.startNum);
    }

    public long getLong() {
        return Long.parseLong(this.toString());
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
