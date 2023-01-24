package configuration;

// Jackson imports
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// Lombok imports
import dto.AdapterHelper;
import lombok.RequiredArgsConstructor;

// Spring imports
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

// Javax imports
import javax.validation.constraints.NotNull;

// J2EE imports
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        final TomcatServletWebServerFactory tomcatServerFactory = new TomcatServletWebServerFactory();
        tomcatServerFactory.setPort(9096);
        return tomcatServerFactory;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("PsoCommonDefaultServlet");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.extendMessageConverters(converters);
        Optional<HttpMessageConverter<?>> jacksonConverterOpt = converters.stream().filter(AbstractJackson2HttpMessageConverter.class::isInstance).findFirst();
        if ( ! jacksonConverterOpt.isPresent()) {
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
            ObjectMapper objectMapper = converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper = objectMapper.enable(SerializationFeature.CLOSE_CLOSEABLE);
            objectMapper = objectMapper.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
            objectMapper = objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
            objectMapper = objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            objectMapper = objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper = objectMapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
            objectMapper = objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
            objectMapper = objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            objectMapper = objectMapper.registerModule(new JavaTimeModule());
            converter.setObjectMapper(objectMapper);
            converters.add(converter);
        }
        else {
            AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) jacksonConverterOpt.get();
            ObjectMapper objectMapper = converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper = objectMapper.enable(SerializationFeature.CLOSE_CLOSEABLE);
            objectMapper = objectMapper.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
            objectMapper = objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
            objectMapper = objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            objectMapper = objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper = objectMapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
            objectMapper = objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
            objectMapper = objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            objectMapper = objectMapper.registerModule(new JavaTimeModule());
            converter.setObjectMapper(objectMapper);
        }
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper(List<HttpMessageConverter<?>> converters){
        extendMessageConverters(converters);
        final Optional<HttpMessageConverter<?>> jacksonConverterOpt = converters.stream().filter(AbstractJackson2HttpMessageConverter.class::isInstance).findFirst();
        if (jacksonConverterOpt.isPresent()) {
            // Getting the Jackson JSON/XML serializer/deserializer converter and managing the pretty print settings
            final AbstractJackson2HttpMessageConverter jacksonConverter = (AbstractJackson2HttpMessageConverter) jacksonConverterOpt.get();
            log.info("Customized jackson mapper");
            return jacksonConverter.getObjectMapper();
        }
        log.info("Default jackson mapper");
        return new ObjectMapper();
    }

    @Bean(name = "adapterHelper")
    public AdapterHelper adapterHelper(@NotNull final ObjectMapper objectMapper) {
        return new AdapterHelper(objectMapper);
    }
}
