package com.peoplespheres.dto;

// Local imports

import com.peoplespheres.enums.FieldTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/** DTO to transport and expose data for a target data */
@Getter
@Setter
@Schema(description = "Data DTO")
public abstract class ADataDTO implements Serializable {
    @Schema(description = "Data technical ID", nullable = true)
    @Nullable
    private Long id;

    @Schema(description = "Name of the main source data to map to build the associated target data (ex:'Registration number')", example = "Registration number")
    @NotBlank(message = "{data.name.not-blank}")
    private String name;

    @Schema(description = "Alias of the main source data to map to build the associated target data (ex:'usr_registration_number')", example = "usr_registration_number")
    @NotBlank(message = "{data.alias.not-blank}")
    private String alias;

    @Schema(description = "Type of the main source data to map to build the associated target data (ex:text)", example = "text",
            allowableValues = {"alias", "text", "list", "hierarchy", "checkbox", "multi_checkbox", "radio_button",
            "composite", "calculated", "currency", "range", "named_file", "tag", "url", "number", "datetime", "date", "image",
            "user_relation", "group", "latitude", "longitude", "modules", "domain", "external_id", "string"})
    @NotNull(message = "{data.type.not-null}")
    private FieldTypeEnum fieldType;

    @Schema(description = "The default value to give to this data source if not present in the PSO returned by PS global search API", nullable = true)
    @Nullable
    private String defaultValue;

    @Schema(description = "Boolean flag indicating if it is an array")
    @NotNull(message = "{data.isArray.not-null}")
    private Boolean isArray;

    @Schema(description = "Boolean flag indicating if it is an array")
    @NotNull(message = "{data.isRequired.not-null}")
    private Boolean isRequired;
}
