package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface FunctionRepository extends JpaRepository<Function, Long> {

    Optional<Function> findByFormatAndSubtitledAndDateAndTimeAndMovieAndRoom(String format, Boolean subtitled, LocalDate date, LocalTime time, Movie movie, Room room);

    Optional<Function> findByIdFunction (Long idFunction);
}