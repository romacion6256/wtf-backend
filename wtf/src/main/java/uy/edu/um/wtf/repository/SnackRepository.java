package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Snack;

public interface SnackRepository extends JpaRepository<Snack, Long> {
}
