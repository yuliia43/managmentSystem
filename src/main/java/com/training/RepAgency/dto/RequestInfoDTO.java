package com.training.RepAgency.dto;

import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestInfoDTO {

    String request;

    String master;

    Date deadline;

}
