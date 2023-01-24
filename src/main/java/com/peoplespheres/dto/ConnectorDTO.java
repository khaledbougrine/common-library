package com.peoplespheres.dto;

// Jackson imports

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peoplespheres.enums.ConnectorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/** DTO data transport object for connector data */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Connector DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorDTO {
    @Schema(description = "Connector ID", nullable = true)
    @Nullable
    private Long id;

    @Schema(description = "Connector name")
    @NotBlank(message="{connector.name.not-blank}")
    private String name;

    @Schema(description = "Connector type")
    @JsonProperty("type")
    @NotBlank(message="{connector.type.not-blank}")
    private ConnectorTypeEnum typeCode;

    @Schema(description = "List of flow ids")
    @NotNull(message="{connector.flows.id.not-null}")
    private List<Long> flowIds = new ArrayList<>();

    @Schema(description = "Unique identifier of the targeted module")
    @NotNull(message="{connector.module.id.not-null}")
    @PositiveOrZero(message="{connector.module.id.positive}")
    private Long moduleId;

    @Schema(description = "Unique identifier of the client", nullable = true) // Required, but not needed from the FE
    @NotBlank(message="{connector.client.id.not-blank}")
    @Size(min=36,max=36, message = "{connector.client.id.uuid-format}")
    String clientId;
}
