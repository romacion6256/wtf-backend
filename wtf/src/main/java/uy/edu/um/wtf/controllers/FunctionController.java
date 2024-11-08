package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.BranchRepository;
import uy.edu.um.wtf.repository.FunctionRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.RoomRepository;
import uy.edu.um.wtf.serivces.FunctionService;
import uy.edu.um.wtf.serivces.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/function")
@CrossOrigin(origins = "http://localhost:3000")
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @PostMapping("/agregarFuncion/{idAdmin}")
    public ResponseEntity<String> agregarFuncion(@PathVariable Long idAdmin ,@RequestBody Map<String, String> payload) throws InvalidInformation {
        String format = payload.get("format");
        Boolean subtitled = Boolean.parseBoolean(payload.get("subtitled"));
        LocalDate date = LocalDate.parse(payload.get("date"));
        LocalTime time = LocalTime.parse(payload.get("time"));
        Movie movie = movieRepository.findByMovieName(payload.get("movie")).orElseThrow(() -> new InvalidInformation("La pelicula no existe"));
        String sucursal = payload.get("sucursal");
        Branch branch = branchRepository.findByBranchName(sucursal);
        int sala = Integer.parseInt(payload.get("sala"));
        Room room = roomRepository.findByBranchAndNumber(branch, sala).orElseThrow(() -> new InvalidInformation("La sala no existe"));
        User administrador = userService.getUserById(idAdmin);
        BigDecimal price = new BigDecimal(payload.get("price"));

        Function funcion = new Function();
        funcion.setFormat(format);
        funcion.setSubtitled(subtitled);
        funcion.setDate(date);
        funcion.setTime(time);
        funcion.setMovie(movie);
        funcion.setRoom(room);
        funcion.setPrice(price);
        funcion.setAdmin((Admin) administrador);
        try {
            functionService.agregarFuncion(funcion);
            return ResponseEntity.ok("Funcion agregada correctamente");
        } catch (InvalidInformation e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerTodas")
    public ResponseEntity<?> obtenerTodas() {
        try {
            List<Function> funciones = functionService.obtenerTodas();
            return new ResponseEntity<>(funciones, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarFuncion(@PathVariable Long id) {
        try {
            functionService.eliminarFuncion(id);
            return new ResponseEntity<>("Funcion eliminada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la funcion: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/obtenerFechasDisponibles/{movieName}/{branchName}/{roomNumber}")
    public ResponseEntity<List<String>> obtenerFechasDisponibles(@PathVariable String movieName, @PathVariable String branchName, @PathVariable int roomNumber) {
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Película no encontrada"));
        }

        Long movieId = movie.get().getIdMovie();

        List<Function> functions = functionRepository.findByMovieAndBranchAndRoom(movieId, branchName, roomNumber);
        System.out.println("Funciones encontradas: " + functions.size()); // Verificación

        if (functions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No hay funciones disponibles para los parámetros especificados"));
        }

        List<String> dates = functions.stream()
                .map(function -> function.getDate().toString()) // Conversión de fecha a String
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Fechas encontradas: " + dates); // Verificación

        return ResponseEntity.ok(dates);
    }

    @GetMapping("/obtenerHorasDisponibles/{movieName}/{branchName}/{roomNumber}/{date}")
    public ResponseEntity<List<String>> obtenerHorasDisponibles(
            @PathVariable String movieName,
            @PathVariable String branchName,
            @PathVariable int roomNumber,
            @PathVariable String date) {

        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Película no encontrada"));
        }

        Long movieId = movie.get().getIdMovie();
        LocalDate fecha = LocalDate.parse(date); // Convertir la fecha de String a LocalDate

        List<Function> functions = functionRepository.findByMovieRoomAndDate(movieId, branchName, roomNumber, fecha);

        if (functions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No hay horas disponibles"));
        }

        List<String> horasDisponibles = functions.stream()
                .map(function -> function.getTime().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok(horasDisponibles);
    }

    @GetMapping("/obtenerFormatosDisponibles/{movieName}/{branchName}/{roomNumber}/{date}/{time}")
    public ResponseEntity<List<String>> obtenerFormatosDisponibles(
            @PathVariable String movieName,
            @PathVariable String branchName,
            @PathVariable int roomNumber,
            @PathVariable String date,
            @PathVariable String time) {

        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Película no encontrada"));
        }

        Long movieId = movie.get().getIdMovie();
        LocalDate localDate = LocalDate.parse(date); // Convertir fecha a LocalDate
        LocalTime localTime = LocalTime.parse(time); // Convertir hora a LocalTime

        // Buscar todas las funciones que coincidan con los parámetros especificados
        List<Function> functions = functionRepository.findByMovieBranchRoomDateAndTime(movieId, branchName, roomNumber, localDate, localTime);

        if (functions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No hay formatos disponibles para los parámetros especificados"));
        }

        // Extraer y eliminar formatos duplicados
        List<String> formatos = functions.stream()
                .map(Function::getFormat) // Extraer el formato de cada función
                .distinct() // Eliminar duplicados
                .collect(Collectors.toList());

        return ResponseEntity.ok(formatos);
    }

    @GetMapping("/obtenerSubtitulosDisponibles/{movieName}/{branchName}/{roomNumber}/{date}/{time}/{format}")
    public ResponseEntity<List<String>> obtenerSubtitulosDisponibles(
            @PathVariable String movieName,
            @PathVariable String branchName,
            @PathVariable int roomNumber,
            @PathVariable String date,
            @PathVariable String time,
            @PathVariable String format) {

        // Buscar la película por nombre
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Película no encontrada"));
        }

        Long movieId = movie.get().getIdMovie();
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        // Obtener las opciones de subtitulado
        List<Boolean> subtitledOptions = functionRepository.findSubtitledByMovieBranchRoomDateTimeAndFormat(
                movieId, branchName, roomNumber, localDate, localTime, format);

        if (subtitledOptions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No hay opciones de subtitulado disponibles para los parámetros especificados"));
        }

        // Convertir a "Sí" o "No" y eliminar duplicados
        List<String> subtitulos = subtitledOptions.stream()
                .map(subtitled -> subtitled ? "Si" : "No")
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(subtitulos);
    }

    @GetMapping("/obtenerIdFuncion")
    public ResponseEntity<Long> obtenerIdFuncion(
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No se encontró la película
        }

        Long movieId = movieOpt.get().getIdMovie();
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        // Busca la función que coincide con todos los parámetros
        Function funcion = functionRepository.findByDetails(
                movieId, branchName, roomNumber, localDate, localTime, format, subtitled);

        if (funcion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No se encontró la función
        }

        return ResponseEntity.ok(funcion.getIdFunction()); // Devuelve el ID de la función
    }


}
