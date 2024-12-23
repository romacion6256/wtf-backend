package uy.edu.um.wtf.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;
import java.util.LinkedList;
import java.util.List;
@Entity
@Table(name = "ROOM")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "NUMBER")
    private int number;

    // Many rooms belong to one branch
    @ManyToOne
    @JoinColumn(name = "BRANCH_ID", nullable = false)
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Function> functions = new LinkedList<Function>();


}
