package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_GENRE")
    private Long idGenre;

}
