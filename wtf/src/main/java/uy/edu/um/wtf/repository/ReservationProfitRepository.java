package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.ReservationProfit;

import java.util.List;

public interface ReservationProfitRepository extends JpaRepository<ReservationProfit, Long> {

    List<ReservationProfit> findByFunctionIdAndStatus(Long functionId, String status);

}
