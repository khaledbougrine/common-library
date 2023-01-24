package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Configuration
@Slf4j
public class AdapterHelper {

    private ObjectMapper objectMapper;

    public AdapterHelper(@Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getEntities_Helper(String path, Class className) {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        T entities;
        try {
            entities = objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(Collection.class, className));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (T) entities;
    }

    public <T> T getEntity_Helper(String path, Class className) {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        T entity;
        try {
            entity = (T) objectMapper.readValue(inputStream, className);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }


}
