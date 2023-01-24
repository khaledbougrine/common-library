package com.peoplespheres.dto;

// Local imports

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.FieldTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@Schema(description = "Data full DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ADataFullDTO implements Serializable {
    @Schema(description = "Data ID", nullable = true)
    @Nullable
    protected Long id;

    @Schema(description = "Data name (ex:Username)", example = "Username")
    @NotBlank(message = "{data.name.not-blank}")
    protected String name;

    @Schema(description = "Data alias (ex:usr_username)", example = "usr_username")
    @NotBlank(message = "{data.alias.not-blank}")
    protected String alias;

    @Schema(description = "Data field type (ex:text)",
            allowableValues = {"alias", "text", "list", "hierarchy", "checkbox", "multi_checkbox", "radio_button",
            "composite", "calculated", "currency", "range", "named_file", "tag", "url", "number", "datetime", "date", "image",
            "user_relation", "group", "latitude", "longitude", "modules", "domain", "external_id", "string"})
    @NotNull(message = "{data.type.not-null}")
    protected FieldTypeEnum fieldType;

    @Schema(description = "Data category alias")
    @NotBlank(message = "{data.category.alias.not-blank}")
    protected String categoryAlias;

    @Schema(description = "Data default value", nullable = true)
    @Nullable
    protected String defaultValue;

    @Schema(description = "Data type array (true/false)")
    @NotNull(message = "{data.isArray.not-null}")
    protected Boolean isArray;

    @Schema(description = "Data parent alias", nullable = true)
    @Nullable
    protected String parentAlias;

    @Schema(description = "Data parent field name", nullable = true)
    @Nullable
    protected String parentFieldName;

    @Schema(description = "Data required flag (true/false)")
    @NotNull(message = "{data.isRequired.not-null}")
    protected Boolean isRequired;

    @Schema(description = "Data associated PSO type alias", nullable = true)
    @Nullable // Nullable only for external/partner data, not for PS data
    protected String psoTypeAlias;

    @Schema(description = "Data related pso", nullable = true)
    @Nullable
    protected String relatedPso;

    @Schema(description = "Unique identifier of the associated flow")
    @NotNull(message = "{data.mapping.id.not-null}")
    @PositiveOrZero(message = "{data.mapping.id.positive}")
    protected Long mappingId;

    @Schema(description = "Unique identifier of the associated client", nullable = true) // Required, but not needed fom the FE
    @NotBlank(message = "{data.client.id.not-blank}")
    protected String clientId;

    @Schema(description = "Unique identifier given for external/partner data", nullable = true)
    @Nullable // Nullable only for PS data but not external/partner data
    private String technicalId;
}
