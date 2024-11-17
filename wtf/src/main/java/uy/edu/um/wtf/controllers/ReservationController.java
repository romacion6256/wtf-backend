package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.repository.FunctionRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.ReservationRepository;
import uy.edu.um.wtf.repository.SnackRepository;
import uy.edu.um.wtf.serivces.ReservationProfitService;
import uy.edu.um.wtf.serivces.ReservationService;
import uy.edu.um.wtf.serivces.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SnackRepository snackRepository;

    @Autowired
    private ReservationProfitService reservationProfitService;

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

//    @PostMapping("/crearReservaa/{idClient}")
//    public ResponseEntity<?> crearReservaa(@PathVariable Long idClient, @RequestBody Map<String, String> payload) {
//        String paymentMethod = payload.get("paymentMethod");
//        int rowSeat = Integer.parseInt(payload.get("rowSeat"));
//        int columnSeat = Integer.parseInt(payload.get("columnSeat"));
//        Date reservationDate = new Date();
//        Function funcion = functionRepository.findByIdFunction(Long.parseLong(payload.get("idFunction"))).orElseThrow(() -> new IllegalArgumentException("Funcion no encontrada"));
//        User cliente = userService.getUserById(idClient);
//        List<Snack> snacks = Arrays.stream(payload.get("snackIds").split(","))
//                .map(Long::parseLong)
//                .map(id -> snackRepository.findById(id)
//                        .orElseThrow(() -> new IllegalArgumentException("Snack con ID " + id + " no encontrado")))
//                .collect(Collectors.toList());
//
//        Reservation reservation = new Reservation();
//        reservation.setStatus("Pendiente");
//        reservation.setPaymentMethod(paymentMethod);
//        reservation.setRowSeat(rowSeat);
//        reservation.setColumnSeat(columnSeat);
//        reservation.setReservationDate(reservationDate);
//        reservation.setClient((Client) cliente);
//        reservation.setFunction(funcion);
//        reservation.setSnacks(snacks);
//
//        try {
//            reservationService.crearReserva(reservation);
//            return ResponseEntity.ok("Reserva creada exitosamente con ID: " + reservation.getIdReservation());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error al crear la reserva: " + e.getMessage());
//        }
//    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/crearReserva/{idClient}")
    public ResponseEntity<?> crearReserva(@PathVariable Long idClient, @RequestBody Map<String, Object> payload) {
        String paymentMethod = (String) payload.get("paymentMethod");
        Date reservationDate = new Date();

        // Obtener la función
        Long idFunction = Long.valueOf(String.valueOf(payload.get("idFunction")));
        Function funcion = functionRepository.findByIdFunction(idFunction)
                .orElseThrow(() -> new IllegalArgumentException("Función no encontrada"));

        // Obtener el cliente
        User cliente = userService.getUserById(idClient);

        // Obtener la lista de snacks seleccionados
        List<Snack> snacks = new ArrayList<>();
        if (payload.containsKey("snackIds") && payload.get("snackIds") != null && !((String) payload.get("snackIds")).isEmpty()) {
            snacks = Arrays.stream(((String) payload.get("snackIds")).split(","))
                    .map(Long::parseLong)
                    .map(id -> snackRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Snack con ID " + id + " no encontrado")))
                    .collect(Collectors.toList());
        }

        // Obtener la lista de asientos seleccionados
        List<Map<String, Integer>> seats = (List<Map<String, Integer>>) payload.get("seats");

        // Validar si hay asientos seleccionados
        if (seats == null || seats.isEmpty()) {
            return ResponseEntity.badRequest().body("Debe seleccionar al menos un asiento.");
        }

        // Calcular el precio total por los asientos
        BigDecimal precioFuncion = funcion.getPrice();

        // Calcular el precio total por los snacks
        BigDecimal totalPrecioSnacks = snacks.stream()
                .map(Snack::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal precioDeSnackPorAsiento = totalPrecioSnacks.divide(new BigDecimal(seats.size()), RoundingMode.HALF_UP);

        // Aplicar descuento si la reserva está dentro del plazo
        BigDecimal totalMonto;
        LocalDate fechaLimiteDescuento = LocalDate.of(2024, 11, 1).plusMonths(6);
        if (!reservationDate.toInstant().isAfter(fechaLimiteDescuento.atStartOfDay(ZoneId.systemDefault()).toInstant())) {
            // Solo se cobra el precio de los snacks si aplica el descuento
            totalMonto = precioDeSnackPorAsiento;
        } else {
            // Precio completo
            totalMonto = precioFuncion.add(precioDeSnackPorAsiento);
        }

        // Crear una reserva por cada asiento
        List<Long> reservationIds = new ArrayList<>();

        for (Map<String, Integer> seat : seats) {
            int rowSeat = seat.get("rowSeat");
            int columnSeat = seat.get("columnSeat");

            // Crear una nueva instancia de Reservation para cada asiento
            Reservation reservation = new Reservation();
            reservation.setStatus("Pendiente");
            reservation.setPaymentMethod(paymentMethod);
            reservation.setRowSeat(rowSeat);
            reservation.setColumnSeat(columnSeat);
            reservation.setReservationDate(reservationDate);
            reservation.setClient((Client) cliente);
            reservation.setFunction(funcion);
            reservation.setSnacks(snacks);

            // Crear la instancia de ReservationProfit
            ReservationProfit reservationProfit = ReservationProfit.builder()
                    .clientId(idClient)
                    .functionId(idFunction)
                    .monto(totalMonto) // Asignar el monto calculado
                    .status("PAGO")
                    .reservation(reservation)
                    .build();

            try {
                reservationService.crearReserva(reservation);
                reservationIds.add(reservation.getIdReservation()); // Guarda el ID de la reserva creada
                reservationProfitService.saveReservationProfit(reservationProfit);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error al crear la reserva: " + e.getMessage());
            }
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Reservas creadas exitosamente con IDs: " + reservationIds));
    }



    @GetMapping("/asientosReservados")
    public ResponseEntity<List<Map<String, Integer>>> obtenerAsientosReservados(
            @RequestParam String movieName,
            @RequestParam String branchName,
            @RequestParam int roomNumber,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam String format,
            @RequestParam boolean subtitled) {

        // Buscar la película por nombre
        Optional<Movie> movieOpt = movieRepository.findByMovieName(movieName);
        if (movieOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        Long movieId = movieOpt.get().getIdMovie();
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        // Busca la función que coincide con todos los parámetros
        List<Function> funciones = functionRepository.findByParams(
                movieId, branchName, roomNumber, localDate, localTime, format, subtitled);

        if (funciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        // Obtener las reservas de la función encontrada
        List<Reservation> reservas = reservationRepository.findByFunction(funciones.get(0));

        // Recopilar asientos reservados
        List<Map<String, Integer>> asientosReservados = new ArrayList<>();
        for (Reservation reserva : reservas) {
            Map<String, Integer> asiento = new HashMap<>();
            asiento.put("row", reserva.getRowSeat());
            asiento.put("column", reserva.getColumnSeat());
            asientosReservados.add(asiento);
        }

        return ResponseEntity.ok(asientosReservados);
    }

    @GetMapping("/actualizarReservas")
    public ResponseEntity<String> actualizarReservas() {
        reservationService.actualizarReservasPendientes();
        return ResponseEntity.ok("Reservas actualizadas");
    }

    @PatchMapping("/calificar/{idReserva}")
    public ResponseEntity<String> calificarReserva(@PathVariable Long idReserva, @RequestBody Map<String, String> payload) {
        float puntuacion = Float.parseFloat(payload.get("puntuacion"));
        Reservation reserva = reservationRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        try {
            reserva.setRating(puntuacion);
            reservationRepository.save(reserva);
            return ResponseEntity.ok("Reserva calificada correctamente");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
