package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Branch;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Room;
import uy.edu.um.wtf.repository.BranchRepository;
import uy.edu.um.wtf.repository.FunctionRepository;
import uy.edu.um.wtf.repository.MovieRepository;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @GetMapping("/obtenerSalas/{branchName}")
    public ResponseEntity<List<String>> obtenerSalas(@PathVariable String branchName) {
        // Buscar la sucursal por nombre
        Optional<Branch> branch = Optional.ofNullable(branchRepository.findByBranchName(branchName));
        if (branch.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Sucursal no encontrada"));
        }

        // Obtener las salas de la sucursal
        List<Room> rooms = branch.get().getRooms();

        // Verificar si la sucursal tiene salas
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No hay salas en esta sucursal"));
        }

        // Convertir los números o nombres de las salas a String
        List<String> roomNames = rooms.stream()
                .map(room -> String.valueOf(room.getNumber())) // o room.getName() si tienes nombres de sala
                .collect(Collectors.toList());

        return ResponseEntity.ok(roomNames);
    }
    @GetMapping("/obtenerSalasDisponibles/{movieName}/{branchName}")
    public ResponseEntity<List<String>> obtenerSalasDisponibles(@PathVariable String movieName, @PathVariable String branchName) {
        // Buscar la película por nombre
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Película no encontrada"));
        }

        Long movieId = movie.get().getIdMovie();

        // Encuentra las funciones por id de película y nombre de la sucursal
        List<Function> functions = functionRepository.findByMovieAndBranch(movieId, branchName);

        // Obtener las salas de las funciones
        List<Room> rooms = functions.stream()
                .map(Function::getRoom) // Obtener la sala de cada función
                .distinct()             // Filtrar salas duplicadas
                .collect(Collectors.toList());

        // Verificar si hay salas disponibles
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No hay salas disponibles"));
        }

        // Convertir los números de las salas a String
        List<String> roomNames = rooms.stream()
                .map(room -> String.valueOf(room.getNumber())) // Convierte el número de la sala a String
                .collect(Collectors.toList());

        return ResponseEntity.ok(roomNames);
    }



}
