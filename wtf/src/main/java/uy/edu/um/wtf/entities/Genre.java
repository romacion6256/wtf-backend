package uy.edu.um.wtf.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "GENRE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENRE_ID")
    private Long idGenre;

    @Column (name = "NAME")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Movie> movies; // Lista de peliculas que tienen el genero
}
