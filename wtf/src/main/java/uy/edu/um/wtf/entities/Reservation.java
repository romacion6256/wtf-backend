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
    @Column(name = "ID")
    private Long idReservation;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "ROW_SEAT")
    private int rowSeat;

    @Column(name = "COLUMN_SEAT")
    private int columnSeat;

    /*@ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;*/

    // Relationship with User (Many to One, using composite key)
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "USER_NAME", referencedColumnName = "USER_NAME"),
            @JoinColumn(name = "ID", referencedColumnName = "ID"),
            @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    })
    private User user;

    @OneToMany(mappedBy = "reservation")
    private List<Snack> snacks;

    @ManyToOne
    @JoinColumn(name = "idFunction", nullable = false)
    private Function function;
}
