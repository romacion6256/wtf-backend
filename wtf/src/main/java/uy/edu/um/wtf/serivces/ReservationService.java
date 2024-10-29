package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Reservation;
import uy.edu.um.wtf.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> obtenerReservasPorClienteId(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("El id del cliente no puede ser nulo");
        }
        return reservationRepository.findByClient_Id(clientId);
    }

}
