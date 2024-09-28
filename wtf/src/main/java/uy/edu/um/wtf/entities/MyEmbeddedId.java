package uy.edu.um.wtf.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MyEmbeddedId implements Serializable {
    @Column(name = "USER_NAME")
    private Long userName;
    @Column(name = "ID")
    private Long idUser;
    @Column(name = "EMAIL")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyEmbeddedId)) return false;
        MyEmbeddedId that = (MyEmbeddedId) o;
        return userName.equals(that.userName) &&
                idUser.equals(that.idUser) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, idUser, email);
    }
}
