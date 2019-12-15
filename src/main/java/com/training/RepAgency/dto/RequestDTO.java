package com.training.RepAgency.dto;

import com.training.RepAgency.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Getter
@Setter
public class RequestDTO {
    @NotNull(message = "{not.null}")
    Long id;

    @NotNull(message = "{not.null}")
    @Size(min = 5, max = 255, message = "{length.request}")
    String request;

    @NotNull(message = "{not.null}")
    String  status;


    @NotNull(message = "{not.null}")
    Date deadline;

    @NotNull(message = "{not.null}")
    User master;


    Date finishedDate;
}
