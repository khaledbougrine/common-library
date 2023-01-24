package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/** DTO to transport and expose data for a mapping transformation */
@Getter
@Setter
@Schema(description = "Mapping DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformationDTO implements Serializable {
    @Schema(description = "TransformationDomain technical ID (ex:1)", example = "1")
    @NotNull(message="{transformation.id.not-null}")
    @PositiveOrZero(message="{transformation.id.positive}")
    private Long id;

    @Schema(description = "The displayed name of this mapping transformation (ex: 'Uppercase')", example = "Uppercase")
    @Nullable
    private String name;

    @Schema(description = "The definition of transformation rule associated to this mapping transformation")
    @Valid
    private TransformationRuleDTO transformationRule;

    @Schema(description = "A pipe separated list of parameters of this mapping transformation")
    @Nullable
    private String parameters;

    @Schema(description = "The position of application of the associated transformation within the mapping operation as it is not idempotent (ex:1)", example = "1")
    @NotNull(message="{transformation.position.not-null}")
    @PositiveOrZero(message="{transformation.position.positive}")
    private Integer position;

    @Schema(description = "The identifier of the module associated to this transformation (ex:9999)", example = "9999")
    @NotNull(message="{transformation.module-id.not-null}")
    @Positive(message="{transformation.module-id.positive}")
    private Long moduleId;
}
