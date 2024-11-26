package org.example;

import java.io.IOException;
import java.util.*;


public class App {
    private static final String PATH_FILE_MESSAGE = "Enter the path to the file (press Enter to exit): ";
    private static final String MENU_MESSAGE = """
            Methods of calculation (the last option will be selected by default):
            1. Iterative - there are no restrictions on the stack fullness, but slower than the Recursive method.
            2. Recursive - faster than Iterative, but a stack overflow may occur when a very large number of numbers are passed.
            Your choice: """;
    private static final String FILE_ERROR = "An error occurred while working with the file. Here is its text: “%s”. Please try again. %n";
    private static final String CHOOSE_DFS_ERROR = "The answer you entered is not a number. Please try again.";

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
                DFS dfs = chooseOption();

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

    private static DFS chooseOption() {
        while(true) {
            try {
                System.out.print(MENU_MESSAGE);
                String choice = scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case "1": return new IterativeDFS();
                    default: return new RecursionDFS();
                }
            } catch (Exception ex) {
                System.out.println(CHOOSE_DFS_ERROR);
            }
        }
    }
}
