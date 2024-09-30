package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}