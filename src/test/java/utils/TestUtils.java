package utils;

// Jackson imports

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class containing utilities usable for the unit tests
 */
@Slf4j
public class TestUtils {
    /**
     * Mapper used for the serialization and deserialization of JSON formatted streams
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Bean
    public ObjectMapper defaultMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * Performing a direct line by line comparison between 2 files. It is generally used in this UT to compare the result
     * obtained after the transformation and its associated expected result.
     *
     * @param resultReader         The reader on the first file (generally result of the transformation).
     * @param expectedResultReader The reader on the second file (generally the expected result for the transformation).
     * @return long - The result of the comparison line by line between the two files. Result is -1 if the files are identical,
     * otherwise the number of the line where the first difference between the two files appears.
     * @throws IOException Exception thrown in case of issue to read one of the files processed during this comparison.
     */
    public static long compareFilesByByte(final BufferedReader resultReader, final BufferedReader expectedResultReader) throws IOException {
        long lineNumber = 1;
        String lineResult = "";
        String lineExpectedResult;
        while ((lineExpectedResult = expectedResultReader.readLine()) != null) {
            lineNumber++;
            lineResult = resultReader.readLine();
            if (!lineExpectedResult.equals(lineResult)) {
                log.error("The comparison between the transformation and the expected result differs at line {}", lineNumber);
                log.error("Result  : {}", lineResult);
                log.error("Expected: {}", lineExpectedResult);
                return lineNumber;
            }
        }

        // Case where result and expected result are matching -> Successful
        if (resultReader.readLine() == null) {
            log.info("Mapping transformation PSO OUT -> client is successful ({})", lineNumber);
            return -1;
        }
        log.error("The comparison between the transformation and the expected result differs at line {}", lineNumber);
        log.error("Result  : {}", lineResult);
        log.error("Expected: {}", lineExpectedResult);
        return lineNumber;
    }

}
