package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name = "CVV")
    private int cvv;

    // Relationship with User (One to One)
    @OneToOne(mappedBy = "card")
    private User user; // Relationship inverse

}
