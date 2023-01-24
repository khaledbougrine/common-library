package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Data source DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSourceFullDTO extends ADataFullDTO implements Serializable {

}
