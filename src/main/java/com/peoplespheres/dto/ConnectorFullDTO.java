package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.ConnectorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Import connector template file")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorFullDTO implements Serializable {
    @Schema(description = "Connector ID", nullable = true)
    @Nullable
    @PositiveOrZero(message="{connector.id.positive}")
    private Long id;

    @Schema(description = "Connector name")
    @NotBlank(message="{connector.name.not-blank}")
    private String name;

    @Schema(description = "Connector type", allowableValues = {"IN", "OUT", "IN-OUT"})
    @NotBlank(message="{connector.type.not-blank}")
    private ConnectorTypeEnum type;

    @Schema(description = "Provider ID")
    @NotBlank(message="{connector.provider.not-blank}")
    private String providerId;

    @Schema(description = "Is this connector currently active ?")
    @NotBlank(message="{connector.provider.not-blank}")
    private Boolean isActive;

    @Schema(description = "List of flows", nullable = true)
    @Nullable
    private Set<FlowFullDTO> flows;
}
