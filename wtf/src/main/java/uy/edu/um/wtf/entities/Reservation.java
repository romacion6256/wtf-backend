package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;
import java.util.LinkedList;
import java.util.List;
import uy.edu.um.wtf.entities.Reservation;
import uy.edu.um.wtf.entities.Card;

@Entity
@Table(name = "RESERVATION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long reservationId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "NRO_ASIENTO")
    private int nroAsiento;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "reservation")
    private List<Snack> snacks;

    @OneToOne
    @JoinColumn(name = "SHOWTIME_ID")
    private Function showtime;
}
