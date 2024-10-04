package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.poi.ss.formula.functions.DMax;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "CARD")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CARD_NUMBER")
    private Long cardNumber;

    @Column(name = "EXPIRATION_MONTH")
    private int expirationMonth;

    @Column(name = "EXPIRATION_YEAR")
    private int expirationYear;

    @Column(name = "CVV")
    private int cvv;

    // Relationship with User (One to One)
    @OneToOne(mappedBy = "card")
    private User user; // Relationship inverse

}
