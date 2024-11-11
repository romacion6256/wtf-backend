package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.ReservationProfit;
import uy.edu.um.wtf.repository.ReservationProfitRepository;

@Service
public class ReservationProfitService {

    @Autowired
    private ReservationProfitRepository reservationProfitRepository;

    public void saveReservationProfit(ReservationProfit reservationProfit) {
        reservationProfitRepository.save(reservationProfit);
    }

}
