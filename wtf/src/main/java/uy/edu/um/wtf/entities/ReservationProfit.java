package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "RESERVATION_PROFIT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Column(name = "FUNCTION_ID")
    private Long functionId;

    @Column(name = "MONTO")
    private BigDecimal monto;

    @Column(name = "ESTADO")
    private String status;
}

