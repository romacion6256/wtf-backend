package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
