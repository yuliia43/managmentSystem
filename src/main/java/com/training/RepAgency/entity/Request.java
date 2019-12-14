package com.training.RepAgency.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String request; //task
    String status;
    Date deadline;
    String creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    User master;


}
