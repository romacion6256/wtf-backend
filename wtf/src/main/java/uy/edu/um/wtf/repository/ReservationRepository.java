package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClient_Id(Long clientId);

    List<Reservation> findByColumnSeatAndRowSeat (int columnSeat, int rowSeat);
}