package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "BRANCH")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Branch implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_BRANCH")
    private Long idBranch;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Room> rooms;
}
