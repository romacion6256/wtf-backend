package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Branch;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Room;
import uy.edu.um.wtf.repository.FunctionRepository;
import uy.edu.um.wtf.repository.MovieRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/branch")
@CrossOrigin(origins = "http://localhost:3000")
public class BranchController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @GetMapping("/obtenerSucursales/{movieName}")
    public ResponseEntity<List<String>> obtenerSucursalesPorPelicula(@PathVariable String movieName) {
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);

        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Película no encontrada"));
        }

        // Obtener todas las funciones de la película usando la entidad Movie
        List<Function> functions = functionRepository.findByMovie(movie.get());

        // Obtener el nombre de la sucursal para cada sala de la función
        List<String> branchNames = functions.stream()
                .map(Function::getRoom)                      // Obtener el room de cada función
                .map(Room::getBranch)                        // Obtener la branch de cada room
                .map(Branch::getBranchName)                  // Obtener el nombre de cada branch
                .distinct()                                  // Filtrar nombres duplicados de branches
                .collect(Collectors.toList());

        if (branchNames.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No se encontraron sucursales para la película"));
        }

        return ResponseEntity.ok(branchNames);
    }



}
