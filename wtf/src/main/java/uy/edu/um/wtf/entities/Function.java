package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;


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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "TIME")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "room", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "function")
    private List<Reservation> reservations; // Relación inversa


    /*@OneToOne(mappedBy = "function")
    private Reservation reservation;*/
}
