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
    private List<Function> albums = new LinkedList<Function>();
}
