package by.epam.javaweb.glazko.multithreading.reader;

import by.epam.javaweb.glazko.multithreading.exception.FileReadingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOGGER = LogManager.getLogger(CustomerFileReader.class);

    /**
     * Reads a file with customer parameters (order window number, menu items and their quantities)
     * and returns a list with valid lines (not empty).
     * @param path path to the file with information.
     * @return list with valid lines.
     * @throws FileReadingException if there was an error while reading.
     */
    public List<String> read(String path) throws FileReadingException {
        try (Stream<String> lineStream = Files.lines(Paths.get(path))) {
            return lineStream.filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        throw new FileReadingException("Error while reading file!");
    }
}