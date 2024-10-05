package uy.edu.um.wtf.entities;
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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long idMovie;

    @Column(name = "NAME")
    private String movieName;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "DIRECTOR")
    private String directorName;

    @Column(name = "DURATION")
    private Integer duration;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Function> functions = new LinkedList<Function>();

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", nullable = false) // Clave foránea
    private Admin admin; // Referencia al administrador que creó la película

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "MOVIE_GENRE", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "ID_MOVIE"), // Columna de la tabla película
            inverseJoinColumns = @JoinColumn(name = "ID_GENRE") // Columna de la tabla género
    )
    private List<Genre> genres; // Relación con Genero
}
