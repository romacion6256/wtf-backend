package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

        Function funcion = new Function();
        funcion.setFormat(format);
        funcion.setSubtitled(subtitled);
        funcion.setDate(date);
        funcion.setTime(time);
        funcion.setMovie(movie);
        funcion.setRoom(room);
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

}
