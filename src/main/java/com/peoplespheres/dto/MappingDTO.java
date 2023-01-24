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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MappingDTO extends AMappingDTO implements Serializable {
    @Schema(description = "Main source data associated to this mapping", nullable = true)
    @Nullable
    DataSourceDTO dataSource;

    @Schema(description = "Transformations needed on this mapping, no transformation means raw-data transformation", nullable = true)
    @Nullable
    private Collection<TransformationDTO> transformations = new ArrayList<>();

    @Schema(description = "Target data associated to this mapping")
    @NotNull(message="{mapping.target.not-null}")
    DataTargetDTO dataTarget;
}
