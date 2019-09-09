package com.training.Trucking.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
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
