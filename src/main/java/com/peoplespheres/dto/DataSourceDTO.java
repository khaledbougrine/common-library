package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/** DTO to transport and expose the data for a flow mapping */
@Getter
@Setter
@Schema(description = "Mapping DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSourceDTO extends ADataDTO implements Serializable {
    @Schema(description = "The alias of the category to which this data source is associated (ex:'personal_information')", example = "personal_information")
    @NotBlank(message = "{data.category.alias.not-blank}")
    private String categoryAlias;

    @Schema(description = "Data associated PSO type alias", nullable = true)
    @Nullable // Nullable only for external/partner data, not for PS data
    protected String psoTypeAlias;

    @Schema(description = "Data associated PSO type id", nullable = true)
    @Nullable // Nullable only for external/partner data, not for PS data
    protected Integer psoTypeId;

    @Schema(description = "Data associated related PSO type alias", nullable = true)
    @Nullable // Nullable only for external/partner data, not for PS data
    protected String relatedPsoTypeAlias;

    @Schema(description = "Data associated related PSO type id", nullable = true)
    @Nullable // Nullable only for external/partner data, not for PS data
    protected Integer relatedPsoTypeId;

    @Schema(description = "Data parent alias", nullable = true)
    @Nullable
    protected String parentAlias;

    @Schema(description = "Data parent field name", nullable = true)
    @Nullable
    protected String parentFieldName;
}
