package uy.edu.um.wtf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
//@Table(name = "CLIENT")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User {

    // Relación uno a uno con la entidad Tarjeta
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "CARD_NUMBER", referencedColumnName = "CARD_NUMBER")
    private Card card;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Reservation> reservations; // Relación con Reserva
}
