package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
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
    @Column(name = "ID_MOVIE")
    private Long idMovie;

    @Column(name = "MOVIE_NAME")
    private String movieName;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "DIRECTOR_NAME")
    private String directorName;

    @Column(name = "DURATION")
    private Integer duration;

}
