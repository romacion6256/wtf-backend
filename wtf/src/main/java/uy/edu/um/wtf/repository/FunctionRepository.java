package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface FunctionRepository extends JpaRepository<Function, Long> {

    Optional<Function> findByFormatAndSubtitledAndDateAndTimeAndMovieAndRoom(String format, Boolean subtitled, LocalDate date, LocalTime time, Movie movie, Room room);

    Optional<Function> findByIdFunction (Long idFunction);

    List<Function> findByMovie(Movie movie);

    @Query("SELECT f FROM Function f WHERE f.movie.id = :movieId AND f.room.branch.branchName = :branchName")
    List<Function> findByMovieAndBranch(@Param("movieId") Long movieId, @Param("branchName") String branchName);

    @Query("SELECT f FROM Function f WHERE f.movie.id = :movieId AND f.room.branch.branchName = :branchName AND f.room.number = :roomNumber")
    List<Function> findByMovieAndBranchAndRoom(@Param("movieId") Long movieId, @Param("branchName") String branchName, @Param("roomNumber") int roomNumber);



    @Query("SELECT f FROM Function f " + "JOIN f.movie m " + "JOIN f.room r " + "JOIN r.branch b " + "WHERE m.idMovie = :movieId " + "AND b.branchName = :branchName " + "AND r.number = :roomNumber " + "AND f.date = :date")
    List<Function> findByMovieRoomAndDate(@Param("movieId") Long movieId, @Param("branchName") String branchName, @Param("roomNumber") int roomNumber, @Param("date") LocalDate date);

    @Query("SELECT f FROM Function f WHERE f.movie.id = :movieId AND f.room.branch.branchName = :branchName AND f.room.number = :roomNumber AND f.date = :date AND f.time = :time")
    List<Function> findByMovieBranchRoomDateAndTime(
            @Param("movieId") Long movieId,
            @Param("branchName") String branchName,
            @Param("roomNumber") int roomNumber,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);

    @Query("SELECT f.subtitled FROM Function f WHERE f.movie.id = :movieId AND f.room.branch.branchName = :branchName AND f.room.number = :roomNumber AND f.date = :date AND f.time = :time AND f.format = :format")
    List<Boolean> findSubtitledByMovieBranchRoomDateTimeAndFormat(
            @Param("movieId") Long movieId,
            @Param("branchName") String branchName,
            @Param("roomNumber") int roomNumber,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("format") String format);

    @Query("SELECT f FROM Function f WHERE f.movie.id = :movieId AND f.room.branch.branchName = :branchName " +
            "AND f.room.number = :roomNumber AND f.date = :date AND f.time = :time AND f.format = :format AND f.subtitled = :subtitled")
    List<Function> findByParams(
            @Param("movieId") Long movieId,
            @Param("branchName") String branchName,
            @Param("roomNumber") int roomNumber,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("format") String format,
            @Param("subtitled") boolean subtitled);

    @Query("SELECT f FROM Function f WHERE f.movie.id = :movieId " +
            "AND f.room.branch.branchName = :branchName " +
            "AND f.room.number = :roomNumber " +
            "AND f.date = :date " +
            "AND f.time = :time " +
            "AND f.format = :format " +
            "AND f.subtitled = :subtitled")
    Function findByDetails(@Param("movieId") Long movieId,
                           @Param("branchName") String branchName,
                           @Param("roomNumber") int roomNumber,
                           @Param("date") LocalDate date,
                           @Param("time") LocalTime time,
                           @Param("format") String format,
                           @Param("subtitled") boolean subtitled);


    @Query("SELECT DISTINCT f.idFunction FROM Function f WHERE f.movie.idMovie = :movieId")
    List<Long> findDistinctFunctionIdsByMovieId(@Param("movieId") Long movieId);


    Optional<Object> findByDateAndTimeAndRoom(LocalDate date, LocalTime time, Room room);
}
