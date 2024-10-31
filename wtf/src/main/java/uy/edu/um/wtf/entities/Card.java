package uy.edu.um.wtf.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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
    @Column(name = "CARD_NUMBER")
    private Long cardNumber;

    @Column(name = "EXPIRATION_MONTH")
    private int expirationMonth;

    @Column(name = "EXPIRATION_YEAR")
    private int expirationYear;

    @Column(name = "CVV")
    private int cvv;

    // Relación uno a uno inversa con la entidad Client
    @OneToOne(mappedBy = "card")
    @JsonIgnore
    private Client client;

}
