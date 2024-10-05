package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    // Relación uno a muchos con la entidad Snack
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Snack> snacks; // Lista de snacks creados por el administrado

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Function> functions; // Lista de funciones creadas por este admin

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movie> movies; // Lista de películas creadas por este admin
}
