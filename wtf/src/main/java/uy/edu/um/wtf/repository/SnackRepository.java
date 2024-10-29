package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Snack;

import java.util.Optional;

public interface SnackRepository extends JpaRepository<Snack, Long> {
    Optional<Snack> findByDescription(String description);
}
