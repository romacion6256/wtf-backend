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

    public void cancelarReserva(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id de la reserva no puede ser nulo");
        }
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        // Limpiar la lista de snacks antes de eliminar
        reservation.getSnacks().clear();
        reservationRepository.save(reservation);
        reservationRepository.deleteById(id);
    }

    public void crearReserva(Reservation reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }
        if (reserva.getClient() == null) {
            throw new IllegalArgumentException("El cliente de la reserva no puede ser nulo");
        }
        if (reserva.getFunction() == null) {
            throw new IllegalArgumentException("La funcion de la reserva no puede ser nula");
        }
        if (reserva.getPaymentMethod() == null || reserva.getPaymentMethod().isEmpty()) {
            throw new IllegalArgumentException("El metodo de pago de la reserva no puede ser nulo o vacio");
        }
        if (!reservationRepository.findByColumnSeatAndRowSeat(reserva.getColumnSeat(), reserva.getRowSeat()).isEmpty()) {
            throw new IllegalArgumentException("El asiento ya esta ocupado");
        }
        reservationRepository.save(reserva);
    }
}
