package uy.edu.um.wtf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
//@Table(name = "ADMIN")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {

    // Relación uno a muchos con la entidad Snack
    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Snack> snacks; // Lista de snacks creados por el administrado

    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Function> functions; // Lista de funciones creadas por este admin

    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movie> movies; // Lista de películas creadas por este admin
}
