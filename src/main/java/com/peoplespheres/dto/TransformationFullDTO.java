package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.RuleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@Schema(description = "Transformation Full object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformationFullDTO implements Serializable {
    @Schema(description = "Transformation id")
    @NotNull(message="{transformation.id.not-null}")
    @PositiveOrZero(message="{transformation.id.positive}")
    private Long id;

    @Schema(description = "Transformation name")
    @Nullable
    private String name;

    @Schema(description = "Transformation parameters")
    @Nullable
    private String parameters;

    @Schema(description = "Transformation position")
    @NotNull(message="{transformation.position.not-null}")
    @PositiveOrZero(message="{transformation.position.positive}")
    private Integer position;

    @Schema(description = "Transformation module id")
    @NotNull(message="{transformation.module.id.not-null}")
    @Positive(message="{transformation.module.id.positive}")
    private Long moduleId;

    @Schema(description = "Transformation flow id")
    @NotNull(message="{transformation.mapping.id.not-null}")
    @Positive(message="{transformation.mapping.id.positive}")
    private Long mappingId;

    @Schema(description = "Transformation flow id")
    @NotBlank(message="{flow.client.id.not-blank}")
    @Size(min = 36,max = 36, message = "{transformation.client.id.uuid-format}")
    private String clientId;

    @Schema(description = "Transformation rule object")
    @Nullable // null means this a raw data if there is a single transformation else ignored
    private TransformationRuleDTO transformationRule;
}
