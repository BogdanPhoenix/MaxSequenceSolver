package org.example;

import java.io.IOException;
import java.util.*;


public class App {
    private static final String PATH_FILE_MESSAGE = "Enter the path to the file (press Enter to exit): ";
    private static final String FILE_ERROR = "An error occurred while working with the file. Here is its text: “%s”. Please try again. %n";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args ) {
        while (true) {
            System.out.print(PATH_FILE_MESSAGE);
            String path = scanner.nextLine();
            System.out.println();

            if(path.isBlank()) {
                break;
            }

            try {
                List<String> data = getData(path);
                DFS dfs = new RecursionDFS();

                System.out.println("Starts the calculation. Please wait a moment...\n");
                List<Puzzle> result = dfs.getLargestDigitalPuzzle(data);
                System.out.printf("The result: %s.%n%n", Puzzle.concatPuzzles(result));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Exit...");
    }

    private static List<String> getData(String path) throws RuntimeException {
        try {
            return Data.getDataFromTXT(path);
        } catch (IOException e) {
            throw new RuntimeException(String.format(FILE_ERROR, path), e);
        }
    }
}
