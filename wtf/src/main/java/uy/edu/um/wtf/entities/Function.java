package uy.edu.um.wtf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "FUNCTION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Function implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long idFunction;

    @Column(name = "FORMAT")
    private String format;

    @Column(name = "SUBTITLED")
    private Boolean subtitled;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "TIME")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room", nullable = false)
    private Room room;

    @JsonIgnoreProperties({"function","snacks","idReservation","status","paymentMethod","reservationDate"})
    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    private Admin admin;
}
