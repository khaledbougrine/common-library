package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/** DTO to transport and expose data for a target data */
@Getter
@Setter
@Schema(description = "Data target DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataTargetDTO extends ADataDTO implements Serializable {
    @Schema(description = "Unique identifier given for external/partner data", nullable = true)
    @Nullable // Nullable only for PS data but not external/partner data
    private String technicalId;
}
