package com.training.RepAgency.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class CommentDTO {
    @NotNull(message = "{not.null}")
    LocalDate date;

    @NotNull(message = "{not.null}")
    @Size(min = 5, max = 255, message = "{length.request}")
    String comment;

    @NotNull(message = "{not.null}")
    String username;
}
