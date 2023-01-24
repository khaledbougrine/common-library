package com.peoplespheres.dto;

// Swagger imports
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/** DTO data transport object for client data */
@Schema(description = "Client full DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientFullDTO extends ClientDTO {

}