package uy.edu.um.wtf.entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import uy.edu.um.wtf.entities.Reservation;
import uy.edu.um.wtf.entities.Card;

@Entity
@Table(name = "USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable{

    /*// PK COMPUESTA
    @EmbeddedId
    private MyEmbeddedId myEmbeddedId;
    *//*@GeneratedValue(strategy = IDENTITY)*/

    @Id
    @GeneratedValue
    @GenericGenerator(name = "ID", strategy = "increment")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "DOCUMENT")
    private Long document;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "PHONE_NUMBER")
    private Long phoneNumber;

    @Column(name = "ADRESS")
    private String adress;

    @Column(name = "EMAIL")
    private String email;

    //@OneToMany(mappedBy = "user")
    //private List<Reservation> reservations;

    // Relationship with Card (One to One)
    //@OneToOne
    //@JoinColumn(name = "CARD_ID", referencedColumnName = "CARD_NUMBER", nullable = false)
    //private Card card; // Relationship to Card

}
