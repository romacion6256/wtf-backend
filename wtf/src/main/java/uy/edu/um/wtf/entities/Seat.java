package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import static jakarta.persistence.GenerationType.IDENTITY;
import java.util.LinkedList;
import java.util.List;
@Entity
@Table(name = "SEAT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seat implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEAT_ID")
    private Long seatId;

    @Column(name = "ROW")
    private String row;

    @Column(name = "NUMBER")
    private int number;

    // Many seats belong to one room
    @ManyToOne
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private Room room;

}
