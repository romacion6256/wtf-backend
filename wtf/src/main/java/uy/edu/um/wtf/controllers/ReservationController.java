package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Reservation;
import uy.edu.um.wtf.serivces.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/misReservas/{clientId}")
    public ResponseEntity<?> obtenerReservasPorCliente(@PathVariable Long clientId) {
        try {
            List<Reservation> reservations = reservationService.obtenerReservasPorClienteId(clientId);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cancelarReserva/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        try {
            reservationService.cancelarReserva(id);
            return new ResponseEntity<>("Reserva cancelada", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
