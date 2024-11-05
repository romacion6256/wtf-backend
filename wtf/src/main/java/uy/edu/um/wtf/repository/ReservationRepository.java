package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClient_Id(Long clientId);

    List<Reservation> findByColumnSeatAndRowSeat (int columnSeat, int rowSeat);
    List<Reservation> findByFunction(Function function);

    List<Reservation> findByStatus(String status);

    List<Reservation> findByFunction_IdFunctionAndColumnSeatAndRowSeat(Long idFunction, int columnSeat, int rowSeat);

    @Query("SELECT r FROM Reservation r WHERE r.function.idFunction IN :functionIds AND r.rating IS NOT NULL")
    List<Reservation> findByFunctionIdInAndRatingIsNotNull(@Param("functionIds") List<Long> functionIds);



}