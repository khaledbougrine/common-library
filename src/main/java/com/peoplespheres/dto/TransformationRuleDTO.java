package com.peoplespheres.dto;

// Local imports

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.RuleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/** DTO to transport and expose the details about a mapping transformation rule */
@Getter
@Setter
@Schema(description = "TransformationDomain rule DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformationRuleDTO implements Serializable {
    @Schema(description = "TransformationDomain rule technical ID")
    @NotNull(message="{transformation.rule.id.not-null}")
    @PositiveOrZero(message="{transformation.rule.id.positive}")
    private Integer id;

    @Schema(description = "The displayed name of this transformation rule" )
    @NotBlank(message="{transformation.rule.name.not-blank}")
    private String name;

    @Schema(description = "The function associated to this transformation rule (ex:text)", example = "text",
            allowableValues = {"alias", "text", "list", "hierarchy", "checkbox", "multi_checkbox", "radio_button",
                    "composite", "calculated", "currency", "range", "named_file", "tag", "url", "number", "datetime", "date", "image",
                    "user_relation", "group", "latitude", "longitude", "modules", "domain", "external_id", "string"})
    @NotNull(message="{transformation.rule.function.not-null}")
    private RuleTypeEnum function;

    @Schema(description = "The minimum number of parameters required for this specific transformation rule (ex: 0)", example = "0")
    @NotNull(message="{transformation.rule.min-param.not-null}")
    @PositiveOrZero(message="{transformation.rule.min-param.positive}")
    private Integer paramNumberMin;

    @Schema(description = "The maximum number of parameters required for this specific transformation rule (ex: 3)", example = "3")
    @NotNull(message="{transformation.rule.max-param.not-null}")
    @PositiveOrZero(message="{transformation.rule.max-param.positive}")
    private Integer paramNumberMax;

    @Schema(description = "Boolean flag indicating if the transformation rule is purely internal" )
    @NotNull(message="{transformation.rule.internal.not-null}")
    Boolean isInternal;
}
