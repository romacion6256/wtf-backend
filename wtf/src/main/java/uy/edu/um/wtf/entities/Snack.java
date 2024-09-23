package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "SNACK")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Snack implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SNACK_ID")
    private Long snackId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;
}
