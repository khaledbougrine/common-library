package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/** Projection of flow module */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represent a module row for a flow.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleDTO implements Serializable {
    @Schema(description = "The unique technical identifier of the module")
    @NotNull(message = "{module.id.not-null}")
    @PositiveOrZero(message = "{module.id.positive}")
    Long id;

    @Schema(description = "The name of the module")
    @NotBlank(message = "{module.name.not-blank}")
    String name;

    @Schema(description = "The alias of the module", nullable = true)
    @Nullable
    String alias;

    @Schema(description = "The name of the partner owning the module", nullable = true)
    @Nullable
    String partnerName;

    @Schema(description = "Boolean flag indicating if the module is active")
    @NotNull(message = "{module.active.not-null}")
    Boolean isActive = true;
}
