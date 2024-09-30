package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
