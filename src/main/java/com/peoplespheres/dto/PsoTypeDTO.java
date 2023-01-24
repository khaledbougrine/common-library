package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/** A DTO for the {@link com.peoplespheres.out.mapping.infrastructure.persistence.models.PsoTypeEntity} entity */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PsoTypeDTO implements Serializable {
    @Schema(description = "Unique identifier of this PSO type")
    @NotNull(message = "{pso_type.id.not-null}")
    private Integer id;

    @Schema(description = "Alias of this PSO type (ex:usr)", example = "usr")
    @Size(max = 3, message = "{pso_type.alias.max_size}")
    @NotNull(message = "{pso_type.alias.not-null}")
    private String alias;

    @Schema(description = "Name of this PSO type (ex:User)", example = "User")
    @Size(max = 255, message = "{pso_type.name.max_size}")
    private String name;

    @Schema(description = "List of the field categories containing fields in this PSO type")
    @Size(min = 1, message = "{pso_type.field_categories.min_size}")
    private Set<FieldCategoryDTO> fieldCategories;
}