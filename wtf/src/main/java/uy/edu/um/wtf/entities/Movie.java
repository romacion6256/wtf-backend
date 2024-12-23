package uy.edu.um.wtf.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "MOVIE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long idMovie;

    @Column(name = "NAME")
    private String movieName;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "PUNTUACION")
    private float puntuacion;

    @Column(name = "DIRECTOR")
    private String directorName;

    @Column(name = "DURATION")
    private Integer duration;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Function> functions = new LinkedList<Function>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", nullable = false) // Clave foránea
    private Admin admin; // Referencia al administrador que creó la película

    @ManyToOne
    @JoinColumn(name = "GENRE_ID", nullable = false) // Clave foránea
    private Genre genre;
}

