package by.epam.javaweb.glazko.multithreading.reader;

import by.epam.javaweb.glazko.multithreading.exception.FileReadingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerFileReader {

    private static final CustomerFileReader INSTANCE = new CustomerFileReader();

    public static CustomerFileReader getInstance() {
        return INSTANCE;
    }

    private CustomerFileReader() {}

    public List<String> read(String path) throws FileReadingException {
        try (Stream<String> lineStream = Files.lines(Paths.get(path))) {
            return lineStream.filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new FileReadingException("Error while reading file!");
    }
}