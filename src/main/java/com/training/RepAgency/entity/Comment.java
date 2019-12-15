package com.training.RepAgency.entity;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;
    String comment;

    @ManyToOne()
    Request request;

//    @ManyToOne()
//    User user;

//    @OneToOne
   // Request request;
}
