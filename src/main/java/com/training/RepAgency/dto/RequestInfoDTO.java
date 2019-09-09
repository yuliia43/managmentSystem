package com.training.RepAgency.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestInfoDTO {

    long id;

    String master;

    long price;

    String  reason;
}
