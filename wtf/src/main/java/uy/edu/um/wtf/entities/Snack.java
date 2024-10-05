package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "SNACK")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Snack implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SNACK_ID")
    private Long snackId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToMany(mappedBy = "snacks")
    private List<Reservation> reservations; // Relación con Reservas

    // Relación muchos a uno con la entidad Admin
    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", nullable = false) // Clave foránea
    private Admin admin; // Referencia al administrador que creó el snack
}
