package com.peoplespheres.dto;

// J2EE imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/** DTO data transport object for client data */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Client DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    @Schema(description = "Client technical unique identifier")
    @NotNull(message="{client.guid.not-null}")
    String id;

    @Schema(description = "Client name (ex:Orchestrate)", example = "Orchestrate")
    @NotBlank(message="{client.name.not-blank}")
    String name;

    @Schema(description = "Client alias (ex:orchestrate)", example = "orchestrate")
    @NotBlank(message="{client.alias.not-blank}")
    String alias;

    @Schema(description = "Client description", nullable = true)
    @Nullable
    String description;

    @Schema(description = "Client list of its active connector unique identifiers", nullable = true)
    @Nullable
    List<Long> connectorIds;

    @Schema(description = "Client list of its active population names", nullable = true)
    @Nullable
    List<String> populations;

    @Schema(description = "Client list of its active settings unique identifiers", nullable = true)
    @Nullable
    List<Long> settingIds;
}