package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}