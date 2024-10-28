package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Admin;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.serivces.MovieService;
import uy.edu.um.wtf.serivces.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @GetMapping("/obtenerTodas")
    public List<Movie> obtenerTodas () {
        List<Movie> movies = movieService.obtenerTodasLasPeliculas();
        return new ResponseEntity<>(movies, HttpStatus.OK).getBody();
    }

    @PostMapping("/agregarPelicula/{idAdmin}")
    public ResponseEntity<String> agregarPelicula (@PathVariable Long idAdmin, @RequestBody Map<String, String> payload) throws InvalidInformation {
        String movieName = payload.get("movieName");
        int movieYear = Integer.parseInt(payload.get("movieYear"));
        String nombreDirector = payload.get("nombreDirector");
        Integer duracion = Integer.parseInt(payload.get("duracion"));
        //String genero = payload.get("genero"); tiene q ser una lista
        User administrador = userService.getUserById(idAdmin);

        Movie pelicula = new Movie();
        pelicula.setMovieName(movieName);
        pelicula.setYear(movieYear);
        pelicula.setDirectorName(nombreDirector);
        pelicula.setDuration(duracion);
        pelicula.setAdmin((Admin) administrador);

        try {
            movieService.agregarPelicula(pelicula);
            return ResponseEntity.ok("Pelicula agregada correctamente");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

}
