package tn.amira.demospringangular.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    @ManyToOne
    private Student student;
    private String file;

}
