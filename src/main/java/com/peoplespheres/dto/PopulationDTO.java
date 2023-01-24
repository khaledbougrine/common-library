package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/** Projection of a population */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Target population DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PopulationDTO implements Serializable {
    @Schema(description = "The unique technical identifier of the population")
    @NotNull(message = "{population.id.not-null}")
    @PositiveOrZero(message = "{population.id.positive}")
    Long id;

    @Schema(description = "The name given to the population")
    @NotBlank(message = "{population.id.not-null}")
    String name;

    @Schema(description = "The alias given to the population", nullable = true)
    @Nullable
    String alias;
}
