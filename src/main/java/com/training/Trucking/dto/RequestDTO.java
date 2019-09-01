package com.training.Trucking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
public class RequestDTO {

    @NotNull(message = "{not.null}")
    @Size(min = 5, max = 255, message = "{length.request}")
    String request;

    @NotNull(message = "{not.null}")
    String  status;

    @NotNull(message = "{not.null}")
    Long price;

    @NotNull(message = "{not.null}")
    String  reason;

}
