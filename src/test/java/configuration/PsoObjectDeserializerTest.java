package configuration;

// Jackson imports

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peoplespheres.model.PSObject;
import lombok.SneakyThrows;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.*;
import utils.TestUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class testing the serialization and deserialization of PSO objects from raw input and kafka streams
 */
@DisplayName("Unit tests about the deserializer of PSO objects")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PsoObjectDeserializerTest {
    // Creation of new mapper
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    ///////////
    // TESTS //
    ///////////
    @SneakyThrows
    @Test
    @Order(1)
    @DisplayName("1 - Unit test testing the deserialization of a PSO object from an input stream")
    void testDeserializeFromInputStream() {
        // Getting the input data to serialize and deserialize
        final InputStream psoInputStream = this.getClass().getClassLoader().getResourceAsStream("CompositionOfTransformations/Input.json");
        assertThat(psoInputStream).as("Input resource is missing (CompositionOfTransformations/Input.json) !!").isNotNull();
        final PSObject psoObject = OBJECT_MAPPER.readValue(psoInputStream, PSObject.class);
        psoInputStream.close();

        // Serializing the content of the PSO object right away and checking that it is exactly the same
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputStream, psoObject);

        // Checking that the input and the output are the same
        final BufferedReader inputReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("CompositionOfTransformations/Input.json"))));
        final BufferedReader outputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
        final long compareResults = TestUtils.compareFilesByByte(outputReader, inputReader);
        assertThat(compareResults).as("Content after deserialization and serialization should be the same").isEqualTo(-1L);
    }

    @Test
    @Order(2)
    @DisplayName("2 - Unit test testing the deserialization of incorrectly formatted JSON")
    void testDeserializeInvalidJson() {
        // Creating a runner that sneaky throws while deserializing the invalid JSON content
        final Runnable sneakyRunnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                final String invalidJsonInput = "{";
                final InputStream psoInputStream = new ByteArrayInputStream(invalidJsonInput.getBytes(StandardCharsets.UTF_8));
                OBJECT_MAPPER.readValue(psoInputStream, PSObject.class);
            }
        };

        // Running the sneaky runner and checking that it indeed throws
        final JsonProcessingException excThrown = Assertions.assertThrows(JsonProcessingException.class, () -> sneakyRunnable.run());
        assertThat(excThrown.getMessage()).as("Unexpected exception thrown following JSON parsing issue").startsWith("Unexpected end-of-input");
    }
}
