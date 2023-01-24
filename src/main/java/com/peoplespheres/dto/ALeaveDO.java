package com.peoplespheres.dto;

// Lombok imports

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Data
public abstract class ALeaveDO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "client can not null")
  private String client;

  @NotNull(message = "id can not null")
  private Long id;

  @NotNull(message = "externalId can not null")
  private String externalId;

  @NotNull(message = "typeCode can not null")
  private String typeCode;

  @NotNull(message = "startDate can not null")
  private Date startDate;

  @NotNull(message = "endDate can not null")
  private Date endDate;

  @NotNull(message = "daysCount can not null")
  private Double daysCount;

  private String comment;

  @NotNull(message = "userId can not null")
  private Long userId;

}
