package org.example;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        try {
            List<String> numbers = Data.getDataFromTXT("src/main/resources/numbers.txt");
            String result = Algorithm.getLargestDigitalPuzzle(numbers);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
