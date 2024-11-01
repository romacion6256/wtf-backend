package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Movie;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovieName (String movieName);
    Optional<Movie> findById (Long id);
}
