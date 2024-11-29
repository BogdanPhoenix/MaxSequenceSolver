package org.example;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Data {
    private static final String EXTENSION = ".txt";
    private Data() {}

    public static List<String> getDataFromTXT(@NonNull String fileName) throws IOException, IllegalArgumentException {
        if(fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be blank");
        } else if(!fileName.endsWith(EXTENSION)) {
            throw new IllegalArgumentException("The file you provided has an unsupported extension");
        }

        Path path = Paths.get(fileName);
        return Files.readAllLines(path);
    }
}
