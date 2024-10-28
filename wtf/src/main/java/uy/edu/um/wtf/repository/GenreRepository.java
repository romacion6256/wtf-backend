package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
