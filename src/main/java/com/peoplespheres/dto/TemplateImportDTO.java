package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Schema(description = "Template file import DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateImportDTO implements Serializable {
    @Schema(description = "List of connectors")
    @Size(min = 1, message="{template.import.connectors.min-one}")
    private Collection<ConnectorFullDTO> connectors;
}
