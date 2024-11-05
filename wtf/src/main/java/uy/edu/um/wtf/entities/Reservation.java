package uy.edu.um.wtf.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


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

    //@JsonIgnore
    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "ROW_SEAT")
    private int rowSeat;

    @Column(name = "COLUMN_SEAT")
    private int columnSeat;

    @JsonIgnore
    @Column(name = "RESERVATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date reservationDate;

    @Column(name = "RATING")
    private Float rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_CLIENT")
    private Client client; // Relación con Cliente

    @JsonIgnoreProperties({"reservations", "admin"})
    @ManyToOne
    @JoinColumn(name = "ID_FUNCTION")
    private Function function; // Relación con Funcion

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "RESERVATION_SNACK", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "ID_RESERVATION"),
            inverseJoinColumns = @JoinColumn(name = "ID_SNACK")
    )
    private List<Snack> snacks; // Relación con Snacks (opcional)
}
