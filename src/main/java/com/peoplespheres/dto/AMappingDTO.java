package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AMappingDTO implements Serializable {
    @Schema(description = "Unique identifier of this mapping", nullable = true)
    @Nullable
    Long id;

    @Schema(description = "Position in the display of the associated mapping block (ex:1)", example = "1")
    @NotNull(message = "{mapping.position.not-null}")
    @Positive(message = "{mapping.position.positive}")
    private Integer position;

    @Schema(description = "Unique identifier of the flow associated to this mapping")
    @NotNull(message="{mapping.flow.id.not-null}")
    @PositiveOrZero(message="{mapping.flow.id.positive}")
    Long flowId;
}
